package com.asaskevich.smartcursor.gui;

import java.io.File;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.utils.Setting;

public class GuiSMSettings extends GuiScreen {
	private RenderHandler renderHandler;
	private File configFile;
	private int w;
	private int h;
	private int btnW;
	private int btnH;
	private int fH;

	public GuiSMSettings(RenderHandler r, File f) {
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
		// Enable/Disable all mod
		this.buttonList.add(new GuiOptionButton(0, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2, btnW, btnH, Setting.isEnabled ? "ON"
				: "OFF"));
		// Enable/Disable block damage and choose style
		this.buttonList.add(new GuiOptionButton(1, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 1, btnW, btnH,
				renderHandler.getStyleName()));
		// Enable/Disable mob indicator and choose style
		this.buttonList.add(new GuiOptionButton(2, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 2, btnW, btnH,
				renderHandler.getMobStyleName()));
		// Show Drop info
		this.buttonList.add(new GuiOptionButton(3, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 3, btnW, btnH,
				Setting.showDrop ? "ON" : "OFF"));
		// Show Drop enchs
		this.buttonList.add(new GuiOptionButton(4, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 4, btnW, btnH,
				Setting.showEnch ? "ON" : "OFF"));
		// Show Drop durability
		this.buttonList.add(new GuiOptionButton(5, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 5, btnW, btnH,
				Setting.showDur ? "ON" : "OFF"));
		// Show XPorb
		this.buttonList.add(new GuiOptionButton(6, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 6, btnW, btnH,
				Setting.showXPOrb ? "ON" : "OFF"));
		// Advanced options
		this.buttonList.add(new GuiOptionButton(7, w - btnW - 10, h - btnH - 10, btnW, btnH, "ADVANCED"));
		// Small fix
		for (int i = 1; i < this.buttonList.size(); i++) {
			GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(i);
			btn.enabled = Setting.isEnabled;
		}
		if (Setting.isEnabled) {
			GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(4);
			btn.enabled = Setting.showDrop;
			btn = (GuiOptionButton) this.buttonList.get(5);
			btn.enabled = Setting.showDrop;
		}
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			renderHandler.invertRender();
			button.displayString = Setting.isEnabled ? "ON" : "OFF";
			for (int i = 1; i < this.buttonList.size(); i++) {
				GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(i);
				btn.enabled = Setting.isEnabled;
			}
			if (Setting.isEnabled) {
				GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(4);
				btn.enabled = Setting.showDrop;
				btn = (GuiOptionButton) this.buttonList.get(5);
				btn.enabled = Setting.showDrop;
			}
		}
		if (button.id == 1) {
			renderHandler.setNextStyle();
			button.displayString = renderHandler.getStyleName();
		}
		if (button.id == 2) {
			renderHandler.setMobNextStyle();
			button.displayString = renderHandler.getMobStyleName();
		}
		if (button.id == 3) {
			renderHandler.invertDropInfo();
			if (Setting.isEnabled) {
				GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(4);
				btn.enabled = Setting.showDrop;
				btn = (GuiOptionButton) this.buttonList.get(5);
				btn.enabled = Setting.showDrop;
			}
			button.displayString = Setting.showDrop ? "ON" : "OFF";
		}
		if (button.id == 4) {
			renderHandler.invertEnchInfo();
			button.displayString = Setting.showEnch ? "ON" : "OFF";
		}
		if (button.id == 5) {
			renderHandler.invertDurInfo();
			button.displayString = Setting.showDur ? "ON" : "OFF";
		}
		if (button.id == 6) {
			renderHandler.invertXPInfo();
			button.displayString = Setting.showXPOrb ? "ON" : "OFF";
		}
		if (button.id == 7) {
			mc.displayGuiScreen(new GuiAdvancedSettings(renderHandler, configFile));
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
		this.drawCenteredString(this.fontRendererObj, "SmartCursor Settings", w / 2, h / 4 - 60 + 20, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Is SmartCursor Enabled:", w / 4, h / 4 - 60 + 50, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show current block damage:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 1, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show mob health:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 2, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show drop information:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 3, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show drop enchantments:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 4, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show drop durability:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 5, 16777215);
		this.drawCenteredString(this.fontRendererObj, "Show XPorb information:", w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 6, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}
