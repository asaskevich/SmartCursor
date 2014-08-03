package com.asaskevich.smartcursor.utils;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Settings {

	public static boolean isRenderEnabled(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		boolean isEnabled = config.get("smartCursor", "enabled", true).getBoolean(true);
		config.save();
		return isEnabled;
	}

}
