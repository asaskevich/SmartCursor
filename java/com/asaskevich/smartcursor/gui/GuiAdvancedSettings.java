package com.asaskevich.smartcursor.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.SmartCursor;
import com.asaskevich.smartcursor.utils.Setting;

public class GuiAdvancedSettings
		extends GuiScreen {
	private RenderHandler	renderHandler;
	private int				w;
	private int				h;
	private int				btnW;
	private int				btnH;
	private int				fH;

	public GuiAdvancedSettings(RenderHandler r) {
		this.renderHandler = r;
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		w = this.width;
		h = this.height;
		btnW = w / 4;
		btnH = this.fontRendererObj.FONT_HEIGHT * 2;
		fH = this.fontRendererObj.FONT_HEIGHT;
		this.buttonList.clear();
		this.buttonList.add(new GuiOptionButton(0, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 0, btnW, btnH, Setting.displayAdvInfoMob ? "ON" : "OFF"));
		this.buttonList.add(new CustomGuiOptionSlider(1, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 1, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.heartCount"), 5F, 50F, 1F,
				(float) Setting.maxHeartCount, this));
		this.buttonList.add(new GuiOptionButton(2, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 2, btnW, btnH, renderHandler.getDropStyleName()));
		this.buttonList.add(new CustomGuiOptionSlider(3, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 3, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.distance"), 1F, 100F, 1F,
				(float) Setting.lookDistance, this));
		this.buttonList
				.add(new CustomGuiOptionSlider(4, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 4, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.transparent"), 0F, 255F, 1F, Setting.transparent, this));
		this.buttonList.add(new GuiOptionButton(5, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 5, btnW, btnH, renderHandler.getPlayerStyleName()));
		this.buttonList.add(new GuiOptionButton(6, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 6, btnW, btnH, Setting.showTooltipInRightCorner ? StatCollector.translateToLocal("smartcursor.gui.rightCorner")
				: StatCollector.translateToLocal("smartcursor.gui.leftCorner")));
		this.buttonList.add(new CustomGuiOptionSlider(7, w / 2 + btnW / 2, h / 4 - 60 + 50 - btnH / 2 + (btnH + fH / 2) * 7, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.tooltipDelta"), 0F, 5000F, 1F, (float) Setting.delta,
				this));
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			renderHandler.invertMobInfo();
			button.displayString = Setting.displayAdvInfoMob ? "ON" : "OFF";
		}
		if (button.id == 2) {
			renderHandler.setDropNextStyle();
			button.displayString = renderHandler.getDropStyleName();
		}
		if (button.id == 5) {
			renderHandler.setPlayerNextStyle();
			button.displayString = renderHandler.getPlayerStyleName();
		}
		if (button.id == 6) {
			renderHandler.invertTooltipPlaceInfo();
			button.displayString = Setting.showTooltipInRightCorner ? StatCollector.translateToLocal("smartcursor.gui.rightCorner") : StatCollector.translateToLocal("smartcursor.gui.leftCorner");
		}
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
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.advancedTitle"), w / 2, h / 4 - 60 + 20, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showAdvancedInfoMobs"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 0, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.maxHeartCount"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 1, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.showAdvancedDropInfo"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 2, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.lookingDistance"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 3, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.transparent"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 4, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.styleOfPlayerInfo"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 5, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.tooltipPosition"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 6, 16777215);
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.displayTime"), w / 4, h / 4 - 60 + 50 + (btnH + fH / 2) * 7, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	public void updateSettings(CustomGuiOptionSlider slider) {
		if (slider.id == 1) Setting.maxHeartCount = slider.value;
		if (slider.id == 3) Setting.lookDistance = slider.value;
		if (slider.id == 4) Setting.transparent = (int) slider.value;
		if (slider.id == 7) Setting.delta = (int) slider.value;
		Setting.updateSettings(SmartCursor.config);
		Setting.syncConfig(SmartCursor.config);
	}
}
