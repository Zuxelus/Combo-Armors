package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.entity.EntityTechArrow;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemNanoBow extends Item implements IElectricItem, IItemUpgradeable {
	public IIcon[] icons;

	public ItemNanoBow()
	{
		super();
		maxStackSize = 1;
		setMaxDamage(27);
		setFull3D();
		setCreativeTab(ComboArmors.creativeTab);
		icons = new IIcon[4];
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		IElectricItem elec = (IElectricItem)stack.getItem();
		if (!nbt.getBoolean("loaded")) {
			if (nbt.getInteger("tier") == 0)
				nbt.setInteger("tier", elec.getTier(stack));
			if (nbt.getDouble("transferLimit") == 0)
				nbt.setDouble("transferLimit", elec.getTransferLimit(stack));
			if (nbt.getDouble("maxCharge") == 0)
				nbt.setDouble("maxCharge", elec.getMaxCharge(stack));
			nbt.setBoolean("loaded", true);
		}
		if (nbt.getDouble("transferLimit") != elec.getTransferLimit(stack))
			info.add(String.format(StatCollector.translateToLocal("info.transferspeed"), nbt.getDouble("transferLimit")));
		if (nbt.getInteger("tier") != elec.getTier(stack))
			info.add(String.format(StatCollector.translateToLocal("info.chargingtier"), nbt.getInteger("tier")));
	}

	@Override
	public boolean canProvideEnergy(ItemStack is) {
		return false;
	}

	@Override
	public Item getChargedItem(ItemStack is) {
		return this;
	}

	@Override
	public int getDefaultMaxCharge() {
		return 40000;
	}

	@Override
	public int getDefaultTier() {
		return 2;
	}

	@Override
	public int getDefaultTransferLimit() {
		return 128;
	}

	@Override
	public Item getEmptyItem(ItemStack is) {
		return this;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem != null && usingItem.getItem() == ModItems.nanoBow) {
			NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
			int mode = nbt.getInteger("bowMode");
			int i1 = 18;
			int i2 = 13;
			if (mode == 4 || mode == 6) {
				i1 = 36;
				i2 = 26;
			} else if (mode == 2) {
				i1 = 5;
				i2 = 3;
			}
			int k = usingItem.getMaxItemUseDuration() - useRemaining;
			if (k >= i1)
				return this.icons[3];
			if (k > i2)
				return this.icons[2];
			if (k > 0)
				return this.icons[1];
		}
		return this.icons[0];
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getItemTier() {
		return 3;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (nbt.getDouble("maxCharge") == 0)
			nbt.setDouble("maxCharge", getDefaultMaxCharge());
		return nbt.getDouble("maxCharge");
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");
		if ((mode == 4) || (mode == 6))
			return 144000;
		if (mode == 2)
			return 18000;
		return 72000;
	}

	@Override
	public int getMaxUpgradeableCharge() {
		return ComboArmors.config.maxEnergyUpgrades - getDefaultMaxCharge();
	}

	@Override
	public int getMaxUpgradeableTransfer() {
		return ComboArmors.config.maxTransferUpgrades - getDefaultTransferLimit();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
		list.add(charged);
		list.add(new ItemStack(this, 1, getMaxDamage()));
	}

	@Override
	public int getTier(ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (nbt.getInteger("tier") == 0)
			nbt.setInteger("tier", getDefaultTier());
		return nbt.getInteger("tier");
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (nbt.getDouble("transferLimit") == 0)
			nbt.setDouble("transferLimit", getDefaultTransferLimit());
		return nbt.getDouble("transferLimit");
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving,
			EntityLivingBase par3EntityLiving) {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World world, Block block, int par4, int par5, int par6,
			EntityLivingBase par7EntityLiving) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if (IC2.keyboard.isModeSwitchKeyDown(player) && nbt.getByte("toggleTimer") == 0) {
			if (IC2.platform.isSimulating()) {
				byte toggle = 10;
				nbt.setByte("toggleTimer", toggle);

				int mode = nbt.getInteger("bowMode");
				mode++;
				if (mode == 2 && !ComboArmors.config.rapidFireMode)
					mode++;
				if (mode == 3 && !ComboArmors.config.spreadMode)
					mode++;
				if (mode == 4 && !ComboArmors.config.sniperMode)
					mode++;
				if (mode == 5 && !ComboArmors.config.flameMode)
					mode++;
				if (mode == 6 && !ComboArmors.config.explosiveMode)
					mode++;
				if (mode > 6)
					mode -= 6;
				nbt.setInteger("bowMode", mode);

				String[] name = { StatCollector.translateToLocal("info.normal"),
						StatCollector.translateToLocal("info.rapidfire"), StatCollector.translateToLocal("info.spread"),
						StatCollector.translateToLocal("info.sniper"), StatCollector.translateToLocal("info.flame"),
						StatCollector.translateToLocal("info.explosive") };
				IC2.platform.messagePlayer(player, String.format(StatCollector.translateToLocal("info.mode_enabled"), name[(mode - 1)]));
			}
		} else if (player.capabilities.isCreativeMode || ElectricItem.manager.canUse(stack, 100))
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");

		int var6 = getMaxItemUseDuration(stack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, var6);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		var6 = event.charge;
		if ((mode == 4) || (mode == 6))
			var6 /= 2;
		if (mode == 2)
			var6 *= 4;
		float var7 = var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
		if (var7 < 0.1D)
			return;
		if (var7 > 1.0F)
			var7 = 1.0F;
		EntityTechArrow var8 = new EntityTechArrow(world, player, var7 * 2.0F);
		EntityTechArrow arrow2 = new EntityTechArrow(world, player, var7 * 2.0F);
		arrow2.setPosition(arrow2.posX, arrow2.posY + 0.5D, arrow2.posZ);
		arrow2.canBePickedUp = 2;
		EntityTechArrow arrow3 = new EntityTechArrow(world, player, var7 * 2.0F);
		arrow3.setPosition(arrow3.posX, arrow3.posY + 0.25D, arrow3.posZ);
		arrow3.canBePickedUp = 2;
		EntityTechArrow arrow4 = new EntityTechArrow(world, player, var7 * 2.0F);
		arrow4.setPosition(arrow4.posX, arrow4.posY - 0.25D, arrow4.posZ);
		arrow4.canBePickedUp = 2;
		EntityTechArrow arrow5 = new EntityTechArrow(world, player, var7 * 2.0F);
		arrow5.setPosition(arrow5.posX, arrow5.posY - 0.5D, arrow5.posZ);
		arrow5.canBePickedUp = 2;
		if (var7 == 1.0F)
			var8.setIsCritical(true);
		int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
		if (mode == 1 && var8.getIsCritical())
			var9 += 3;
		else if (mode == 4 && var8.getIsCritical())
			var9 += 10;
		if (var9 > 0)
			var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
		if (ComboArmors.config.nanoBowBoost > 0)
			var8.setDamage(var8.getDamage() + ComboArmors.config.nanoBowBoost * 0.5D + 0.5D);
		int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
		if (mode == 1 && var8.getIsCritical())
			var10 += 1;
		else if (mode == 4 && var8.getIsCritical())
			var10 += 5;
		if (var10 > 0)
			var8.setKnockbackStrength(var10);
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
			var8.setFire(100);
		if (mode == 5 && var8.getIsCritical())
			var8.setFire(2000);
		if (mode == 6 && var8.getIsCritical())
			var8.setExplosive(true);
		world.playSoundAtEntity(player, "random.bow", 1.0F,
				1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
		var8.canBePickedUp = 2;
		if (!world.isRemote) {
			if (mode == 1) {
				ElectricItem.manager.use(stack, 100, player);
				world.spawnEntityInWorld(var8);
			} else if (mode == 2) {
				ElectricItem.manager.use(stack, 50, player);
				world.spawnEntityInWorld(var8);
			} else if (mode == 3) {
				ElectricItem.manager.use(stack, 250, player);
				world.spawnEntityInWorld(var8);
				if (var8.getIsCritical()) {
					world.spawnEntityInWorld(arrow2);
					world.spawnEntityInWorld(arrow3);
					world.spawnEntityInWorld(arrow4);
					world.spawnEntityInWorld(arrow5);
				}
			} else if (mode == 4) {
				ElectricItem.manager.use(stack, 250, player);
				world.spawnEntityInWorld(var8);
			} else if (mode == 5) {
				ElectricItem.manager.use(stack, 100, player);
				world.spawnEntityInWorld(var8);
			} else if (mode == 6) {
				ElectricItem.manager.use(stack, 250, player);
				world.spawnEntityInWorld(var8);
			}
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		byte toggle = nbt.getByte("toggleTimer");
		if (toggle > 0) {
			toggle = (byte) (toggle - 1);
			nbt.setByte("toggleTimer", toggle);
		}
		int mode = nbt.getInteger("bowMode");
		if (mode == 0)
			nbt.setInteger("bowMode", 1);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int i) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");
		if (mode == 2) {
			int j = getMaxItemUseDuration(stack) - i;
			if (j >= 5 && ElectricItem.manager.canUse(stack, 50))
				player.stopUsingItem();
		}
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		icons[0] = Util.register(ir, this);
		icons[1] = Util.register(ir, Util.getFileName(this) + "_1");
		icons[2] = Util.register(ir, Util.getFileName(this) + "_2");
		icons[3] = Util.register(ir, Util.getFileName(this) + "_3");
		itemIcon = icons[0];
	}
}
