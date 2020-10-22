package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ServerTickHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketCloakKeyPressed implements IMessage, IMessageHandler<PacketCloakKeyPressed, IMessage> {

	public PacketCloakKeyPressed() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public IMessage onMessage(PacketCloakKeyPressed message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		ServerTickHandler.switchCloakMode(player);
		return null;
	}
}