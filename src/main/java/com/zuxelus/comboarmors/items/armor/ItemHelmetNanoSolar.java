package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.ArmorUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHelmetNanoSolar extends ItemArmorElectricUtility implements IMetalArmor {
	public ItemHelmetNanoSolar(int renderIndex) {
		super(renderIndex, 0, 1000000, 1000, 3, false);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/solar_nano.png";
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
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getItemTier() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack1, EntityPlayer player) {
		return true;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		boolean updated = false;
		if (onHelmetSolarTick(player, stack))
			updated = true;
		if (onNightvisionTick(player, stack))
			updated = true;
		if (updated)
			player.inventoryContainer.detectAndSendChanges();
	}
}
