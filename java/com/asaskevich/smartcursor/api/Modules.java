package com.asaskevich.smartcursor.api;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraftforge.common.config.Configuration;
import com.asaskevich.smartcursor.SmartCursor;

public class Modules {
	public static ArrayList<IEntityProcessor>	entityModules	= new ArrayList<IEntityProcessor>();
	public static ArrayList<IPlayerProcessor>	playerModules	= new ArrayList<IPlayerProcessor>();
	public static ArrayList<IBlockProcessor>	blockModules	= new ArrayList<IBlockProcessor>();
	public static ArrayList<IDropProcessor>		dropModules		= new ArrayList<IDropProcessor>();
	public static HashMap<String, Boolean>		moduleStatus	= new HashMap<String, Boolean>();

	public static void registerModule(IEntityProcessor module) {
		entityModules.add(module);
		moduleStatus.put(module.getClass().getCanonicalName(), true);
	}

	public static void registerModule(IPlayerProcessor module) {
		playerModules.add(module);
		moduleStatus.put(module.getClass().getCanonicalName(), true);
	}

	public static void registerModule(IBlockProcessor module) {
		blockModules.add(module);
		moduleStatus.put(module.getClass().getCanonicalName(), true);
	}

	public static void registerModule(IDropProcessor module) {
		dropModules.add(module);
		moduleStatus.put(module.getClass().getCanonicalName(), true);
	}

	public static void switchModule(String canonicalName) {
		if (!moduleStatus.containsKey(canonicalName)) moduleStatus.put(canonicalName, true);
		boolean status = moduleStatus.get(canonicalName);
		moduleStatus.put(canonicalName, !status);
		syncConfig(SmartCursor.config);
	}

	public static boolean isActiveModule(String canonicalName) {
		if (!moduleStatus.containsKey(canonicalName)) moduleStatus.put(canonicalName, true);
		return moduleStatus.containsKey(canonicalName) && moduleStatus.get(canonicalName);
	}

	public static void syncConfig(Configuration config) {
		config.load();
		for (String key : moduleStatus.keySet()) {
			boolean status = isActiveModule(key);
			config.get("modules", key, status).set(status);
		}
		config.save();
	}

	public static void loadConfig(Configuration config) {
		for (String key : moduleStatus.keySet()) {
			boolean status = config.getBoolean(key, "modules", true, key);
			moduleStatus.put(key, status);
		}
	}
}
