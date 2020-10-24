package com.zuxelus.comboarmors;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.zuxelus.comboarmors.client.ClientProxy;
import com.zuxelus.comboarmors.client.ClientTickHandler;
import com.zuxelus.comboarmors.config.ConfigHandler;
import com.zuxelus.comboarmors.init.ModIntegrationHandler;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.network.ChannelHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

@Mod(name = ComboArmors.NAME, modid = ComboArmors.MODID, version = ComboArmors.VERSION, dependencies = "required-after:IC2@[2.2.767-experimental,);after:CompactSolars;after:AdvancedSolarPanel", guiFactory = "com.zuxelus.comboarmors.config.GuiFactory", acceptedMinecraftVersions = "[1.7.10]")
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

	public static ArrayList<String> solars = new ArrayList();
	public static ArrayList<String> statics = new ArrayList();
	public static ArrayList<String> chests = new ArrayList();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		proxy.loadConfig(event);

		ChannelHandler.init();

		ModItems.onBlockRegistry();
		ModItems.onItemRegistry();
		ModItems.registerTileEntities();
		ModIntegrationHandler.loadIntegrationModules();
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
		ModItems.registerLists();
		ModItems.registerCraftingRecipes();
	}
}
