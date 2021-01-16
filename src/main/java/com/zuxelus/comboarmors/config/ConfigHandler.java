package com.zuxelus.comboarmors.config;

import java.io.File;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public Configuration config;

	public boolean enableCSolars;
	public boolean enableASolars;
	public int[] soPriority = new int[4];
	public int[] stPriority = new int[4];
	public int maxProdUpgrades;
	public int maxEnergyUpgrades;
	public int maxTransferUpgrades;
	public int nanoBowBoost;
	public int turbineEUAmount;
	public int jetpackEUAmount;
	public boolean craftSolarProd;
	public boolean craftStaticProd;
	public boolean craftFlightTurbine;
	public boolean craftCloakingModule;
	public boolean craftDischargeModule;
	public boolean craftCellModule;
	public boolean craftEnergyMk2;
	public boolean craftEnergyMk3;
	public boolean useEnergyMk1;
	public boolean useOverclocker;
	public boolean useTransformer;
	public boolean craftNanoBow;
	public boolean rapidFireMode;
	public boolean spreadMode;
	public boolean sniperMode;
	public boolean flameMode;
	public boolean explosiveMode;

	public void init(File configFile) {
		if (config == null)
			config = new Configuration(configFile);
		loadConfig();
	}

	private void loadConfig() {
		final String CATEGORY_CROSSMOD = "cross-mod";
		final String CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL;
		try {
			enableCSolars = config.getBoolean("cs-enable", CATEGORY_CROSSMOD, true, "Disable Compact Solars integration, regardless of whether or not the mod is found.", "config.cs-enable");
			enableASolars = config.getBoolean("asp-enable", CATEGORY_CROSSMOD, true, "Disabled Advanced Solar Panels integration, regardless of whether or not the mod is found.", "config.asp-enable");

			String comment = "Set the charging priority for the Solar Helmets and Static Boots. Use numbers 0-3, where 0 is the boots. Default order: 2, 0, 1, 3";
			soPriority[0] = config.getInt("solarPriority1", CATEGORY_GENERAL, 2, 0, 3, comment, "config.solarPriority1");
			soPriority[1] = config.getInt("solarPriority2", CATEGORY_GENERAL, 0, 0, 3, "", "config.solarPriority2");
			soPriority[2] = config.getInt("solarPriority3", CATEGORY_GENERAL, 1, 0, 3, "", "config.solarPriority3");
			soPriority[3] = config.getInt("solarPriority4", CATEGORY_GENERAL, 3, 0, 3, "", "config.solarPriority4");
			stPriority[0] = config.getInt("staticPriority1", CATEGORY_GENERAL, 2, 0, 3, "", "config.staticPriority1");
			stPriority[1] = config.getInt("staticPriority2", CATEGORY_GENERAL, 0, 0, 3, "", "config.staticPriority2");
			stPriority[2] = config.getInt("staticPriority3", CATEGORY_GENERAL, 1, 0, 3, "", "config.staticPriority3");
			stPriority[3] = config.getInt("staticPriority4", CATEGORY_GENERAL, 3, 0, 3, "", "config.staticPriority4");

			turbineEUAmount = config.getInt("euUsageTurbine", CATEGORY_GENERAL, 10, 0, 10000, "", "config.euUsageTurbine");
			jetpackEUAmount = config.getInt("euUsageJetpack", CATEGORY_GENERAL, 8, 0, 10000, "Change the EU Usage of Jetpacks and Turbines. Hover mode uses 25% less than the value below.", "config.euUsageJetpack");
			maxProdUpgrades = config.getInt("maxProductionUpgrades", CATEGORY_GENERAL, 511, 0, 65530,
					"Set the max number of Solar Production or Static Production upgrades to be installed in one item. Note that the max will be one more than the number you enter, as the default has 1. Default value: 511.",
					"config.maxProductionUpgrades");
			maxEnergyUpgrades = config.getInt("maxEnergyUpgrades", CATEGORY_GENERAL, 100000000, 0, Integer.MAX_VALUE, "Set the max Energy that an upgraded item can have. Default: 100,000,000", "config.maxEnergyUpgrades");
			maxTransferUpgrades = config.getInt("maxTransferUpgrades", CATEGORY_GENERAL, 200000, 0, 10000000, "Set the max Transfer Limit that an upgraded item can have. Default: 200,000", "config.maxTransferUpgrades");

			comment = "Enable whether or not the upgrades can be crafted. They can still be spawned in and used if you are an admin. Default: true.";
			craftCellModule =  config.getBoolean("enableCraftingCellModule", CATEGORY_GENERAL, true, comment, "config.enableCraftingCellModule");
			craftSolarProd = config.getBoolean("enableCraftingSolarProduction", CATEGORY_GENERAL, true, "", "config.enableCraftingSolarProduction");
			craftStaticProd = config.getBoolean("enableCraftingStaticProduction", CATEGORY_GENERAL, true, "", "config.enableCraftingStaticProduction");
			craftFlightTurbine = config.getBoolean("enableCraftingFlightTurbine", CATEGORY_GENERAL, true, "", "config.enableCraftingFlightTurbine");
			craftCloakingModule = config.getBoolean("enableCraftingCloakingModule", CATEGORY_GENERAL, true, "", "config.enableCraftingCloakingModule");
			craftDischargeModule = config.getBoolean("enableCraftingDischargeModule", CATEGORY_GENERAL, true, "", "config.enableCraftingDischargeModule");
			craftEnergyMk2 = config.getBoolean("enableCraftingEnergyMk2", CATEGORY_GENERAL, true, "", "config.enableCraftingEnergyMk2");
			craftEnergyMk3 = config.getBoolean("enableCraftingEnergyMk3", CATEGORY_GENERAL, true, "", "config.enableCraftingEnergyMk3");
			craftNanoBow = config.getBoolean("enableCraftingNanoBow", CATEGORY_GENERAL, true, "", "config.enableCraftingNanoBow");
			useEnergyMk1 = config.getBoolean("enableUseEnergyMk1", CATEGORY_GENERAL, true, "", "config.enableUseEnergyMk1");
			useOverclocker = config.getBoolean("enableUseOverclocker", CATEGORY_GENERAL, true, "", "config.enableUseOverclocker");
			useTransformer = config.getBoolean("enableUseTransformer", CATEGORY_GENERAL, true, "", "config.enableUseTransformer");

			nanoBowBoost = config.getInt("nanoBowDamageBoost", CATEGORY_GENERAL, 0, 0, 10000,
					"Boost the damage of the NanoBow, for use with things like Divine RPG. Each number adds 1 level of the Power enchantment.", "config.nanoBowDamageBoost");

			comment = "Enabled NanoBow modes { \"Normal\", \"Rapid fire\", \"Spread\", \"Sniper\", \"Flame\", \"Explosive\" }";
			explosiveMode = config.getBoolean("bowExplosiveMode", CATEGORY_GENERAL, true, comment, "config.bowExplosiveMode");
			flameMode = config.getBoolean("bowFlameMode", CATEGORY_GENERAL, true, "", "config.bowFlameMode");
			rapidFireMode = config.getBoolean("bowRapidFireMode", CATEGORY_GENERAL, true, "", "config.bowRapidFireMode");
			sniperMode = config.getBoolean("bowSniperMode", CATEGORY_GENERAL, true, "", "config.bowSniperMode");
			spreadMode = config.getBoolean("bowSpreadMode", CATEGORY_GENERAL, true, "", "config.bowSpreadMode");
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
			loadConfig();
	}
}
