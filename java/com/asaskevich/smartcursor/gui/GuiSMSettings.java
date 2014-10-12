package com.asaskevich.smartcursor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.SmartCursor;
import com.asaskevich.smartcursor.utils.Setting;

public class GuiSMSettings
		extends GuiScreen {
	private RenderHandler	renderHandler;
	private int				w;
	private int				h;
	private int				btnW;
	private int				btnH;
	private int				fH;

	public GuiSMSettings(RenderHandler renderHandler) {
		this.renderHandler = renderHandler;
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		w = this.width;
		h = this.height;
		btnW = w / 4;
		btnH = this.fontRendererObj.FONT_HEIGHT * 2;
		fH = this.fontRendererObj.FONT_HEIGHT;
		this.buttonList.clear();
		// Enable/Disable all mod
		this.buttonList.add(new GuiOptionButton(0, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2, btnW, btnH, Setting.isEnabled ? "ON" : "OFF"));
		// Enable/Disable block damage and choose style
		this.buttonList.add(new GuiOptionButton(1, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 1, btnW, btnH, renderHandler.getStyleName()));
		// Enable/Disable mob indicator and choose style
		this.buttonList.add(new GuiOptionButton(2, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 2, btnW, btnH, renderHandler.getMobStyleName()));
		// Show Drop info
		this.buttonList.add(new GuiOptionButton(3, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 3, btnW, btnH, Setting.showDropInformation ? "ON" : "OFF"));
		// Show Drop enchs
		this.buttonList.add(new GuiOptionButton(4, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 4, btnW, btnH, Setting.showEnchantments ? "ON" : "OFF"));
		// Show Drop durability
		this.buttonList.add(new GuiOptionButton(5, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 5, btnW, btnH, Setting.showDurability ? "ON" : "OFF"));
		// Show XPorb
		this.buttonList.add(new GuiOptionButton(6, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 6, btnW, btnH, Setting.showXPOrb ? "ON" : "OFF"));
		// Temp
		this.buttonList.add(new GuiOptionButton(8, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 7, btnW, btnH, Setting.showPlayerInformation ? "ON" : "OFF"));
		this.buttonList.add(new GuiOptionButton(9, w / 2 + btnW / 2, h / 7 - 60 + 50 - btnH / 2 + (btnH + fH / 4) * 8, btnW, btnH, Setting.showBlockInformation ? "ON" : "OFF"));
		// Advanced options
		this.buttonList.add(new GuiOptionButton(7, w - btnW - 5, h - btnH - 5, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.advanced")));
		// Small fix
		for (int i = 1; i < this.buttonList.size(); i++) {
			GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(i);
			btn.enabled = Setting.isEnabled;
		}
		if (Setting.isEnabled) {
			GuiOptionButton btn = (GuiOptionButton) this.buttonList.get(4);
			btn.enabled = Setting.showDropInformation;
			btn = (GuiOptionButton) this.buttonList.get(5);
			btn.enabled = Setting.showDropInformation;
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
				btn.enabled = Setting.showDropInformation;
				btn = (GuiOptionButton) this.buttonList.get(5);
				btn.enabled = Setting.showDropInformation;
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
				btn.enabled = Setting.showDropInformation;
				btn = (GuiOptionButton) this.buttonList.get(5);
				btn.enabled = Setting.showDropInformation;
			}
			button.displayString = Setting.showDropInformation ? "ON" : "OFF";
		}
		if (button.id == 4) {
			renderHandler.invertEnchInfo();
			button.displayString = Setting.showEnchantments ? "ON" : "OFF";
		}
		if (button.id == 5) {
			renderHandler.invertDurInfo();
			button.displayString = Setting.showDurability ? "ON" : "OFF";
		}
		if (button.id == 6) {
			renderHandler.invertXPInfo();
			button.displayString = Setting.showXPOrb ? "ON" : "OFF";
		}
		if (button.id == 8) {
			renderHandler.invertPlayerInfo();
			button.displayString = Setting.showPlayerInformation ? "ON" : "OFF";
		}
		if (button.id == 9) {
			renderHandler.invertBlockInfo();
			button.displayString = Setting.showBlockInformation ? "ON" : "OFF";
		}
		if (button.id == 7) mc.displayGuiScreen(new GuiAdvancedSettings(renderHandler));
		Setting.updateSettings(SmartCursor.config);
		Setting.syncConfig(SmartCursor.config);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1) this.mc.thePlayer.closeScreen();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.title"), w / 2, (h / 7 - 10) / 2, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.isEnabled"), w / 4, h / 7 - 60 + 50, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showBlockDamage"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 1, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showMobHealth"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 2, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showDropInfo"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 3, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showDropEnchantments"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 4, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showDurability"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 5, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showXPorbInfo"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 6, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showPlayerInfo"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 7, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showBlockInfo"), w / 4, h / 7 - 60 + 50 + (btnH + fH / 4) * 8, 16777215);
		super.drawScreen(par1, par2, par3);
	}
}
