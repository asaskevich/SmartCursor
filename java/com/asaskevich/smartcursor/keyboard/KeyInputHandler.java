package com.asaskevich.smartcursor.keyboard;

import java.io.File;
import net.minecraft.client.Minecraft;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.gui.GuiSMSettings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
	private Minecraft mc = Minecraft.getMinecraft();
	private GuiSMSettings gui;

	public KeyInputHandler(RenderHandler r, File file) {
		this.gui = new GuiSMSettings(r, file);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindler.renderBlockDamage.isPressed()) {
			mc.displayGuiScreen(gui);
		}
	}
}