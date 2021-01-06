package com.zuxelus.comboarmors.tileentities;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.blocks.BlockArmorAssembler;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.items.IItemUpgrade;
import com.zuxelus.comboarmors.items.IItemUpgradeable;
import com.zuxelus.comboarmors.items.ItemUpgrade;
import com.zuxelus.comboarmors.items.armor.ItemArmorTankUtility;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes;
import com.zuxelus.comboarmors.recipes.RecipeHandler;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.zlib.tileentities.TileEntityEnergySink;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityArmorAssembler extends TileEntityEnergySink implements ITickable {
	public static final int SLOT_INPUT1 = 0;
	public static final int SLOT_INPUT2 = 1;
	public static final int SLOT_OUTPUT = 2;
	public static final int SLOT_UPGRADE = 3;
	public static final int SLOT_DISCHARGER = 4;
	public static final int TIER = 3;
	public static final int CAPACITY = 50000;
	public static final int OUTPUT = 512;
	public static final int CONSUMPTION = 32;
	public static final int TIMEFACTOR = 14;
	private int production;
	private int productionMax;
	private boolean active;

	public TileEntityArmorAssembler() {
		super("tile.armor_assembler.name", TIER, OUTPUT, CAPACITY);
		addedToEnet = false;
		active = false;
		production = 0;
		productionMax = 0;
	}

	public int getProduction() {
		return production;
	}

	public int getProductionFactor() {
		if (productionMax == 0)
			return 0;
		return (int) Math.round(production / productionMax * 24); //TODO
	}

	@SideOnly(Side.CLIENT)
	public String getTimeString() {
		if (productionMax <= 0)
			return I18n.format("info.remaining", "0:00:00");
		int mtime = productionMax / 280;
		int time = production / 280;
		int timeleft = mtime - time;
		int minutes = timeleft / 60;
		int hours = minutes / 60;
		minutes -= hours * 60;
		int seconds = timeleft - (minutes * 60 + hours * 3600);
		String s;
		if (seconds < 10)
			s = ":0";
		else
			s = ":";
		String s2;
		if (minutes < 10)
			s2 = ":0";
		else
			s2 = ":";
		return I18n.format("info.remaining", "" + hours + s2 + minutes + s + seconds);
	}

	public boolean getActive() {
		return active;
	}

	@Override
	public void onServerMessageReceived(NBTTagCompound tag) {
		if (!tag.hasKey("type"))
			return;
	}

	@Override
	public void onClientMessageReceived(NBTTagCompound tag) {
		if (!tag.hasKey("type"))
			return;
		switch (tag.getInteger("type")) {
		case 1:
			if (tag.hasKey("energy") && tag.hasKey("production")) {
				energy = tag.getDouble("energy");
				production = tag.getInteger("production");
			}
			break;
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		tag = writeProperties(tag);
		tag.setBoolean("active", active);
		return new SPacketUpdateTileEntity(getPos(), 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readProperties(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag = writeProperties(tag);
		updateActive();
		tag.setBoolean("active", active);
		return tag;
	}

	@Override
	protected void readProperties(NBTTagCompound tag) {
		super.readProperties(tag);
		if (tag.hasKey("production"))
			production = tag.getInteger("production");
		if (tag.hasKey("productionMax"))
			productionMax = tag.getInteger("productionMax");
		if (tag.hasKey("active"))
			active = tag.getBoolean("active");
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readProperties(tag);
	}

	@Override
	protected NBTTagCompound writeProperties(NBTTagCompound tag) {
		tag = super.writeProperties(tag);
		tag.setInteger("production", production);
		tag.setInteger("productionMax", productionMax);
		return tag;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		return writeProperties(super.writeToNBT(tag));
	}

	@Override
	public void onLoad() {
		if (!addedToEnet && world != null && !world.isRemote && Info.isIc2Available()) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnet = true;
			updateActive();
		}
	}

	@Override
	public void update() {
		if (world.isRemote)
			return;
		onLoad();
		handleDischarger(SLOT_DISCHARGER);
		ItemStack stack = getStackInSlot(SLOT_UPGRADE);
		if (!stack.isEmpty() && stack.getItem() == ModItems.creativeUpgrade && productionMax > 0) {
			combineItem();
			production = 0;
			updateState();
			return;
		}
		if (!active)
			return;
		if (energy >= CONSUMPTION) {
			energy -= CONSUMPTION;
			production += TIMEFACTOR;

			if (production >= productionMax) {
				combineItem();
				production = 0;
				updateState();
			}
		} else {
			energy = 0;
			production = 0;
			updateState();
		}
	}

	public void notifyBlockUpdate() {
		IBlockState iblockstate = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, iblockstate, iblockstate, 2);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (world == null || world.isRemote)
			return;
		updateState();
	}

	private void updateActive() {
		active = false;
		updateMaxProgress();
		if (energy < CONSUMPTION)
			return;
		active = canCombine();
	}

	private void updateState() {
		boolean old = active;
		int oldProductionMax = productionMax;
		updateActive();
		if (active != old) {
			production = 0;
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();
			if (!(block instanceof BlockArmorAssembler) || iblockstate.getValue(BlockArmorAssembler.ACTIVE) == active)
				return;
			IBlockState newState = block.getDefaultState()
				.withProperty(BlockArmorAssembler.FACING, iblockstate.getValue(BlockArmorAssembler.FACING))
				.withProperty(BlockArmorAssembler.ACTIVE, active);
			world.setBlockState(pos, newState, 3);
			return;
		}
		if (oldProductionMax != productionMax)
			notifyBlockUpdate();
	}

	private boolean canCombine() {
		ItemStack input1 = getStackInSlot(SLOT_INPUT1);
		ItemStack input2 = getStackInSlot(SLOT_INPUT2);
		if (input1.isEmpty() || input2.isEmpty())
			return false;
		ItemStack result = ArmorAssemblerRecipes.getAssemblyResult(input1, input2);
		if (result.isEmpty())
			return false;
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		if (output.isEmpty()) 
			return true;
		/*if (!output.isItemEqual(out)) // TODO
			return false;
		int result = this.outputSlot.get().stackSize + var1.stackSize;
		return (result <= getInventoryStackLimit()) && (result <= var1.getMaxStackSize());*/
		return false;
	}

	private void combineItem() {
		if (world.isRemote)
			return;
		if (!canCombine())
			return;
		ItemStack input1 = getStackInSlot(SLOT_INPUT1);
		ItemStack input2 = getStackInSlot(SLOT_INPUT2);
		ItemStack result = ArmorAssemblerRecipes.getAssemblyResult(input1, input2);
		ItemStack output = result.copy();

		RecipeHandler.onCrafting(output, this);

		int charge = 0;
		NBTTagCompound nbtout = ItemNBTHelper.getOrCreateNbtData(output);
		NBTTagCompound nbtin1 = ItemNBTHelper.getOrCreateNbtData(input1);
		NBTTagCompound nbtin2 = ItemNBTHelper.getOrCreateNbtData(input2);
		charge += nbtin1.getInteger("charge");
		charge += nbtin2.getInteger("charge");
		if (charge > nbtout.getInteger("maxCharge"))
			charge = nbtout.getInteger("maxCharge");
		nbtout.setInteger("charge", charge);
		RecipeHandler.updateElectricDamageBars(output);
		setInventorySlotContents(SLOT_OUTPUT, output);
		/*if (this.outputSlot.get() == null) {
			this.outputSlot.add(output);
		} else if (this.outputSlot.get().isItemEqual(output)) {
			this.outputSlot.get().stackSize += output.stackSize;
		}*/
		input1.shrink(1);
		if (input1.getCount() <= 0)
			setInventorySlotContents(SLOT_INPUT1, ItemStack.EMPTY);
		input2.shrink(1);
		if (input2.getCount() <= 0)
			setInventorySlotContents(SLOT_INPUT2, ItemStack.EMPTY);
	}

	private void updateMaxProgress() {
		ItemStack input1 = getStackInSlot(SLOT_INPUT1);
		ItemStack input2 = getStackInSlot(SLOT_INPUT2);
		ItemStack result = ArmorAssemblerRecipes.getAssemblyResult(input1, input2);
		if (result.isEmpty()) {
			productionMax = 0;
			return;
		}

		int upgradeSlot = -1;
		if (input1.getItem() instanceof IItemUpgrade || input1.isItemEqual(ComboArmors.ic2.getItemStack("overclockerUpgrade"))
				|| input1.isItemEqual(ComboArmors.ic2.getItemStack("energyStorageUpgrade")) || input1.isItemEqual(ComboArmors.ic2.getItemStack("transformerUpgrade")))
			upgradeSlot = 1;
		if (input2.getItem() instanceof IItemUpgrade || input2.isItemEqual(ComboArmors.ic2.getItemStack("overclockerUpgrade"))
				|| input2.isItemEqual(ComboArmors.ic2.getItemStack("energyStorageUpgrade")) || input2.isItemEqual(ComboArmors.ic2.getItemStack("transformerUpgrade")))
			upgradeSlot = 2;

		if (upgradeSlot == -1) {
			IItemUpgradeable item = (IItemUpgradeable) result.getItem();
			int tier = item.getItemTier();
			int mins = tier * 10;
			int secs = mins * 60;
			int ticks = secs * 20;
			productionMax = ticks * TIMEFACTOR;
		} else {
			if (result.getItem() instanceof IItemUpgradeable) {
				IItemUpgradeable item = (IItemUpgradeable) result.getItem();
				int modifier = 1;
				if (upgradeSlot == 1 && input1.getItem() instanceof ItemUpgrade)
					modifier = ((ItemUpgrade) input1.getItem()).getStackModifier(input1);
				if (upgradeSlot == 2 && input2.getItem() instanceof ItemUpgrade)
					modifier = ((ItemUpgrade) input2.getItem()).getStackModifier(input2);
				int tier = item.getItemTier();
				int mins = tier * 5;
				int secs = mins * 60;
				int ticks = secs * 20;
				int eu = ticks * TIMEFACTOR;
				int num = 0;
				if (upgradeSlot == 1)
					num = input1.getCount() * modifier;
				if (upgradeSlot == 2)
					num = input2.getCount() * modifier;
				double min = num * 1.875D;
				double sec = min * 60.0D;
				double tick = sec * 20.0D;
				int eu2 = (int) tick * TIMEFACTOR;
				productionMax = eu + eu2;
			} else if (result.getItem() instanceof ItemArmorTankUtility)
				productionMax = 15 * 60 * 20 * TIMEFACTOR;
		}

		ItemStack upgrade = getStackInSlot(SLOT_UPGRADE);
		if (!upgrade.isEmpty() && upgrade.getItem() == ModItems.overclockerUpgrade)
			productionMax = productionMax / (upgrade.getCount() + 1);
	}

	// ------- Inventory -------
	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return isItemValid(index, stack);
	}

	@Override
	public boolean isItemValid(int index, ItemStack stack) { // ISlotItemFilter
		switch (index) {
		case SLOT_INPUT1:
		case SLOT_INPUT2:
			return ArmorAssemblerRecipes.getItemList().contains(stack.getItem());
		case SLOT_UPGRADE:
			return stack.getItem() == ModItems.overclockerUpgrade || stack.getItem() == ModItems.creativeUpgrade;
		case SLOT_DISCHARGER:
			return ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, tier, true, true, true) > 0;
		case SLOT_OUTPUT:
		default:
			return false;
		}
	}

	// IEnergySink
	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		double old = energy;
		energy += amount;
		double left = 0.0;

		if (energy > capacity) {
			left = energy - capacity;
			energy = capacity;
		}
		if (energy > 0 && old == 0 && !world.isRemote)
			updateState();
		return left;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
