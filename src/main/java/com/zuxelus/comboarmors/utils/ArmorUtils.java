package com.zuxelus.comboarmors.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.zuxelus.comboarmors.ComboArmors;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.Ic2Items;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.item.ItemTinCan;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ArmorUtils
{




	public static boolean doStatic(EntityPlayer player, ItemStack stack) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		int prod = nbt.getInteger("staticProd");
		boolean isNotWalking = player.ridingEntity != null || player.isInWater();
		if (!nbt.hasKey("x") || isNotWalking)
			nbt.setInteger("x", (int) player.posX);
		if (!nbt.hasKey("z") || isNotWalking)
			nbt.setInteger("z", (int) player.posZ);
		double distance = Math.sqrt((nbt.getInteger("x") - player.posX) * (nbt.getInteger("x") - player.posX) + (nbt.getInteger("z") - player.posZ) * (nbt.getInteger("z") - player.posZ));
		if (distance < 5.0D)
			return false;

		nbt.setInteger("x", (int) player.posX);
		nbt.setInteger("z", (int) player.posZ);

		boolean result = false;
		ItemStack[] armor = player.inventory.armorInventory;
		if (tryChargeStatic(armor, ComboArmors.config.stPriority[0], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[1], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[2], distance, prod))
			result = true;
		else if (tryChargeStatic(armor, ComboArmors.config.stPriority[3], distance, prod))
			result = true;
		return result;
	}

	private static boolean tryChargeStatic(ItemStack[] armor, int slot, double distance, int prod) {
		if (armor[slot] == null || !(armor[slot].getItem() instanceof IElectricItem))
			return false;
		return ElectricItem.manager.charge(armor[slot], Math.min(3, (int) distance / 5) + prod, Integer.MAX_VALUE, true, false) > 0;
	}
}
