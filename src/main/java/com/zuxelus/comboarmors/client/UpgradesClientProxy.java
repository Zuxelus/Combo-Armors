package com.zuxelus.comboarmors.client;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.network.ChannelHandler;
import com.zuxelus.comboarmors.network.PacketCloakKeyPressed;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.client.FMLClientHandler;
import ic2.api.item.ElectricItem;
import ic2.core.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;

public class UpgradesClientProxy
{	
	public static boolean firstLoad = false;
	private static int lastKeyState = 0;
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	public static int icBoostKeyID;

	/*public static boolean firstLoadClient(EntityPlayer player, ItemStack stack) {
		if (ItemNBTHelper.readFlyStatus(stack)) {
			ItemNBTHelper.saveFlyStatus(stack, false);
			switchFlyModeClient(player, stack);
		}
		return true;
	}*/

	public static void overcharge(EntityPlayer player, ItemStack stack, double x, double y, double z) {
		int charge = ItemNBTHelper.getCharge(stack);
		int maxcharge = StackUtil.getOrCreateNbtData(stack).getInteger("maxCharge");
		int overchargenumber = maxcharge / 10;
		int boltnumber = overchargenumber / 10000;
		if (boltnumber >= 1) {
			if (boltnumber >= 10)
				boltnumber = 10;
			if (charge >= boltnumber * 10000) {
				for (int i = 1; i <= boltnumber; i++) {
					EntityLightningBolt elb = new EntityLightningBolt(player.worldObj, x, y, z);
					player.worldObj.spawnEntityInWorld(elb);
					ElectricItem.manager.discharge(stack, 10000, 4, true, false, false);
				}
			}
		}
	}

	/*public static boolean switchFlyModeClient(EntityPlayer player, ItemStack stack) {
		if (ItemNBTHelper.readFlyStatus(stack)) {
			if (!player.capabilities.isCreativeMode) {
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
			}
			player.addChatMessage(new ChatComponentTranslation("Flight Turbine disabled."));
			ClientTickHandler.isFlyActiveByMod = false;
			ItemNBTHelper.saveFlyStatus(stack, false);
		} else {
			if (ItemNBTHelper.getCharge(stack) < 10 && !player.capabilities.isCreativeMode)
				player.addChatMessage(new ChatComponentTranslation("Not enough energy to enable flight mode!"));
			else {
				player.addChatMessage(new ChatComponentTranslation("Flight Turbine enabled."));
				player.capabilities.allowFlying = true;
				player.capabilities.isFlying = true;
				ClientTickHandler.isFlyActiveByMod = true;
				ItemNBTHelper.saveFlyStatus(stack, true);
			}
		}
		return true;
	}

	public static boolean onFlyTickClient(EntityPlayer player, ItemStack stack) {
		if (firstLoad) {
			ChannelHandler.network.sendToServer(new PacketWorldLoad());
			firstLoad = false;
			return true;
		}
		if (ClientTickHandler.isLastFlyUndressed) {
			ItemNBTHelper.saveFlyStatus(stack, false);
			ClientTickHandler.isLastFlyUndressed = false;
		}
		if (ItemNBTHelper.readFlyStatus(stack)) {
			if (!player.capabilities.isCreativeMode) {
				if (ItemNBTHelper.getCharge(stack) < 10) {
					player.addChatMessage(new ChatComponentTranslation("Out of energy!"));
					switchFlyModeClient(player, stack);
				} else if (player.capabilities.isFlying)
					ElectricItem.manager.discharge(stack, ComboArmors.config.turbineEUAmount, 4, true, false, false);
			}
			//KeyboardClient.sendKeyUpdate(player);
			player.fallDistance = 0.0F;
		}
		return true;
	}*/
}