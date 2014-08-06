package com.asaskevich.smartcursor.utils;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class Setting {
	public static boolean isEnabled;
	public static boolean showDrop;
	public static boolean showEnch;
	public static boolean showDur;
	public static boolean showXPOrb;
	public static boolean displayMobAdv;
	public static int blockDamageStyle;
	public static double maxHeartCount;
	public static int mobStyle;
	public static int dropStyle;
	public static double lookDistance;

	public static void loadSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		blockDamageStyle = config.get("smartCursor", "blockDamageStyle", 0).getInt(0);
		mobStyle = config.get("smartCursor", "mobIndicatorStyle", 0).getInt(0);
		dropStyle = config.get("smartCursor", "dropStyle", 0).getInt(0);
		maxHeartCount = config.get("smartCursor", "maxHeartCount", 15f).getDouble(15f);
		lookDistance = config.get("smartCursor", "lookDistance", 20f).getDouble(20f);
		isEnabled = config.get("smartCursor", "enabled", true).getBoolean(true);
		showDrop = config.get("smartCursor", "showDrop", true).getBoolean(true);
		showEnch = config.get("smartCursor", "showEnch", true).getBoolean(true);
		showDur = config.get("smartCursor", "showDurability", true).getBoolean(true);
		showXPOrb = config.get("smartCursor", "showXPOrb", true).getBoolean(true);
		displayMobAdv = config.get("smartCursor", "mobAdvInfo", true).getBoolean(true);
		config.save();
	}

	public static void updateSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		config.get("smartCursor", "blockDamageStyle", blockDamageStyle).set(blockDamageStyle);
		config.get("smartCursor", "mobIndicatorStyle", mobStyle).set(mobStyle);
		config.get("smartCursor", "lookDistance", lookDistance).set(lookDistance);
		config.get("smartCursor", "dropStyle", dropStyle).set(dropStyle);
		config.get("smartCursor", "maxHeartCount", maxHeartCount).set(maxHeartCount);
		config.get("smartCursor", "enabled", isEnabled).set(isEnabled);
		config.get("smartCursor", "showDrop", showDrop).set(showDrop);
		config.get("smartCursor", "showEnch", showEnch).set(showEnch);
		config.get("smartCursor", "showDurability", showDur).set(showDur);
		config.get("smartCursor", "showXPOrb", showXPOrb).set(showXPOrb);
		config.get("smartCursor", "mobAdvInfo", displayMobAdv).set(displayMobAdv);
		config.save();
	}
}
