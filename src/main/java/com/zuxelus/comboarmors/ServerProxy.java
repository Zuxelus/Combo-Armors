package com.zuxelus.comboarmors;

import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.containers.ContainerArmorAssembler;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ServerProxy implements IGuiHandler {

	public void loadConfig(FMLPreInitializationEvent event) {
		ComboArmors.config = new ConfigHandler();
		ComboArmors.config.init(event.getSuggestedConfigurationFile());
	}

	public static void sendPlayerMessage(EntityPlayer player, String message) {
		player.addChatMessage(new ChatComponentText(message));
	}

	public int addArmor(String name) {
		return 0;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityArmorAssembler)
			return new ContainerArmorAssembler(player, (TileEntityArmorAssembler) te);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void registerEventHandlers() { }
}
