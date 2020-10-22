package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBodyJetpackEnergypack extends ItemArmorBaseJetpack {
	public ItemBodyJetpackEnergypack(int renderIndex) {
		super(renderIndex, 1, 2100000, 1000, 4, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/energy_jetpack.png";
	}

	@Override
	public int getItemTier() {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}
}
