package com.zuxelus.comboarmors.network;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ChannelHandler {
	public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(ComboArmors.MODID);

	public static void init() {
		network.registerMessage(PacketTileEntity.class, PacketTileEntity.class, 1, Side.CLIENT);
		network.registerMessage(PacketTileEntity.class, PacketTileEntity.class, 2, Side.SERVER);
		network.registerMessage(PacketFlyKeyPressed.class, PacketFlyKeyPressed.class, 3, Side.SERVER);
		network.registerMessage(PacketCloakKeyPressed.class, PacketCloakKeyPressed.class, 4, Side.SERVER);
		network.registerMessage(PacketOverchargeKeyPressed.class, PacketOverchargeKeyPressed.class, 5, Side.SERVER);
		network.registerMessage(PacketWorldLoad.class, PacketWorldLoad.class, 6, Side.SERVER);
	}
}