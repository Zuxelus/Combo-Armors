package com.zuxelus.comboarmors.items;

import java.util.List;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.entities.EntityTechArrow;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemNanoBow extends ItemBow implements IElectricItem, IItemUpgradeable {
	static final int NORMAL = 1;
	static final int RAPID = 2;
	static final int SPREAD = 3;
	static final int SNIPER = 4;
	static final int FLAME = 5;
	static final int EXPLOSIVE = 6;
	static final int[] CHARGE = { 300, 150, 400, 1000, 200, 800 };
	static final String[] MODE = { "normal", "rapidfire", "spread", "sniper", "flame", "explosive" };
	public IIcon[] icons;

	public ItemNanoBow() {
		super();
		setMaxDamage(27);
		setFull3D();
		setCreativeTab(ComboArmors.creativeTab);
		icons = new IIcon[4];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		IElectricItem item = (IElectricItem) stack.getItem();
		if (!nbt.getBoolean("loaded")) {
			if (nbt.getInteger("tier") == 0)
				nbt.setInteger("tier", item.getTier(stack));
			if (nbt.getDouble("transferLimit") == 0)
				nbt.setDouble("transferLimit", item.getTransferLimit(stack));
			if (nbt.getDouble("maxCharge") == 0)
				nbt.setDouble("maxCharge", item.getMaxCharge(stack));
			nbt.setBoolean("loaded", true);
		}
		if (nbt.getDouble("transferLimit") != item.getTransferLimit(stack))
			tooltip.add(String.format(I18n.format("info.transferspeed"), nbt.getDouble("transferLimit")));
		if (nbt.getInteger("tier") != item.getTier(stack))
			tooltip.add(String.format(I18n.format("info.chargingtier"), nbt.getInteger("tier")));
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List items) {
		ItemStack charged = new ItemStack(this, 1);
		ElectricItem.manager.charge(charged, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
		items.add(charged);
		items.add(new ItemStack(this, 1, getMaxDamage()));
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		icons[0] = Util.register(ir, this);
		icons[1] = Util.register(ir, Util.getFileName(this) + "_1");
		icons[2] = Util.register(ir, Util.getFileName(this) + "_2");
		icons[3] = Util.register(ir, Util.getFileName(this) + "_3");
		itemIcon = icons[0];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem != null && usingItem.getItem() == ModItems.nanoBow) {
			NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
			int mode = nbt.getInteger("bowMode");
			int i1 = 18;
			int i2 = 13;
			if (mode == SNIPER || mode == EXPLOSIVE) {
				i1 = 36;
				i2 = 26;
			} else if (mode == RAPID) {
				i1 = 5;
				i2 = 3;
			}
			int k = usingItem.getMaxItemUseDuration() - useRemaining;
			if (k >= i1)
				return icons[3];
			if (k > i2)
				return icons[2];
			if (k > 0)
				return icons[1];
		}
		return icons[0];
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");

		int charge = getMaxItemUseDuration(stack) - timeLeft;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, charge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		charge = event.charge;
		if (mode == SNIPER || mode == EXPLOSIVE)
			charge /= 2;
		if (mode == RAPID)
			charge *= 4;
		float f = getArrowVelocity(charge);
		if (f < 0.1)
			return;

		if (!world.isRemote) {
			EntityTechArrow arrow = new EntityTechArrow(world, player, f * 2.0F);

			if (f == 1.5F)
				arrow.setIsCritical(true);

			int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			if (j > 0)
				arrow.setDamage(arrow.getDamage() + j * 0.5D + 0.5D);
			if (mode == NORMAL && arrow.getIsCritical())
				j += 3;
			else if (mode == RAPID && arrow.getIsCritical())
				j += 1;
			else if (mode == SNIPER && arrow.getIsCritical())
				j += 8;
			if (j > 0)
				arrow.setDamage(arrow.getDamage() + j * 0.5D + 0.5D);
		if (ComboArmors.config.nanoBowBoost > 0)
			arrow.setDamage(arrow.getDamage() + ComboArmors.config.nanoBowBoost * 0.5D + 0.5D);
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			if (mode == NORMAL && arrow.getIsCritical())
				k += 1;
			else if (mode == SNIPER && arrow.getIsCritical())
				k += 5;
			if (k > 0)
				arrow.setKnockbackStrength(k);

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
				arrow.setFire(100);
			if (mode == FLAME && arrow.getIsCritical())
				arrow.setFire(2000);
			if (mode == EXPLOSIVE && arrow.getIsCritical())
				arrow.setExplosive(true);

			arrow.canBePickedUp = 2;

			switch (mode) {
			case NORMAL:
			case RAPID:
			case SNIPER:
			case FLAME:
			case EXPLOSIVE:
				ElectricItem.manager.use(stack, CHARGE[mode - 1], player);
				world.spawnEntityInWorld(arrow);
				break;
			case SPREAD:
				ElectricItem.manager.use(stack, 350, player);
				world.spawnEntityInWorld(arrow);
				if (arrow.getIsCritical()) {
					EntityTechArrow arrow2 = new EntityTechArrow(world, player, f * 2.0F);
					arrow2.setPosition(arrow2.posX + 0.25D, arrow2.posY, arrow2.posZ);
					arrow2.setIsCritical(true);
					arrow2.canBePickedUp = 2;
					EntityTechArrow arrow3 = new EntityTechArrow(world, player, f * 2.0F);
					arrow3.setPosition(arrow3.posX, arrow3.posY + 0.25D, arrow3.posZ);
					arrow3.setIsCritical(true);
					arrow3.canBePickedUp = 2;
					EntityTechArrow arrow4 = new EntityTechArrow(world, player, f * 2.0F);
					arrow4.setPosition(arrow4.posX - 0.25D, arrow4.posY, arrow4.posZ);
					arrow4.setIsCritical(true);
					arrow4.canBePickedUp = 2;
					EntityTechArrow arrow5 = new EntityTechArrow(world, player, f * 2.0F);
					arrow5.setPosition(arrow5.posX, arrow5.posY - 0.25D, arrow5.posZ);
					arrow5.setIsCritical(true);
					arrow5.canBePickedUp = 2;
					world.spawnEntityInWorld(arrow2);
					world.spawnEntityInWorld(arrow3);
					world.spawnEntityInWorld(arrow4);
					world.spawnEntityInWorld(arrow5);
				}
				break;
			}
		}

		world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		return f > 1.5F ? 1.5F : f;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		switch (nbt.getInteger("bowMode")) {
		case SNIPER:
		case EXPLOSIVE:
			return 144000;
		case RAPID:
			return 18000;
		default:
			return 72000;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, stack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");
		if (IC2.keyboard.isModeSwitchKeyDown(player) && nbt.getByte("toggleTimer") == 0) {
			if (!world.isRemote) {
				byte toggle = 10;
				nbt.setByte("toggleTimer", toggle);

				mode++;
				if (mode == RAPID && !ComboArmors.config.rapidFireMode)
					mode++;
				if (mode == SPREAD && !ComboArmors.config.spreadMode)
					mode++;
				if (mode == SNIPER && !ComboArmors.config.sniperMode)
					mode++;
				if (mode == FLAME && !ComboArmors.config.flameMode)
					mode++;
				if (mode == EXPLOSIVE && !ComboArmors.config.explosiveMode)
					mode++;
				if (mode > EXPLOSIVE)
					mode -= EXPLOSIVE;
				nbt.setInteger("bowMode", mode);
				player.addChatMessage(new ChatComponentTranslation("info.nanobow." + MODE[mode - 1]));
			}
		} else if (player.capabilities.isCreativeMode || ElectricItem.manager.canUse(stack, CHARGE[mode - 1]))
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		byte toggle = nbt.getByte("toggleTimer");
		if (toggle > 0)
			nbt.setByte("toggleTimer", --toggle);
		int mode = nbt.getInteger("bowMode");
		if (mode == 0)
			nbt.setInteger("bowMode", 1);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int i) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		int mode = nbt.getInteger("bowMode");
		if (mode == RAPID) {
			int j = getMaxItemUseDuration(stack) - i;
			if (j >= 10 && ElectricItem.manager.canUse(stack, CHARGE[RAPID - 1]))
				player.stopUsingItem();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	// IElectricItem
	@Override
	public boolean canProvideEnergy(ItemStack is) {
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getDouble("maxCharge") == 0)
			nbt.setDouble("maxCharge", getDefaultMaxCharge());
		return nbt.getDouble("maxCharge");
	}

	@Override
	public int getTier(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getInteger("tier") == 0)
			nbt.setInteger("tier", getDefaultTier());
		return nbt.getInteger("tier");
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		NBTTagCompound nbt = ItemNBTHelper.getOrCreateNbtData(stack);
		if (nbt.getDouble("transferLimit") == 0)
			nbt.setDouble("transferLimit", getDefaultTransferLimit());
		return nbt.getDouble("transferLimit");
	}

	@Override
	public Item getChargedItem(ItemStack stack) {
		return stack == null ? null : stack.getItem();
	}

	@Override
	public Item getEmptyItem(ItemStack stack) {
		return stack == null ? null : stack.getItem();
	}

	// IItemUpgradeable
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
	public int getItemTier() {
		return 3;
	}

	@Override
	public int getMaxUpgradeableCharge() {
		return ComboArmors.config.maxEnergyUpgrades - getDefaultMaxCharge();
	}

	@Override
	public int getMaxUpgradeableTransfer() {
		return ComboArmors.config.maxTransferUpgrades - getDefaultTransferLimit();
	}
}
