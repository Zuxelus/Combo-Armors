package com.zuxelus.comboarmors;

import com.zuxelus.comboarmors.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs {
	private static ItemStack stack;

	public CreativeTab() {
		super(ComboArmors.NAME);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		if (stack == null)
			stack = new ItemStack(ModItems.quantumUltimate);
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.quantumUltimate);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return ComboArmors.NAME;
	}
}
