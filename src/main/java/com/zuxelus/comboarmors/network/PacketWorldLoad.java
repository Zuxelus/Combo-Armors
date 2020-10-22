package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ServerTickHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketWorldLoad implements IMessage, IMessageHandler<PacketWorldLoad, IMessage> {

	public PacketWorldLoad() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public IMessage onMessage(PacketWorldLoad message, MessageContext ctx) {
		ServerTickHandler.onPlayerLogin(ctx.getServerHandler().playerEntity);
		return null;
	}
}
