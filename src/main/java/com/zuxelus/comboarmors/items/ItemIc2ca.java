package com.zuxelus.comboarmors.items;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.utils.Util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIc2ca extends Item {
	EnumRarity rarity = EnumRarity.common;

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

	@Override
	public void registerIcons(IIconRegister ir) {
		itemIcon = Util.register(ir, this);
	}
}
