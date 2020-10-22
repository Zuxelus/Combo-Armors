package com.zuxelus.comboarmors.client;

import ic2.core.IC2;
import ic2.core.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.WorldEvent.*;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.armor.IJetpack;
import com.zuxelus.comboarmors.network.ChannelHandler;
import com.zuxelus.comboarmors.network.PacketWorldLoad;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ClientTickHandler {
	public static boolean firstLoad = false;

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		firstLoad = true;
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.player.worldObj != null && event.side == event.side.CLIENT)
			if (firstLoad) {
				firstLoad = false;
				ChannelHandler.network.sendToServer(new PacketWorldLoad());
			}
	}

	@SubscribeEvent
	public void onSetArmorModel(RenderPlayerEvent.SetArmorModel event) {
		if (!(event.entityPlayer instanceof EntityPlayer))
			return;
		EntityPlayer player = event.entityPlayer;
		if (player.isInvisible())
			event.result = 0;
	}
}
