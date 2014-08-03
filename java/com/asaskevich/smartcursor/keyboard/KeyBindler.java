package com.asaskevich.smartcursor.keyboard;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindler {
	public static KeyBinding renderBlockDamage;

	public static void init() {
		renderBlockDamage = new KeyBinding("Smart Cursor Enable/Disable", Keyboard.KEY_F, "SmartCursor");
		ClientRegistry.registerKeyBinding(renderBlockDamage);
	}
}
