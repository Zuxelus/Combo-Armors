package com.zuxelus.comboarmors.ic2;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public abstract class CrossIC2 {
	public enum IC2Type{
		SPEIGER,
		EXP,
		NONE
	}

	public abstract IC2Type getType();
	
	public abstract int getProfile();

	public abstract ItemStack getItemStack(String name);

	public abstract Item getItem(String name);

	public abstract ItemStack getChargedStack(ItemStack stack);

	public static CrossIC2 getMod() {
		try {
			if (Loader.isModLoaded("ic2-classic-spmod")) {
				Class clz = Class.forName("com.zuxelus.comboarmors.ic2.IC2Classic");
				if (clz != null)
					return (CrossIC2) clz.newInstance();
			}
			if (Loader.isModLoaded("ic2")) {
				Class clz = Class.forName("com.zuxelus.comboarmors.ic2.IC2Exp");
				if (clz != null)
					return (CrossIC2) clz.newInstance();
			}
		} catch (Exception e) { }
		return new IC2NoMod();
	}
}
