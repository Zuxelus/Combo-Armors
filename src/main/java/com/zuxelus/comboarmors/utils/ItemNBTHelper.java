package com.zuxelus.comboarmors.utils;

import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemNBTHelper {

	public static NBTTagCompound getOrCreateNbtData(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		return tag;
	}

	public static int getCharge(ItemStack stack) {
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		return tag.getInteger("charge");
	}

	public static void setCharge(ItemStack stack, int value) {
		NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
		tag.setInteger("charge", value);
	}
}
