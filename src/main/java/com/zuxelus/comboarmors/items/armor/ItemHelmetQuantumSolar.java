package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHelmetQuantumSolar extends ItemArmorElectricUtility implements IMetalArmor {

	public ItemHelmetQuantumSolar() {
		super(EntityEquipmentSlot.HEAD, 10000000, 12000, 4, false);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/solar_quantum.png";
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 1.0D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 20000;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getItemTier() {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		boolean updated = false;
		if (onHelmetSolarTick(player, stack))
			updated = true;
		if (onHelmetTick(player, stack))
			updated = true;
		if (onNightvisionTick(player, stack))
			updated = true;
		if (updated)
			player.inventoryContainer.detectAndSendChanges();
	}
}
