package com.zuxelus.comboarmors.client;

import com.zuxelus.comboarmors.network.ChannelHandler;
import com.zuxelus.comboarmors.network.PacketWorldLoad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ClientTickHandler {
	public static boolean firstLoad = false;
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		firstLoad = true;
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.player.world != null && event.side == event.side.CLIENT)
			if (firstLoad) {
				firstLoad = false;
				ChannelHandler.network.sendToServer(new PacketWorldLoad());
			}
	}

	@SubscribeEvent
	public void onPreRender(RenderPlayerEvent.Pre event) {
		if (!(event.getEntityPlayer() instanceof EntityPlayer))
			return;
		EntityPlayer player = event.getEntityPlayer();
		if (player.isInvisible())
			event.setCanceled(true);
	}

	/*@SubscribeEvent
	public void onPostRender(RenderPlayerEvent.Post event) {
		if (!(event.getEntityPlayer() instanceof EntityPlayer))
			return;
		EntityPlayer player = event.getEntityPlayer();
	}*/
}
