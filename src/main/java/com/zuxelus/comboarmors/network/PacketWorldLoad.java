package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWorldLoad implements IMessage, IMessageHandler<PacketWorldLoad, IMessage> {

	public PacketWorldLoad() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public IMessage onMessage(PacketWorldLoad message, MessageContext ctx) {
		ServerTickHandler.onPlayerLogin(ctx.getServerHandler().player);
		return null;
	}
}
