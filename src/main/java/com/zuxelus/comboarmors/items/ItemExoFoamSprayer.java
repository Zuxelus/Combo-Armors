package com.zuxelus.comboarmors.items;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.TankFluidHandlerItemStack;

import ic2.core.IC2;
import ic2.core.block.BlockFoam;
import ic2.core.block.BlockIC2Fence;
import ic2.core.block.BlockScaffold;
import ic2.core.block.state.IIdProvider;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.item.tool.ItemSprayer;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemExoFoamSprayer extends ItemIc2ca {
	private static final int CAPACITY = 8000;
	static enum Target {
		Any, Scaffold, Cable;
	}
	
	public ItemExoFoamSprayer() {
		super();
		setCreativeTab(ComboArmors.creativeTab);
		//setMaxDamage(27);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("info.upgrade_module_installed"));
		FluidStack fs = FluidUtil.getFluidContained(stack);
		if (fs != null)
			tooltip.add("< " + fs.getLocalizedName() + ", " + fs.amount + "/" + getCapacity(stack) + " mB >");
		else
			tooltip.add(I18n.format("ic2.item.FluidContainer.Empty"));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab))
			return;
		items.add(new ItemStack(ModItems.exoFoamSprayer));
		ItemStack stack = new ItemStack(ModItems.exoFoamSprayer);
		fillTank(stack);
		items.add(stack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(player)) {
			ItemStack stack = StackUtil.get(player, hand);
			NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
			int mode = nbtData.getInteger("mode");
			mode = (mode == 0) ? 1 : 0;
			nbtData.setInteger("mode", mode);
			String sMode = (mode == 0) ? "ic2.tooltip.mode.normal" : "ic2.tooltip.mode.single";
			IC2.platform.messagePlayer(player, "ic2.tooltip.mode", new Object[] { sMode });
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
		if (IC2.keyboard.isModeSwitchKeyDown(player))
			return EnumActionResult.PASS;
		if (!IC2.platform.isSimulating())
			return EnumActionResult.SUCCESS;
		RayTraceResult rtResult = rayTrace(world, player, true);
		if (rtResult == null)
			return EnumActionResult.PASS;
		if (rtResult.typeOfHit == RayTraceResult.Type.BLOCK && !pos.equals(rtResult.getBlockPos()))
			if (LiquidUtil.drainBlockToContainer(world, rtResult.getBlockPos(), player, hand))
				return EnumActionResult.SUCCESS;

		int maxFoamBlocks = 0;
		ItemStack stack = StackUtil.get(player, hand);
		FluidStack fluid = FluidUtil.getFluidContained(stack);
		if (fluid != null && fluid.amount > 0)
			maxFoamBlocks += fluid.amount / getFluidPerFoam();
		ItemStack pack = player.inventory.armorInventory.get(2);
		if (!pack.isEmpty() && (pack.getItem() == ItemName.cf_pack.getInstance() || pack.getItem() == ModItems.exoCFPack)) {
			fluid = FluidUtil.getFluidContained(pack);
			if (fluid != null && fluid.amount > 0)
				maxFoamBlocks += fluid.amount / getFluidPerFoam();
			else
				pack = null;
		} else
			pack = null;
		if (maxFoamBlocks == 0)
			return EnumActionResult.FAIL;
		maxFoamBlocks = Math.min(maxFoamBlocks, getMaxFoamBlocks(stack));

		Target target = Target.Any;
		if (canPlaceFoam(world, pos, Target.Scaffold))
			target = Target.Scaffold;
		else if (canPlaceFoam(world, pos, Target.Cable))
			target = Target.Cable;
		else
			pos = pos.offset(side);

		Vec3d viewVec = player.getLookVec();
		EnumFacing playerViewFacing = EnumFacing.getFacingFromVector((float) viewVec.x, (float) viewVec.y, (float) viewVec.z);
		int amount = sprayFoam(world, pos, playerViewFacing.getOpposite(), target, maxFoamBlocks);
		amount *= getFluidPerFoam();
		if (amount > 0) {
			if (pack != null) {
				IFluidHandlerItem packHandler = FluidUtil.getFluidHandler(pack);
				assert packHandler != null;
				fluid = packHandler.drain(amount, true);
				amount -= fluid.amount;
				player.inventory.armorInventory.set(2, packHandler.getContainer());
			}
			if (amount > 0) {
				IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
				assert handler != null;
				handler.drain(amount, true);
				StackUtil.set(player, hand, handler.getContainer());
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	private int sprayFoam(World world, BlockPos pos, EnumFacing excludedDir, Target target, int maxFoamBlocks) {
		if (!canPlaceFoam(world, pos, target))
			return 0;
		Queue<BlockPos> toCheck = new ArrayDeque<BlockPos>();
		Set<BlockPos> positions = new HashSet<BlockPos>();
		toCheck.add(pos);
		BlockPos cPos;
		while ((cPos = toCheck.poll()) != null && positions.size() < maxFoamBlocks)
			if (canPlaceFoam(world, cPos, target))
				if (positions.add(cPos))
					for (EnumFacing dir : EnumFacing.VALUES) {
						if (dir != excludedDir)
							toCheck.add(cPos.offset(dir));
					}
		toCheck.clear();
		int failedPlacements = 0;
		for (BlockPos targetPos : positions) {
			IBlockState state = world.getBlockState(targetPos);
			Block targetBlock = state.getBlock();
			if (targetBlock == BlockName.scaffold.getInstance()) {
				BlockScaffold scaffold = (BlockScaffold) targetBlock;
				switch (state.getValue(scaffold.getTypeProperty())) {
				case wood:
				case reinforced_wood:
					scaffold.dropBlockAsItem(world, targetPos, state, 0);
					world.setBlockState(targetPos, BlockName.foam.getBlockState(BlockFoam.FoamType.normal));
					continue;
				case reinforced_iron:
					StackUtil.dropAsEntity(world, targetPos, BlockName.fence.getItemStack(BlockIC2Fence.IC2FenceType.iron));
				case iron:
					world.setBlockState(targetPos, BlockName.foam.getBlockState(BlockFoam.FoamType.reinforced));
					continue;
				}
				continue;
			}
			if (targetBlock == BlockName.te.getInstance()) {
				TileEntity te = world.getTileEntity(targetPos);
				if (te instanceof TileEntityCable && !((TileEntityCable) te).foam())
					failedPlacements++;
				continue;
			}
			if (!world.setBlockState(targetPos, BlockName.foam.getBlockState(BlockFoam.FoamType.normal)))
				failedPlacements++;
		}
		return positions.size() - failedPlacements;
	}

	private int getMaxFoamBlocks(ItemStack stack) {
		NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
		if (nbtData.getInteger("mode") == 0)
			return 10;
		return 1;
	}

	private int getFluidPerFoam() {
		return 100;
	}

	private static boolean canPlaceFoam(World world, BlockPos pos, Target target) {
		switch (target) {
		case Any:
			return BlockName.foam.getInstance().canPlaceBlockOnSide(world, pos, EnumFacing.DOWN);
		case Scaffold:
			return world.getBlockState(pos).getBlock() == BlockName.scaffold.getInstance();
		case Cable:
			if (world.getBlockState(pos).getBlock() != BlockName.te.getInstance())
				return false;
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityCable)
				return !((TileEntityCable) te).isFoamed();
			return false;
		}
		return false;
	}

	@Override
	public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new TankFluidHandlerItemStack(stack, CAPACITY);
	}

	protected void fillTank(ItemStack stack) {
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(stack);
		NBTTagCompound fluidTag = tag.getCompoundTag("Fluid");
		FluidStack fs = new FluidStack(FluidRegistry.getFluid("ic2construction_foam"), getCapacity(stack));
		fs.writeToNBT(fluidTag);
		tag.setTag("Fluid", fluidTag);
	}

	protected double getCharge(ItemStack stack) {
		FluidStack fs = FluidUtil.getFluidContained(stack);
		if (fs == null)
			return 0.0D;
		double ret = fs.amount;
		return ret > 0.0D ? ret : 0.0D;
	}

	public int getCapacity(ItemStack stack) {
		NBTTagCompound tag = ItemNBTHelper.getOrCreateNbtData(stack);
		if (tag.hasKey("addCapacity"))
			return CAPACITY + tag.getInteger("addCapacity");
		tag.setInteger("addCapacity", 0);
		return CAPACITY;
	}
}