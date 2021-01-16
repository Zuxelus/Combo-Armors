package com.zuxelus.comboarmors.recipes;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String key = JsonUtils.getString(json, "config");
		if (key.equals("enableCSolars"))
			return () -> (ComboArmors.config.enableCSolars);
		if (key.equals("enableASolars"))
			return () -> (ComboArmors.config.enableASolars);
		if (key.equals("craftSolarProd"))
			return () -> (ComboArmors.config.craftSolarProd);
		if (key.equals("craftStaticProd"))
			return () -> (ComboArmors.config.craftStaticProd);
		if (key.equals("craftFlightTurbine"))
			return () -> (ComboArmors.config.craftFlightTurbine);
		if (key.equals("craftCloakingModule"))
			return () -> (ComboArmors.config.craftCloakingModule);
		if (key.equals("craftDischargeModule"))
			return () -> (ComboArmors.config.craftDischargeModule);
		if (key.equals("craftCellModule"))
			return () -> (ComboArmors.config.craftCellModule);
		if (key.equals("craftEnergyMk2"))
			return () -> (ComboArmors.config.craftEnergyMk2);
		if (key.equals("craftEnergyMk3"))
			return () -> (ComboArmors.config.craftEnergyMk3);
		return () -> (true);
	}
}
