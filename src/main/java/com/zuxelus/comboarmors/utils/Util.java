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

	public static IIcon register(IIconRegister ir, Block b) {
		return register(ir, getFileName(b));
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

	public static void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof IInventory))
			return;

		IInventory inventory = (IInventory) te;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null && stack.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem item = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
				if (stack.hasTagCompound())
					item.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				float factor = 0.05F;
				item.motionX = rand.nextGaussian() * factor;
				item.motionY = rand.nextGaussian() * factor + 0.2F;
				item.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(item);
				stack.stackSize = 0;
			}
		}
	}
}
