package com.zuxelus.comboarmors.utils;

import java.util.Random;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Util {
	public static String getFileName(Block block) {
		return block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf(".") + 1);
	}

	public static String getFileName(Item item) {
		return item.getUnlocalizedName().substring(item.getUnlocalizedName().indexOf(".") + 1);
	}
}
