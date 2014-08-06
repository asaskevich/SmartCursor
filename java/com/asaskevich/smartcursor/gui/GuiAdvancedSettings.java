package com.asaskevich.smartcursor.gui;

import java.io.File;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.utils.Setting;

public class GuiAdvancedSettings extends GuiScreen {
	private RenderHandler renderHandler;
	private File configFile;
	private int w;
	private int h;
	private int btnW;
	private int btnH;
	private int fH;

	public GuiAdvancedSettings(RenderHandler r, File f) {
		this.renderHandler = r;
		this.configFile = f;
	}

	public void initGui() {
		w = this.width;
		h = this.height;
		btnW = w / 4;
		btnH = this.fontRendererObj.FONT_HEIGHT * 2;
		fH = this.fontRendererObj.FONT_HEIGHT;
		this.buttonList.clear();
		// Enable/Disable block damage and choose style
		this.buttonList.add(new GuiOptionButton(0, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 0, btnW, btnH,
				Setting.displayMobAdv ? "ON" : "OFF"));
		this.buttonList.add(new GuiOptionButton(1, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 1, btnW, btnH,
				(int) Setting.maxHeartCount + "x"));
		this.buttonList.add(new GuiOptionButton(2, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 2, btnW, btnH,
				renderHandler.getDropStyleName()));
		this.buttonList.add(new GuiOptionButton(3, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 3, btnW, btnH,
				(int) Setting.lookDistance + " meters"));
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			renderHandler.invertMobInfo();
			button.displayString = Setting.displayMobAdv ? "ON" : "OFF";
		}
		if (button.id == 1) {
			renderHandler.nextMaxHeart();
			button.displayString = (int) Setting.maxHeartCount + "x";
		}
		if (button.id == 2) {
			renderHandler.setDropNextStyle();
			button.displayString = renderHandler.getDropStyleName();
		}
		if (button.id == 3) {
			renderHandler.nextLookDistance();
			button.displayString = (int) Setting.lookDistance + " meters";
		}
		Setting.updateSettings(configFile);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1) {
			this.mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "SmartCursor Advanced Settings", w / 2, h / 4 - 60 + 20, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Display advanced info for mobs:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 0,
				16777215);
		this.drawCenteredString(this.fontRendererObj, "Max. count of heart icons in row:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 1,
				16777215);
		this.drawCenteredString(this.fontRendererObj, "Display drop info:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 2, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Find mob looking at in:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 3, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}
