package com.asaskevich.smartcursor.keyboard;

import java.io.File;

import com.asaskevich.smartcursor.RenderHandler;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

	private RenderHandler renderHandler;
	private File configFile;

	public KeyInputHandler(RenderHandler r, File file) {
		this.renderHandler = r;
		this.configFile = file;
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindler.renderBlockDamage.isPressed()) {
			renderHandler.invertRender();
			Configuration config = new Configuration(configFile);
			config.load();
			config.get("smartCursor", "enabled", renderHandler.getRender()).set(renderHandler.getRender());
			config.save();
		}
	}
}