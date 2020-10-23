package com.zuxelus.comboarmors.config;

import com.zuxelus.comboarmors.ComboArmors;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
	public ConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(ComboArmors.config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				ComboArmors.MODID, false, false,GuiConfig.getAbridgedConfigPath(ComboArmors.config.config.toString()));
	}
}