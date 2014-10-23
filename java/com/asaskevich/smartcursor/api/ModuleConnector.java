package com.asaskevich.smartcursor.api;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;

public class ModuleConnector
		extends Event {
	public static final int	ENTITY_PROCESSOR	= 0, PLAYER_PROCESSOR = 1, DROP_PROCESSOR = 2, BLOCK_PROCESSOR = 3;
	private IModule			module;
	private int				type;

	public ModuleConnector(IEntityProcessor module) {
		this.module = module;
		this.type = ENTITY_PROCESSOR;
	}

	public ModuleConnector(IPlayerProcessor module) {
		this.module = module;
		this.type = PLAYER_PROCESSOR;
	}

	public ModuleConnector(IDropProcessor module) {
		this.module = module;
		this.type = DROP_PROCESSOR;
	}

	public ModuleConnector(IBlockProcessor module) {
		this.module = module;
		this.type = BLOCK_PROCESSOR;
	}

	public IModule getModule() {
		return this.module;
	}

	public int getType() {
		return this.type;
	}

	public static void connectModule(IEntityProcessor module) {
		MinecraftForge.EVENT_BUS.post(new ModuleConnector(module));
	}

	public static void connectModule(IPlayerProcessor module) {
		MinecraftForge.EVENT_BUS.post(new ModuleConnector(module));
	}

	public static void connectModule(IDropProcessor module) {
		MinecraftForge.EVENT_BUS.post(new ModuleConnector(module));
	}

	public static void connectModule(IBlockProcessor module) {
		MinecraftForge.EVENT_BUS.post(new ModuleConnector(module));
	}
}
