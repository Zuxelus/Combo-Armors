package com.zuxelus.comboarmors.utils;

import java.io.File;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.init.ModItems;
import com.zuxelus.comboarmors.items.EnumUpgradeType;
import com.zuxelus.comboarmors.items.ItemUpgrade;
import com.zuxelus.comboarmors.recipes.ArmorAssemblerRecipes;
import com.zuxelus.comboarmors.recipes.RecipeHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.server.FMLServerHandler;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
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

	/*
	 * private static void integrateGraviSuite() { Configuration gsconfig =
	 * getConfig(modids[2] + ".cfg"); }
	 */
	public static Configuration getConfig(String dir) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			return new Configuration(new File(Loader.instance().getConfigDir() + "/config/" + dir));
		return new Configuration(FMLServerHandler.instance().getServer().getFile("/config/" + dir));
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

			RecipeHandler.instance().addSolarRecipes(ModItems.lvSolarModule);
			RecipeHandler.instance().addSolarRecipes(ModItems.mvSolarModule);
			RecipeHandler.instance().addSolarRecipes(ModItems.hvSolarModule);
		}
		ItemStack advanced = new ItemStack(ModItems.solarNanoHelm, 1);
		NBTTagCompound nbtadv = StackUtil.getOrCreateNbtData(advanced);
		nbtadv.setInteger("solarProd", 7);
		ItemStack hybrid = new ItemStack(ModItems.solarQuantumHelm, 1);
		NBTTagCompound nbthyb = StackUtil.getOrCreateNbtData(hybrid);
		nbthyb.setInteger("solarProd", 63);
		nbthyb.setInteger("upgradedTransfer", 4000);
		nbthyb.setInteger("transferLimit", 5000);
		ItemStack ultimate = new ItemStack(ModItems.solarQuantumHelm, 1);
		NBTTagCompound nbtult = StackUtil.getOrCreateNbtData(ultimate);
		nbtult.setInteger("solarProd", 511);
		nbtult.setInteger("upgradedTransfer", 4000);
		nbtult.setInteger("transferLimit", 5000);
		ArmorAssemblerRecipes.addAssemblyRecipe(advanced, ModItems.ash, ModItems.exoModule);
		ArmorAssemblerRecipes.addAssemblyRecipe(hybrid, ModItems.hsh, ModItems.exoModule);
		ArmorAssemblerRecipes.addAssemblyRecipe(ultimate, ModItems.uhsh, ModItems.exoModule);

		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.lvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('S'), ModItems.asp });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.mvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.insulatedGoldCableItem, Character.valueOf('S'), ModItems.hybridsp });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.hvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('S'), ModItems.ulthybsp });
	}

	private static void integrateCompactSolars() {
		ModItems.lvHat = GameRegistry.findItem(modids[0], "solarHatLV");
		ModItems.mvHat = GameRegistry.findItem(modids[0], "solarHatMV");
		ModItems.hvHat = GameRegistry.findItem(modids[0], "solarHatHV");
		ComboArmors.solars.add(ModItems.lvHat.getUnlocalizedName());
		ComboArmors.solars.add(ModItems.mvHat.getUnlocalizedName());
		ComboArmors.solars.add(ModItems.hvHat.getUnlocalizedName());
		ModItems.lvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 0);
		ModItems.mvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 1);
		ModItems.hvArray = new ItemStack(GameRegistry.findBlock(modids[0], "CompactSolarBlock"), 1, 2);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.lvHat);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.mvHat);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarNanoHelm, ModItems.exoNanoHelm, ModItems.hvHat);

		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.lvHat);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.mvHat);
		ArmorAssemblerRecipes.addAssemblyRecipe(ModItems.solarQuantumHelm, ModItems.exoQuantumHelm, ModItems.hvHat);

		ModItems.lvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "lv_solar_module");
		ModItems.mvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "mv_solar_module");
		ModItems.hvSolarModule = ModItems.register(new ItemUpgrade(EnumRarity.common, EnumUpgradeType.SOLARS), "hv_solar_module");

		RecipeHandler.instance().addSolarRecipes(ModItems.lvSolarModule);
		RecipeHandler.instance().addSolarRecipes(ModItems.mvSolarModule);
		RecipeHandler.instance().addSolarRecipes(ModItems.hvSolarModule);

		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.lvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('S'), ModItems.lvArray });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.mvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.insulatedGoldCableItem, Character.valueOf('S'), ModItems.mvArray });
		Recipes.advRecipes.addRecipe(new ItemStack(ModItems.hvSolarModule), new Object[] { "RRR", "CSC", "RRR", Character.valueOf('R'), "plateIron", Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('S'), ModItems.hvArray });
	}

	public static void integrateMod(int i) {
		switch (i) {
		case 0:
			integrateCompactSolars();
			break;
		case 1:
			integrateAdvancedSolars();
			break;
		/*
		 * case 2: integrateGraviSuite();
		 */
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
