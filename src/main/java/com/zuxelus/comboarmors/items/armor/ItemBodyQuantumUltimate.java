package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBodyQuantumUltimate extends ItemArmorBaseQuantum {

	public ItemBodyQuantumUltimate() {
		super(EntityEquipmentSlot.CHEST, 18000000, 12000, 4, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/ultimate_quantum.png";
	}

	@Override
	public int getItemTier() {
		return 6;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack var1) {
		return EnumRarity.EPIC;
	}
}
