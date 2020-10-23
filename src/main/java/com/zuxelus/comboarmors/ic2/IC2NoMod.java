package com.zuxelus.comboarmors.ic2;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2NoMod extends CrossIC2 {

	@Override
	public IC2Type getType() {
		return IC2Type.NONE;
	}

	@Override
	public int getProfile() {
		return -1;
	}

	@Override
	public ItemStack getItemStack(String name) {
		return ItemStack.EMPTY;
	}

	@Override
	public Item getItem(String name) {
		return null;
	}

	@Override
	public ItemStack getChargedStack(ItemStack stack) {
		return ItemStack.EMPTY;
	}
}
