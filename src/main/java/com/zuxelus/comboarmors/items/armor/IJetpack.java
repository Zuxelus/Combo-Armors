package com.zuxelus.comboarmors.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IJetpack {

	void onFlyKeyPressed(EntityPlayer player, ItemStack stack);
}