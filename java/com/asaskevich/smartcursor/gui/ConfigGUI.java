package com.asaskevich.smartcursor.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import com.asaskevich.smartcursor.SmartCursor;
import com.asaskevich.smartcursor.mod.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;

public class ConfigGUI
		extends GuiConfig {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConfigGUI(GuiScreen parent) {
		super(parent, new ConfigElement(SmartCursor.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), ModInfo.ID, false, false, GuiConfig.getAbridgedConfigPath(SmartCursor.config.toString()));
	}
}