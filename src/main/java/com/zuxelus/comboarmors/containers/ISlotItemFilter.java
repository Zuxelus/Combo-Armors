package com.zuxelus.comboarmors.containers;

import net.minecraft.item.ItemStack;

public interface ISlotItemFilter {
	boolean isItemValid(int slotIndex, ItemStack itemStack);
}
