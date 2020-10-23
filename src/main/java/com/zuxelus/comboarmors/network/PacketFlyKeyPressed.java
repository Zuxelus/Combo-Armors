package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFlyKeyPressed implements IMessage, IMessageHandler<PacketFlyKeyPressed, IMessage> {

	public PacketFlyKeyPressed() { }

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public IMessage onMessage(PacketFlyKeyPressed message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		ServerTickHandler.switchFlyMode(player);
		return null;
	}
}