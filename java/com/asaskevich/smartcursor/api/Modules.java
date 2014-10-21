package com.asaskevich.smartcursor.api;

import java.util.ArrayList;

public class Modules {
	public static ArrayList<IEntityProcessor>	entityModules	= new ArrayList<IEntityProcessor>();
	public static ArrayList<IPlayerProcessor>	playerModules	= new ArrayList<IPlayerProcessor>();
	public static ArrayList<IBlockProcessor>	blockModules	= new ArrayList<IBlockProcessor>();
	public static ArrayList<IDropProcessor>		dropModules		= new ArrayList<IDropProcessor>();

	public static void registerModule(IEntityProcessor module) {
		entityModules.add(module);
	}

	public static void registerModule(IPlayerProcessor module) {
		playerModules.add(module);
	}

	public static void registerModule(IBlockProcessor module) {
		blockModules.add(module);
	}

	public static void registerModule(IDropProcessor module) {
		dropModules.add(module);
	}
}
