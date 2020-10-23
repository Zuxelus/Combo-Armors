package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;

import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorExoNano extends ItemArmorElectricUtility implements IMetalArmor {

	public ItemArmorExoNano(EntityEquipmentSlot slot) {
		super(slot, 1000000, 1600, 3, false);
		if (slot == EntityEquipmentSlot.FEET) // boots
			MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ModItems.exoNanoHelm || stack.getItem() == ModItems.exoNanoChest || stack.getItem() == ModItems.exoNanoBoots)
			return ComboArmors.MODID + ":textures/armor/exo_nano_1.png";
		return ComboArmors.MODID + ":textures/armor/exo_nano_2.png";
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 0.9D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 5000;
	}

	@Override
	public int getItemTier() {
		return 3;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source == DamageSource.FALL && armorType == EntityEquipmentSlot.FEET) {
			int energyPerDamage = getEnergyPerDamage();
			int damageLimit = Integer.MAX_VALUE;
			if (energyPerDamage > 0)
				damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
			return new ArmorProperties(10, damage < 8.0D ? 1.0D : 0.875D, damageLimit);
		}
		return super.getProperties(entity, armor, source, damage, slot);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (armorType == EntityEquipmentSlot.HEAD)
			if (onNightvisionTick(player, stack))
				player.inventoryContainer.detectAndSendChanges();
	}

	@SubscribeEvent
	public void onEntityLivingFallEvent(LivingFallEvent event) {
		onFall(event, false);
	}
}
