package com.zuxelus.comboarmors.nei;

import com.zuxelus.comboarmors.ComboArmors;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIComboArmorConfig implements IConfigureNEI {

	@Override
	public String getName() {
		return ComboArmors.NAME;
	}

	@Override
	public String getVersion() {
		return ComboArmors.VERSION;
	}

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new ArmorAssemblerRecipeHandler());
		API.registerUsageHandler(new ArmorAssemblerRecipeHandler());
	}
}