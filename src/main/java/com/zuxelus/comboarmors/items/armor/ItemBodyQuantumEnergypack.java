package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemBodyQuantumEnergypack extends ItemArmorBaseQuantum {

	public ItemBodyQuantumEnergypack(int renderIndex) {
		super(renderIndex, 1, 15000000, 12000, 4, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/quantum_energy.png";
	}

	@Override
	public int getItemTier() {
		return 5;
	}
}
