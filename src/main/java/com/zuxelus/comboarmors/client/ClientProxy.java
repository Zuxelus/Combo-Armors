package com.zuxelus.comboarmors.client;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.ServerProxy;
import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.containers.ContainerArmorAssembler;
import com.zuxelus.comboarmors.gui.GuiArmorAssembler;
import com.zuxelus.comboarmors.tileentities.TileEntityArmorAssembler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends ServerProxy {

	@Override
	public void loadConfig(FMLPreInitializationEvent event) {
		ComboArmors.config = new ConfigHandler();
		MinecraftForge.EVENT_BUS.register(ComboArmors.config);
		FMLCommonHandler.instance().bus().register(ComboArmors.config);
		ComboArmors.config.init(event.getSuggestedConfigurationFile());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityArmorAssembler)
			return new GuiArmorAssembler(new ContainerArmorAssembler(player, (TileEntityArmorAssembler) te));
		return null;
	}

	@Override
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	public static EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
}
