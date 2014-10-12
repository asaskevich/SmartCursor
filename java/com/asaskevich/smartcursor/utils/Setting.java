package com.asaskevich.smartcursor.utils;

import net.minecraftforge.common.config.Configuration;

public class Setting {
	public static boolean	isEnabled					= true;
	public static boolean	showDropInformation			= true;
	public static boolean	showEnchantments			= true;
	public static boolean	showDurability				= true;
	public static boolean	showXPOrb					= true;
	public static boolean	displayAdvInfoMob			= true;
	public static boolean	showPlayerInformation		= true;
	public static boolean	showBlockInformation		= true;
	public static boolean	showTooltipInRightCorner	= false;
	public static int		blockDamageStyle			= 0;
	public static int		mobStyle					= 0;
	public static int		dropStyle					= 0;
	public static int		playerStyle					= 0;
	public static int		transparent					= 255;
	public static int		delta						= 1000;
	public static double	lookDistance				= 20f;
	public static double	maxHeartCount				= 15f;

	public static void syncConfig(Configuration config) {
		blockDamageStyle = config.getInt("Block Damage Style", Configuration.CATEGORY_GENERAL, blockDamageStyle, 0, 2, "Block Damage Style");
		delta = config.getInt("Display Time", Configuration.CATEGORY_GENERAL, delta, 0, 5000, "Display Time");
		transparent = config.getInt("Tooltip Transparent", Configuration.CATEGORY_GENERAL, transparent, 255, 255, "Transparent Of Tooltip");
		mobStyle = config.getInt("Style Of Mob Indicator", Configuration.CATEGORY_GENERAL, mobStyle, 0, 3, "Style Of Mob Indicator");
		playerStyle = config.getInt("Style Of Player Indicator", Configuration.CATEGORY_GENERAL, playerStyle, 0, 3, "Style Of Player Indicator");
		lookDistance = config.getFloat("Look Distance", Configuration.CATEGORY_GENERAL, (float) lookDistance, 1, 100, "Look Distance");
		dropStyle = config.getInt("Style Of Drop Indicator", Configuration.CATEGORY_GENERAL, dropStyle, 0, 1, "Style Of Drop Indicator");
		maxHeartCount = config.getFloat("Max Heart Count", Configuration.CATEGORY_GENERAL, (float) maxHeartCount, 5f, 50f, "Max Heart Count");
		isEnabled = config.getBoolean("Is Enabled", Configuration.CATEGORY_GENERAL, isEnabled, "Is Enabled");
		showDropInformation = config.getBoolean("Is Drop Indicator Enabled", Configuration.CATEGORY_GENERAL, showDropInformation, "Is Drop Indicator Enabled");
		showEnchantments = config.getBoolean("Is Ench Indicator Enabled", Configuration.CATEGORY_GENERAL, showEnchantments, "Is Ench Indicator Enabled");
		showDurability = config.getBoolean("Is Durability Indicator Enabled", Configuration.CATEGORY_GENERAL, showDurability, "Is Durability Indicator Enabled");
		showXPOrb = config.getBoolean("Is XPorb Indicator Enabled", Configuration.CATEGORY_GENERAL, showXPOrb, "Is XPorb Indicator Enabled");
		displayAdvInfoMob = config.getBoolean("Is Advanced Mob Indicator Enabled", Configuration.CATEGORY_GENERAL, displayAdvInfoMob, "Is Advanced Mob Indicator Enabled");
		showPlayerInformation = config.getBoolean("Is Player Indicator Enabled", Configuration.CATEGORY_GENERAL, showPlayerInformation, "Is Player Indicator Enabled");
		showBlockInformation = config.getBoolean("Is Block Indicator Enabled", Configuration.CATEGORY_GENERAL, showBlockInformation, "Is Block Indicator Enabled");
		showTooltipInRightCorner = config.getBoolean("Show Tooltip In Right Corner", Configuration.CATEGORY_GENERAL, showTooltipInRightCorner, "Show Tooltip In Right Corner");
		if (config.hasChanged()) config.save();
	}

	public static void updateSettings(Configuration config) {
		config.load();
		config.get(Configuration.CATEGORY_GENERAL, "Block Damage Style", blockDamageStyle).set(blockDamageStyle);
		config.get(Configuration.CATEGORY_GENERAL, "Tooltip Transparent", transparent).set(transparent);
		config.get(Configuration.CATEGORY_GENERAL, "Style Of Mob Indicator", mobStyle).set(mobStyle);
		config.get(Configuration.CATEGORY_GENERAL, "Style Of Player Indicator", playerStyle).set(playerStyle);
		config.get(Configuration.CATEGORY_GENERAL, "Look Distance", lookDistance).set(lookDistance);
		config.get(Configuration.CATEGORY_GENERAL, "Style Of Drop Indicator", dropStyle).set(dropStyle);
		config.get(Configuration.CATEGORY_GENERAL, "Max Heart Count", maxHeartCount).set(maxHeartCount);
		config.get(Configuration.CATEGORY_GENERAL, "Is Enabled", isEnabled).set(isEnabled);
		config.get(Configuration.CATEGORY_GENERAL, "Is Drop Indicator Enabled", showDropInformation).set(showDropInformation);
		config.get(Configuration.CATEGORY_GENERAL, "Is Ench Indicator Enabled", showEnchantments).set(showEnchantments);
		config.get(Configuration.CATEGORY_GENERAL, "Is Durability Indicator Enabled", showDurability).set(showDurability);
		config.get(Configuration.CATEGORY_GENERAL, "Is XPorb Indicator Enabled", showXPOrb).set(showXPOrb);
		config.get(Configuration.CATEGORY_GENERAL, "Is Advanced Mob Indicator Enabled", displayAdvInfoMob).set(displayAdvInfoMob);
		config.get(Configuration.CATEGORY_GENERAL, "Is Player Indicator Enabled", showPlayerInformation).set(showPlayerInformation);
		config.get(Configuration.CATEGORY_GENERAL, "Is Block Indicator Enabled", showBlockInformation).set(showBlockInformation);
		config.get(Configuration.CATEGORY_GENERAL, "Show Tooltip In Right Corner", showTooltipInRightCorner).set(showTooltipInRightCorner);
		config.get(Configuration.CATEGORY_GENERAL, "Display Time", delta).set(delta);
		config.save();
	}
}
