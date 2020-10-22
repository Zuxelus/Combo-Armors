package com.zuxelus.comboarmors.config;

import com.zuxelus.comboarmors.ComboArmors;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGui extends GuiConfig {
	public ConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(ComboArmors.config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				ComboArmors.MODID, false, false,GuiConfig.getAbridgedConfigPath(ComboArmors.config.config.toString()));
	}
}