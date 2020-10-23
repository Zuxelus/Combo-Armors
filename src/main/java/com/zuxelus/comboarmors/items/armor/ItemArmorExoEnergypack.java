package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorExoEnergypack extends ItemArmorElectricUtility {

	public ItemArmorExoEnergypack() {
		super(EntityEquipmentSlot.CHEST, 2000000, 2500, 4, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_energypack.png";
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 0.0D;
	}

	@Override
	public int getEnergyPerDamage() {
		return 0;
	}

	@Override
	public int getItemTier() {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}
}
