package com.asaskevich.smartcursor.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.Modules;
import com.asaskevich.smartcursor.SmartCursor;
import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.api.IDropProcessor;
import com.asaskevich.smartcursor.api.IEntityProcessor;
import com.asaskevich.smartcursor.api.IModule;
import com.asaskevich.smartcursor.api.IPlayerProcessor;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Edited net.minecraft.client.gui.GuiLanguage
public class GuiLoadedModules
		extends GuiScreen {
	private GuiLoadedModules.List	field_146450_f;
	private int						w;
	private int						h;
	private int						btnW;
	private int						btnH;

	public GuiLoadedModules() {
		this.initGui();
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		this.field_146450_f = new GuiLoadedModules.List();
		this.field_146450_f.registerScrollButtons(7, 8);
		this.buttonList.clear();
		w = this.width;
		h = this.height;
		btnW = w / 8;
		btnH = GuiLoadedModules.this.mc.getMinecraft().fontRenderer.FONT_HEIGHT * 2;
		this.buttonList.add(new GuiOptionButton(100, w - btnW - 25, h - 5 - btnH, btnW, btnH, StatCollector.translateToLocal("smartcursor.gui.switch")));
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 100) {
			Modules.switchModule(this.field_146450_f.modules.get(this.field_146450_f.lastClickId).getClass().getCanonicalName());
			Modules.syncConfig(SmartCursor.config);
		}
		field_146450_f.drawScreen(w, h, zLevel);
		field_146450_f.updateSlots(button.id);
		this.mc.displayGuiScreen(this);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1) {
			// Close screen and return to ingame screen by ESC
			this.mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int width, int height, float zLevel) {
		this.field_146450_f.drawScreen(width, height, zLevel);
		// TODO move to locales
		int fh = this.fontRendererObj.FONT_HEIGHT;
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.loadedModules"), this.width / 2, 16, 0xFFFFFF);
		this.drawString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.class") + ": " + this.field_146450_f.moduleClass, 25, this.height - fh * 3 - 5, 16777215);
		this.drawString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.name") + ": " + this.field_146450_f.moduleName, 25, this.height - fh * 2 - 5, 16777215);
		this.drawString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.author") + ": " + this.field_146450_f.moduleAuthor, 25, this.height - fh - 5, 16777215);
		super.drawScreen(width, height, zLevel);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	class List
			extends GuiSlot {
		private final java.util.List	moduleNames		= Lists.newArrayList();
		private final java.util.List	moduleClasses	= Lists.newArrayList();
		private final java.util.List	modules			= Lists.newArrayList();
		public int						lastClickId		= 0;
		public String					moduleClass		= "";
		public String					moduleName		= "";
		public String					moduleAuthor	= "";

		@SuppressWarnings("unchecked")
		public List() {
			super(GuiLoadedModules.this.mc, GuiLoadedModules.this.width, GuiLoadedModules.this.height, 32, GuiLoadedModules.this.height - GuiLoadedModules.this.mc.getMinecraft().fontRenderer.FONT_HEIGHT * 4, 18);
			for (IEntityProcessor module : Modules.entityModules) {
				moduleClasses.add(module.getClass().getSimpleName());
				moduleNames.add(module.getModuleName());
				modules.add(module);
			}
			for (IBlockProcessor module : Modules.blockModules) {
				moduleClasses.add(module.getClass().getSimpleName());
				moduleNames.add(module.getModuleName());
				modules.add(module);
			}
			for (IDropProcessor module : Modules.dropModules) {
				moduleClasses.add(module.getClass().getSimpleName());
				moduleNames.add(module.getModuleName());
				modules.add(module);
			}
			for (IPlayerProcessor module : Modules.playerModules) {
				moduleClasses.add(module.getClass().getSimpleName());
				moduleNames.add(module.getModuleName());
				modules.add(module);
			}
			IModule m = (IModule) Modules.entityModules.get(0);
			moduleClass = m.getClass().getSimpleName();
			moduleName = m.getModuleName();
			moduleAuthor = m.getAuthor();
		}

		protected int getSize() {
			return this.moduleNames.size();
		}

		/**
		 * The element in the slot that was clicked, boolean for whether it was double clicked or not
		 */
		protected void elementClicked(int id, boolean flag, int x, int y) {
			this.lastClickId = id;
			IModule m = (IModule) this.modules.get(id);
			this.moduleClass = m.getClass().getSimpleName();
			this.moduleName = m.getModuleName();
			this.moduleAuthor = m.getAuthor();
		}

		/**
		 * Returns true if the element passed in is currently selected
		 */
		protected boolean isSelected(int id) {
			return id == lastClickId;
		}

		public void updateSlots(int slot) {
			if (this.lastClickId == -1) return;
		}

		/**
		 * Return the height of the content being scrolled
		 */
		protected int getContentHeight() {
			return this.getSize() * 18;
		}

		@Override
		public int getListWidth() {
			return GuiLoadedModules.this.width - 40;
		}

		@Override
		protected int getScrollBarX() {
			return GuiLoadedModules.this.width - 20;
		}

		protected void drawBackground() {
			GuiLoadedModules.this.drawDefaultBackground();
		}

		protected void drawSlot(int id, int p_148126_2_, int yPosition, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
			try {
				FontRenderer fontRenderer = GuiLoadedModules.this.fontRendererObj;
				fontRenderer.setBidiFlag(true);
				String name = (String) this.moduleClasses.get(id);
				int w = GuiLoadedModules.this.fontRendererObj.getStringWidth(name);
				GuiLoadedModules.this.drawCenteredString(fontRenderer, name, 25 + w / 2, yPosition + 1, Modules.isActiveModule(this.modules.get(id).getClass().getCanonicalName()) ? 16777215 : 0x8f8f8f);
				name = Modules.isActiveModule(this.modules.get(id).getClass().getCanonicalName()) ? "ON" : "OFF";
				w = GuiLoadedModules.this.fontRendererObj.getStringWidth(name);
				GuiLoadedModules.this.drawCenteredString(fontRenderer, name, this.width - w / 2 - 25, yPosition + 1, Modules.isActiveModule(this.modules.get(id).getClass().getCanonicalName()) ? 16777215 : 0x8f8f8f);
				fontRenderer.setBidiFlag(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
