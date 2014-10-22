package com.asaskevich.smartcursor;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import com.asaskevich.smartcursor.api.Modules;
import com.asaskevich.smartcursor.keyboard.KeyBindler;
import com.asaskevich.smartcursor.keyboard.KeyInputHandler;
import com.asaskevich.smartcursor.mod.ModInfo;
import com.asaskevich.smartcursor.proxy.CommonProxy;
import com.asaskevich.smartcursor.utils.Setting;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, canBeDeactivated = true, guiFactory = "com.asaskevich.smartcursor.gui.GUIFactory")
@SideOnly(Side.CLIENT)
public class SmartCursor {
	public static CommonProxy		proxy;
	public static RenderHandler		renderHandler;
	public static KeyInputHandler	keyInputHandler;
	public static Minecraft			mc;
	public static Configuration		config;
	@Mod.Instance(ModInfo.ID)
	public static SmartCursor		instance;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		// Init
		config = new Configuration(event.getSuggestedConfigurationFile());
		Setting.syncConfig(config);
		mc = Minecraft.getMinecraft();
		renderHandler = new RenderHandler(mc);
		keyInputHandler = new KeyInputHandler(renderHandler);
		KeyBindler.init();
		// Register handlers
		MinecraftForge.EVENT_BUS.register(renderHandler);
		FMLCommonHandler.instance().bus().register(keyInputHandler);
		FMLCommonHandler.instance().bus().register(instance);
		// Register build-in plugins
		Plugins.init();
		Modules.loadConfig(config);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.modID.equals(ModInfo.ID)) Setting.syncConfig(config);
	}
}
