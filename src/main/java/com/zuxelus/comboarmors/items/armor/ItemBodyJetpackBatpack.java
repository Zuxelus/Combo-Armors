package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBodyJetpackBatpack extends ItemArmorBaseJetpack {

	public ItemBodyJetpackBatpack(int renderIndex) {
		super(renderIndex, 1, 100000, 100, 2, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/bat_jetpack.png";
	}

	@Override
	public int getItemTier() {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}
}
