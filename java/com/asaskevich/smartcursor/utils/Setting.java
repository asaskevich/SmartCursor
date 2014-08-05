package com.asaskevich.smartcursor.utils;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class Setting {
	public static boolean isEnabled;
	public static boolean showDrop;
	public static boolean showEnch;
	public static boolean showDur;
	public static boolean showXPOrb;
	public static int blockDamageStyle;
	public static int mobStyle;

	public static void loadSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		blockDamageStyle = config.get("smartCursor", "blockDamageStyle", 0).getInt(0);
		mobStyle = config.get("smartCursor", "mobIndicatorStyle", 0).getInt(0);
		isEnabled = config.get("smartCursor", "enabled", true).getBoolean(true);
		showDrop = config.get("smartCursor", "showDrop", true).getBoolean(true);
		showEnch = config.get("smartCursor", "showEnch", true).getBoolean(true);
		showDur = config.get("smartCursor", "showDurability", true).getBoolean(true);
		showXPOrb = config.get("smartCursor", "showXPOrb", true).getBoolean(true);
		config.save();
	}

	public static void updateSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		config.get("smartCursor", "blockDamageStyle", blockDamageStyle).set(blockDamageStyle);
		config.get("smartCursor", "mobIndicatorStyle", mobStyle).set(mobStyle);
		config.get("smartCursor", "enabled", isEnabled).set(isEnabled);
		config.get("smartCursor", "showDrop", showDrop).set(showDrop);
		config.get("smartCursor", "showEnch", showEnch).set(showEnch);
		config.get("smartCursor", "showDurability", showDur).set(showDur);
		config.get("smartCursor", "showXPOrb", showXPOrb).set(showXPOrb);
		config.save();
	}
}
