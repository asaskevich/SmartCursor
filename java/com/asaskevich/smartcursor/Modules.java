package com.asaskevich.smartcursor;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraftforge.common.config.Configuration;
import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.api.IDropProcessor;
import com.asaskevich.smartcursor.api.IEntityProcessor;
import com.asaskevich.smartcursor.api.IModule;
import com.asaskevich.smartcursor.api.IPlayerProcessor;
import com.asaskevich.smartcursor.api.ModuleConnector;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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

	@SubscribeEvent
	public void onRegisterModule(ModuleConnector event) {
		IModule module = event.getModule();
		switch (event.getType()) {
			case ModuleConnector.ENTITY_PROCESSOR:
				registerModule((IEntityProcessor) module);
				break;
			case ModuleConnector.BLOCK_PROCESSOR:
				registerModule((IBlockProcessor) module);
				break;
			case ModuleConnector.DROP_PROCESSOR:
				registerModule((IDropProcessor) module);
				break;
			case ModuleConnector.PLAYER_PROCESSOR:
				registerModule((IPlayerProcessor) module);
				break;
		}
	}
}
