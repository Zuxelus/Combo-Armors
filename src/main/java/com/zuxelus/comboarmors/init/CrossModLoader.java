package com.zuxelus.comboarmors.init;

import com.zuxelus.comboarmors.ComboArmors;
import com.zuxelus.comboarmors.items.EnumUpgradeType;
import com.zuxelus.comboarmors.items.ItemUpgrade;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CrossModLoader {
	public static final int COMPACT_SOLARS = 0;
	public static final int ADVANCED_SOLAR_PANELS = 1;
	public static final int GRAVISUITE = 2;
	public static final int GREGTECH = 3;
	private static final String[] modids = { "compactsolars", "advanced_solar_panels" };
	private static boolean[] loaded = { false, false, false, false };
	private static boolean[] integrateEnabled = { true, true, true, true };

	private static void integrateCompactSolars(Register<Item> event) {
		ModItems.lvHat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "solar_hat_low_voltage"));
		ModItems.mvHat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "solar_hat_medium_voltage"));
		ModItems.hvHat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "solar_hat_high_voltage"));
		ModItems.lvArray = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "compact_solar_block")), 1, 0);
		ModItems.mvArray = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "compact_solar_block")), 1, 1);
		ModItems.hvArray = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[0], "compact_solar_block")), 1, 2);
		ModItems.lvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "lv_solar_module");
		ModItems.mvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "mv_solar_module");
		ModItems.hvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "hv_solar_module");
	}

	private static void integrateAdvancedSolars(Register<Item> event) {
		ModItems.asp = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "machines")), 1, 2);
		ModItems.hybridsp = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "machines")), 1, 3);
		ModItems.ulthybsp = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "machines")), 1, 4);
		ModItems.ash = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "advancedsolarhelmet")));
		ModItems.hsh = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "hybridsolarhelmet")));
		ModItems.uhsh = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modids[1], "ultimatesolarhelmet")));
		if (!loaded[0]) {
			ModItems.lvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "lv_solar_module");
			ModItems.mvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "mv_solar_module");
			ModItems.hvSolarModule = ModItems.register(event, new ItemUpgrade(EnumRarity.COMMON, EnumUpgradeType.SOLARS), "hv_solar_module");
		}
	}

	public static void integrateMod(Register<Item> event, int i) {
		switch (i) {
		case 0:
			integrateCompactSolars(event);
			break;
		case 1:
			integrateAdvancedSolars(event);
			break;
		case 2:
			//integrateGraviSuite();
			break;
		}
	}

	public static boolean isModLoaded(int i) {
		return loaded[i];
	}

	public static void loadIntegrationModules(Register<Item> event) {
		ComboArmors.logger.info("Loading Cross-Mod Integration Modules");
		int l = modids.length;
		for (int i = 0; i < l; i++) {
			String modid = modids[i];
			if (!integrateEnabled[i]) {
				ComboArmors.logger.info("Integration of " + modid + " has been disabled in the configs. Skipping.");
			} else if (Loader.isModLoaded(modid)) {
				integrateMod(event, i);
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
