package com.zuxelus.comboarmors;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.ic2.CrossIC2;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.network.ChannelHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(name = ComboArmors.NAME, modid = ComboArmors.MODID, version = ComboArmors.VERSION, dependencies = "required-after:ic2", guiFactory = "com.zuxelus.comboarmors.config.GuiFactory", acceptedMinecraftVersions = "[1.12.2]")
public class ComboArmors {
	public static final String MODID = "comboarmors";
	public static final String NAME = "Combo Armors";
	public static final String VERSION = "@VERSION@";

	@SidedProxy(clientSide = "com.zuxelus.comboarmors.client.ClientProxy", serverSide = "com.zuxelus.comboarmors.ServerProxy")
	public static ServerProxy proxy;
	@Instance(MODID)
	public static ComboArmors instance;

	public static CreativeTab creativeTab = new CreativeTab();

	public static Logger logger;
	public static ConfigHandler config;

	public static CrossIC2 ic2;
	public static ArrayList<String> solars = new ArrayList();
	public static ArrayList<String> statics = new ArrayList();
	public static ArrayList<String> chests = new ArrayList();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		proxy.loadConfig(event);

		ChannelHandler.init();

		ModItems.registerTileEntities();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//ModIntegrationHandler.loadIntegrationModules();
	}
}
