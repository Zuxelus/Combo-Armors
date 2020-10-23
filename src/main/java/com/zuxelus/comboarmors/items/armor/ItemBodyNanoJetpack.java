package com.zuxelus.comboarmors.items.armor;

import com.zuxelus.comboarmors.ComboArmors;

import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBodyNanoJetpack extends ItemArmorBaseJetpack implements IMetalArmor {

	public ItemBodyNanoJetpack() {
		super(EntityEquipmentSlot.CHEST, 1500000, 160, 3, false);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return ComboArmors.MODID + ":textures/armor/nano_jet.png";
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
	public int getItemTier() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack var1) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean isMetalArmor(ItemStack var1, EntityPlayer var2) {
		return true;
	}
}
