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

public class ItemBodyNanoAdvBatpack extends ItemArmorElectricUtility implements IMetalArmor {

	public ItemBodyNanoAdvBatpack() {
		super(EntityEquipmentSlot.CHEST, 4000000, 1000, 4, true);
	}

	public boolean canProvideEnergy() {
		return true;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return ComboArmors.MODID + ":textures/armor/nano_adv.png";
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
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}

	@Override
	public boolean isMetalArmor(ItemStack stack1, EntityPlayer stack2) {
		return true;
	}
}
