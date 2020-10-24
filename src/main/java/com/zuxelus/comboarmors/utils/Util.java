package com.zuxelus.comboarmors.utils;

import java.util.Random;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class Util {
	public static String getFileName(Block block) {
		return block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
	}

	public static String getFileName(Item item) {
		return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
	}

	public static IIcon register(IIconRegister ir, Item i) {
		return register(ir, getFileName(i));
	}

	public static IIcon register(IIconRegister ir, String s) {
		return ir.registerIcon(ComboArmors.MODID + ":" + s);
	}

	public static IIcon[] registerSides(IIconRegister ir, Block block) {
		IIcon[] icon = new IIcon[4];
		icon[0] = register(ir, getFileName(block) + "_bottom");
		icon[1] = register(ir, getFileName(block) + "_top");
		icon[2] = register(ir, getFileName(block) + "_front");
		icon[3] = register(ir, getFileName(block) + "_side");
		return icon;
	}
}
