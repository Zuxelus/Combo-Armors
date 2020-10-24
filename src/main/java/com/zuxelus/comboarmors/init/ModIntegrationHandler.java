package com.zuxelus.comboarmors.init;

import java.io.File;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.EnumUpgradeType;
import com.zuxelus.comboarmors.items.ItemUpgrade;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes;
import com.zuxelus.comboarmors.recipes.RecipeHandler;
import com.zuxelus.comboarmors.utils.ItemNBTHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.server.FMLServerHandler;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;

public class ModIntegrationHandler {
	public static final int COMPACT_SOLARS = 0;
	public static final int ADVANCED_SOLAR_PANELS = 1;
	public static final int GRAVISUITE = 2;
	public static final int GREGTECH = 3;
	private static final String[] modids = { "CompactSolars", "AdvancedSolarPanel" };
	private static boolean[] loaded = { false, false, false, false };
	private static boolean[] integrateEnabled = { true, true, true, true };

	private static void integrateCompactSolars() {
		ModItems.lvHat = GameRegistry.findItem(modids[0], "solarHatLV");
		ModItems.mvHat = GameRegistry.findItem(modids[0], "solarHatMV");
		ModItems.hvHat = GameRegistry.findItem(modids[0], "solarHatHV");
		ModItems.lvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 0);
		ModItems.mvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 1);
		ModItems.hvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 2);
		ModItems.lvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "lv_solar_module");
		ModItems.mvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "mv_solar_module");
		ModItems.hvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "hv_solar_module");
	}

	private static void integrateAdvancedSolars() {
		ModItems.asp = new ItemStack(GameRegistry.findBlock(modids[1], "BlockAdvSolarPanel"), 1, 0);
		ModItems.hybridsp = new ItemStack(GameRegistry.findBlock(modids[1], "BlockAdvSolarPanel"), 1, 1);
		ModItems.ulthybsp = new ItemStack(GameRegistry.findBlock(modids[1], "BlockAdvSolarPanel"), 1, 2);
		ModItems.ash = new ItemStack(GameRegistry.findItem(modids[1], "advanced_solar_helmet"));
		ModItems.hsh = new ItemStack(GameRegistry.findItem(modids[1], "hybrid_solar_helmet"));
		ModItems.uhsh = new ItemStack(GameRegistry.findItem(modids[1], "ultimate_solar_helmet"));
		if (!loaded[0]) {
			ModItems.lvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "lv_solar_module");
			ModItems.mvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "mv_solar_module");
			ModItems.hvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "hv_solar_module");
		}
	}

	public static void integrateMod(int i) {
		switch (i) {
		case 0:
			integrateCompactSolars();
			break;
		case 1:
			integrateAdvancedSolars();
			break;
		case 2:
			//integrateGraviSuite();
			break;
		}
	}

	public static boolean isModLoaded(int i) {
		return loaded[i];
	}

	public static void loadIntegrationModules() {
		ComboArmors.logger.info("Loading Cross-Mod Integration Modules");
		int l = modids.length;
		for (int i = 0; i < l; i++) {
			String modid = modids[i];
			if (!integrateEnabled[i]) {
				ComboArmors.logger.info("Integration of " + modid + " has been disabled in the configs. Skipping.");
			} else if (Loader.isModLoaded(modid)) {
				integrateMod(i);
				loaded[i] = true;
				ComboArmors.logger.info("Successfully loaded integration for " + modid + ".");
			} else
				ComboArmors.logger.info("Failed to load integration for " + modid + ".");
		}
	}

	public static void setIntegrationEnabled(int i, boolean b) {
		integrateEnabled[i] = b;
	}
}