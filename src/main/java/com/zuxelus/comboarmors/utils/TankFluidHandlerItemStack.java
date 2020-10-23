package com.zuxelus.comboarmors.utils;

import ic2.core.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class TankFluidHandlerItemStack extends FluidHandlerItemStack {

	public TankFluidHandlerItemStack(ItemStack container, int capacity) {
		super(container, capacity);
	}
}
