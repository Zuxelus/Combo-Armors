package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemArmorExoBatpack extends ItemArmorElectricUtility {

	public ItemArmorExoBatpack(int renderIndex) {
		super(renderIndex, 1, 60000, 100, 1, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/exo_batpack.png";
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
		return 1;
	}
}
