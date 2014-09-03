package com.asaskevich.smartcursor.utils;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class Setting {
	public static boolean isEnabled;
	public static boolean showDropInformation;
	public static boolean showEnchantments;
	public static boolean showDurability;
	public static boolean showXPOrb;
	public static boolean displayAdvInfoMob;
	public static boolean showPlayerInformation;
	public static boolean showBlockInformation;
	public static boolean showTooltipInRightCorner;
	public static int blockDamageStyle;
	public static double maxHeartCount;
	public static int mobStyle;
	public static int dropStyle;
	public static int playerStyle;
	public static int transparent;
	public static double lookDistance;

	public static void loadSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		blockDamageStyle = config.get("smartCursor", "blockDamageStyle", 0).getInt(0);
		transparent = config.get("smartCursor", "transparent", 255).getInt(255);
		mobStyle = config.get("smartCursor", "mobIndicatorStyle", 0).getInt(0);
		dropStyle = config.get("smartCursor", "dropStyle", 0).getInt(0);
		playerStyle = config.get("smartCursor", "playerStyle", 0).getInt(0);
		maxHeartCount = config.get("smartCursor", "maxHeartCount", 15f).getDouble(15f);
		lookDistance = config.get("smartCursor", "lookDistance", 20f).getDouble(20f);
		isEnabled = config.get("smartCursor", "enabled", true).getBoolean(true);
		showDropInformation = config.get("smartCursor", "showDrop", true).getBoolean(true);
		showEnchantments = config.get("smartCursor", "showEnch", true).getBoolean(true);
		showDurability = config.get("smartCursor", "showDurability", true).getBoolean(true);
		showXPOrb = config.get("smartCursor", "showXPOrb", true).getBoolean(true);
		displayAdvInfoMob = config.get("smartCursor", "mobAdvInfo", true).getBoolean(true);
		showPlayerInformation = config.get("smartCursor", "showPlayer", true).getBoolean(true);
		showBlockInformation = config.get("smartCursor", "showBlock", true).getBoolean(true);
		showTooltipInRightCorner = config.get("smartCursor", "showTooltipInRightCorner", false).getBoolean(false);
		config.save();
	}

	public static void updateSettings(File configFile) {
		Configuration config = new Configuration(configFile);
		config.load();
		config.get("smartCursor", "blockDamageStyle", blockDamageStyle).set(blockDamageStyle);
		config.get("smartCursor", "transparent", transparent).set(transparent);
		config.get("smartCursor", "mobIndicatorStyle", mobStyle).set(mobStyle);
		config.get("smartCursor", "mobIndicatorStyle", playerStyle).set(playerStyle);
		config.get("smartCursor", "lookDistance", lookDistance).set(lookDistance);
		config.get("smartCursor", "dropStyle", dropStyle).set(dropStyle);
		config.get("smartCursor", "maxHeartCount", maxHeartCount).set(maxHeartCount);
		config.get("smartCursor", "enabled", isEnabled).set(isEnabled);
		config.get("smartCursor", "showDrop", showDropInformation).set(showDropInformation);
		config.get("smartCursor", "showEnch", showEnchantments).set(showEnchantments);
		config.get("smartCursor", "showDurability", showDurability).set(showDurability);
		config.get("smartCursor", "showXPOrb", showXPOrb).set(showXPOrb);
		config.get("smartCursor", "mobAdvInfo", displayAdvInfoMob).set(displayAdvInfoMob);
		config.get("smartCursor", "showPlayer", showPlayerInformation).set(showPlayerInformation);
		config.get("smartCursor", "showBlock", showBlockInformation).set(showBlockInformation);
		config.get("smartCursor", "showTooltipInRightCorner", showTooltipInRightCorner).set(showTooltipInRightCorner);
		config.save();
	}
}
