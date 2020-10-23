package com.zuxelus.comboarmors.config;

import java.io.File;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public Configuration config;

	public static int[] soPriority = new int[4];
	public static int[] stPriority = new int[4];
	public static int maxProdUpgrades;
	public static int maxEnergyUpgrades;
	public static int maxTransferUpgrades;
	public static int nanoBowBoost;
	public static int turbineEUAmount;
	public static int jetpackEUAmount;
	public static boolean craftSolarProd;
	public static boolean craftStaticProd;
	public static boolean craftFlightTurbine;
	public static boolean craftCloakingModule;
	public static boolean craftDischargeModule;
	public static boolean craftCellModule;
	public static boolean craftEnergyMk2;
	public static boolean craftEnergyMk3;
	public static boolean useEnergyMk1;
	public static boolean useOverclocker;
	public static boolean useTransformer;
	public static boolean craftNanoBow;
	public static boolean rapidFireMode;
	public static boolean spreadMode;
	public static boolean sniperMode;
	public static boolean flameMode;
	public static boolean explosiveMode;

	public void init(File configFile) {
		if (config == null)
			config = new Configuration(configFile);
		
		loadConfiguration();
	}

	private void loadConfiguration() {
		final String CATEGORY_CROSSMOD = "cross-mod";
		final String CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL;
		try {
			Property enableCSolars = config.get(CATEGORY_CROSSMOD, "cs-enable", true);
			enableCSolars.setComment("Disable Compact Solars integration, regardless of whether or not the mod is found.");
			//ModIntegrationHandler.setIntegrationEnabled(0, enableCSolars.getBoolean(true));

			Property enableASolars = config.get(CATEGORY_CROSSMOD, "asp-enable", true);
			enableASolars.setComment("Disabled Advanced Solar Panels integration, regardless of whether or not the mod is found.");
			//ModIntegrationHandler.setIntegrationEnabled(1, enableASolars.getBoolean(true));

			Property soPriority1Prop = config.get(CATEGORY_GENERAL, "solarPriority1", 2);
			soPriority1Prop.setComment("Set the charging priority for the Solar Helmets and Static Boots. Use numbers 0-3, where 0 is the boots. Default order: 2, 0, 1, 3");
			soPriority[0] = soPriority1Prop.getInt();
			soPriority[1] = config.get(CATEGORY_GENERAL, "solarPriority2", 0).getInt();
			soPriority[2] = config.get(CATEGORY_GENERAL, "solarPriority3", 1).getInt();
			soPriority[3] = config.get(CATEGORY_GENERAL, "solarPriority4", 3).getInt();
			stPriority[0] = config.get(CATEGORY_GENERAL, "staticPriority1", 2).getInt();
			stPriority[1] = config.get(CATEGORY_GENERAL, "staticPriority2", 0).getInt();
			stPriority[2] = config.get(CATEGORY_GENERAL, "staticPriority3", 1).getInt();
			stPriority[3] = config.get(CATEGORY_GENERAL, "staticPriority4", 3).getInt();

			turbineEUAmount = config.get(CATEGORY_GENERAL, "euUsageTurbine", 10).getInt();
			Property jetpackTurbine = config.get(CATEGORY_GENERAL, "euUsageJetpack", 8);
			jetpackTurbine.setComment("Change the EU Usage of Jetpacks and Turbines. Hover mode uses 25% less than the value below.");
			jetpackEUAmount = jetpackTurbine.getInt(8);

			Property maxProdUpgradesProp = config.get(CATEGORY_GENERAL, "maxProductionUpgrades", 511);
			maxProdUpgradesProp.setComment("Set the max number of Solar Production or Static Production upgrades to be installed in one item. Note that the max will be one more than the number you enter, as the default has 1. Default value: 511.");
			maxProdUpgrades = maxProdUpgradesProp.getInt();

			Property maxEnergyUpgradesProp = config.get(CATEGORY_GENERAL, "maxEnergyUpgrades", 100000000);
			maxEnergyUpgradesProp.setComment("Set the max Energy that an upgraded item can have. Default: 100,000,000");
			maxEnergyUpgrades = maxEnergyUpgradesProp.getInt();
			Property maxTransferUpgradesProp = config.get(CATEGORY_GENERAL, "maxTransferUpgrades", 200000);
			maxTransferUpgradesProp.setComment("Set the max Transfer Limit that an upgraded item can have. Default: 200,000");
			maxTransferUpgrades = maxTransferUpgradesProp.getInt();

			Property enableCraftingCloak = config.get(CATEGORY_GENERAL, "enableCraftingCloakingModule", true);
			enableCraftingCloak.setComment("Enable whether or not the upgrades can be crafted. They can still be spawned in and used if you are an admin. Default: all true.");
			craftSolarProd = config.get(CATEGORY_GENERAL, "enableCraftingSolarProduction", true).getBoolean(true);
			craftStaticProd = config.get(CATEGORY_GENERAL, "enableCraftingStaticProduction", true).getBoolean(true);
			craftFlightTurbine = config.get(CATEGORY_GENERAL, "enableCraftingFlightTurbine", true).getBoolean(true);
			craftCloakingModule = enableCraftingCloak.getBoolean(true);
			craftDischargeModule = config.get(CATEGORY_GENERAL, "enableCraftingDischargeModule", true).getBoolean(true);
			craftCellModule =  config.get(CATEGORY_GENERAL, "enableCraftingCellModule", true).getBoolean(true);
			craftEnergyMk2 = config.get(CATEGORY_GENERAL, "enableCraftingEnergyMk2", true).getBoolean(true);
			craftEnergyMk3 = config.get(CATEGORY_GENERAL, "enableCraftingEnergyMk3", true).getBoolean(true);
			craftNanoBow = config.get(CATEGORY_GENERAL, "enableCraftingNanoBow", true).getBoolean(true);
			useEnergyMk1 = config.get(CATEGORY_GENERAL, "enableUseEnergyMk1", true).getBoolean(true);
			useOverclocker = config.get(CATEGORY_GENERAL, "enableUseOverclocker", true).getBoolean(true);
			useTransformer = config.get(CATEGORY_GENERAL, "enableUseTransformer", true).getBoolean(true);

			Property nanoboost = config.get(CATEGORY_GENERAL, "nanoBowDamageBoost", 0);
			nanoboost.setComment("Boost the damage of the NanoBow, for use with things like Divine RPG. Each number adds 1 level of the Power enchantment.");
			nanoBowBoost = nanoboost.getInt();

			Property nanoBowMods = config.get(CATEGORY_GENERAL, "bowExplosiveMode", true);
			nanoBowMods.setComment("Enabled NanoBow modes { \"Normal\", \"Rapid fire\", \"Spread\", \"Sniper\", \"Flame\", \"Explosive\" }");
			explosiveMode = nanoBowMods.getBoolean(true);
			flameMode = config.get(CATEGORY_GENERAL, "bowFlameMode", true).getBoolean(true);
			rapidFireMode = config.get(CATEGORY_GENERAL, "bowRapidFireMode", true).getBoolean(true);
			sniperMode = config.get(CATEGORY_GENERAL, "bowSniperMode", true).getBoolean(true);
			spreadMode = config.get(CATEGORY_GENERAL, "bowSpreadMode", true).getBoolean(true);
		} catch (Exception e) {
			ComboArmors.logger.error("Mod has a problem loading it's configuration", e);
		} finally {
			if (config.hasChanged())
				config.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ComboArmors.MODID))
			loadConfiguration();
	}
}
