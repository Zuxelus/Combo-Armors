package com.zuxelus.comboarmors;

import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.containers.ContainerArmorAssembler;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ServerProxy implements IGuiHandler {

	public void loadConfig(FMLPreInitializationEvent event) {
		ComboArmors.config = new ConfigHandler();
		ComboArmors.config.init(event.getSuggestedConfigurationFile());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
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
