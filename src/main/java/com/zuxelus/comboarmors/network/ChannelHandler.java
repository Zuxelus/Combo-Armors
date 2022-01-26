package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.zlib.network.PacketTileEntity;

public class ChannelHandler {

	public static void init() {
		NetworkHelper.createChannel(ComboArmors.MODID);
		NetworkHelper.registerServerToClient(PacketTileEntity.class, PacketTileEntity.class, 1);
		NetworkHelper.registerClientToServer(PacketTileEntity.class, PacketTileEntity.class, 2);
		NetworkHelper.registerClientToServer(PacketFlyKeyPressed.class, PacketFlyKeyPressed.class, 3);
		NetworkHelper.registerClientToServer(PacketCloakKeyPressed.class, PacketCloakKeyPressed.class, 4);
		NetworkHelper.registerClientToServer(PacketOverchargeKeyPressed.class, PacketOverchargeKeyPressed.class, 5);
		NetworkHelper.registerClientToServer(PacketWorldLoad.class, PacketWorldLoad.class, 6);
	}
}