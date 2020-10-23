package com.zuxelus.comboarmors.items;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIc2ca extends Item {
	EnumRarity rarity = EnumRarity.COMMON;

	public ItemIc2ca() {
		super();
		setCreativeTab(ComboArmors.creativeTab);
	}

	public ItemIc2ca(EnumRarity rarity) {
		this();
		this.rarity = rarity;
	}

	public ItemIc2ca(EnumRarity rarity, int stackSize) {
		this(rarity);
		setMaxStackSize(stackSize);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return rarity;
	}
}
