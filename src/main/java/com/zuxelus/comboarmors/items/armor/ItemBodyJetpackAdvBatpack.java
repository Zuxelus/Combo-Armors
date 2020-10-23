package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBodyJetpackAdvBatpack extends ItemArmorBaseJetpack {

	public ItemBodyJetpackAdvBatpack() {
		super(EntityEquipmentSlot.CHEST, 650000, 1000, 3, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/adv_jetpack.png";
	}

	@Override
	public int getItemTier() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}
}
