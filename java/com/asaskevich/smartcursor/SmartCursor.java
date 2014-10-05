package com.asaskevich.smartcursor;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import com.asaskevich.smartcursor.keyboard.KeyBindler;
import com.asaskevich.smartcursor.keyboard.KeyInputHandler;
import com.asaskevich.smartcursor.mod.ModInfo;
import com.asaskevich.smartcursor.proxy.CommonProxy;
import com.asaskevich.smartcursor.utils.Setting;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, canBeDeactivated = true)
@SideOnly(Side.CLIENT)
public class SmartCursor {
	public static CommonProxy proxy;
	public static RenderHandler renderHandler;
	public static KeyInputHandler keyInputHandler;
	public static Minecraft mc;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		// Init
		Setting.loadSettings(event.getSuggestedConfigurationFile());
		mc = Minecraft.getMinecraft();
		renderHandler = new RenderHandler(mc);
		keyInputHandler = new KeyInputHandler(renderHandler, event.getSuggestedConfigurationFile());
		KeyBindler.init();
		// Register handlers
		MinecraftForge.EVENT_BUS.register(renderHandler);
		FMLCommonHandler.instance().bus().register(keyInputHandler);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {}
}
