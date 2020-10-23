package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemBodyQuantumAdvBatpack extends ItemArmorBaseQuantum {

	public ItemBodyQuantumAdvBatpack() {
		super(EntityEquipmentSlot.CHEST, 13000000, 12000, 4, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/quantum_adv.png";
	}

	@Override
	public int getItemTier() {
		return 4;
	}
}
