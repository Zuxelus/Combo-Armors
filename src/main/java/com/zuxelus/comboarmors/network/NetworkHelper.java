package com.zuxelus.comboarmors.network;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class NetworkHelper {

	// server
	private static void sendPacketToAllAround(BlockPos pos, int dist, World world, IMessage packet) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer player : players) {
			if (player instanceof EntityPlayerMP) {
				double dx = pos.getX() - player.posX;
				double dy = pos.getY() - player.posY;
				double dz = pos.getZ() - player.posZ;
	
				if (dx * dx + dy * dy + dz * dz < dist * dist)
					ChannelHandler.network.sendTo(packet, (EntityPlayerMP)player);
			}
		}
	}

	// server
	public static void updateClientTileEntity(IContainerListener crafter, BlockPos pos, int type, int value) {
		if (!(crafter instanceof EntityPlayerMP))
			return;
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("type", type);
		tag.setInteger("value", value);
		ChannelHandler.network.sendTo(new PacketTileEntity(pos, tag), (EntityPlayerMP) crafter);
	}

	public static void updateClientTileEntity(IContainerListener crafter, BlockPos pos, int type, double value) {
		if (!(crafter instanceof EntityPlayerMP))
			return;
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("type", type);
		tag.setDouble("value", value);
		ChannelHandler.network.sendTo(new PacketTileEntity(pos, tag), (EntityPlayerMP) crafter);
	}

	public static void updateClientTileEntity(IContainerListener crafter, BlockPos pos, NBTTagCompound tag) {
		if (!(crafter instanceof EntityPlayerMP))
			return;
		ChannelHandler.network.sendTo(new PacketTileEntity(pos, tag), (EntityPlayerMP) crafter);
	}

	public static void updateClientTileEntity(World world, BlockPos pos, NBTTagCompound tag) {
		sendPacketToAllAround(pos, 64, world, new PacketTileEntity(pos, tag));
	}

	// client
	public static void updateSeverTileEntity(BlockPos pos, int type, String string) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("type", type);
		tag.setString("string", string);
		ChannelHandler.network.sendToServer(new PacketTileEntity(pos, tag));
	}

	public static void updateSeverTileEntity(BlockPos pos, int type, int value) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("type", type);
		tag.setInteger("value", value);
		ChannelHandler.network.sendToServer(new PacketTileEntity(pos, tag));
	}

	public static void updateSeverTileEntity(BlockPos pos, NBTTagCompound tag) {
		ChannelHandler.network.sendToServer(new PacketTileEntity(pos, tag));
	}
}
