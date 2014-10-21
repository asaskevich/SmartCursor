package com.asaskevich.smartcursor.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.api.IDropProcessor;
import com.asaskevich.smartcursor.api.IEntityProcessor;
import com.asaskevich.smartcursor.api.IPlayerProcessor;
import com.asaskevich.smartcursor.api.Modules;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Edited net.minecraft.client.gui.GuiLanguage
public class GuiLoadedModules
		extends GuiScreen {
	private GuiLoadedModules.List	field_146450_f;

	public GuiLoadedModules() {
		this.initGui();
	}

	@SuppressWarnings("unchecked")
	public void initGui() {
		this.field_146450_f = new GuiLoadedModules.List();
		this.field_146450_f.registerScrollButtons(7, 8);
		this.buttonList.clear();
	}

	protected void actionPerformed(GuiButton button) {
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
		this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("smartcursor.gui.loadedModules"), this.width / 2, 16, 0xFFFFFF);
		super.drawScreen(width, height, zLevel);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
	class List
			extends GuiSlot {
		private final java.util.List	moduleNames	= Lists.newArrayList();
		private int						lastClickId	= -1;

		@SuppressWarnings("unchecked")
		public List() {
			super(GuiLoadedModules.this.mc, GuiLoadedModules.this.width, GuiLoadedModules.this.height, 32, GuiLoadedModules.this.height - 5, 18);
			for (IEntityProcessor module : Modules.entityModules) {
				moduleNames.add(module.getClass().getSimpleName());
			}
			for (IBlockProcessor module : Modules.blockModules) {
				moduleNames.add(module.getClass().getSimpleName());
			}
			for (IDropProcessor module : Modules.dropModules) {
				moduleNames.add(module.getClass().getSimpleName());
			}
			for (IPlayerProcessor module : Modules.playerModules) {
				moduleNames.add(module.getClass().getSimpleName());
			}
		}

		protected int getSize() {
			return this.moduleNames.size();
		}

		/**
		 * The element in the slot that was clicked, boolean for whether it was double clicked or not
		 */
		protected void elementClicked(int id, boolean flag, int x, int y) {
			this.lastClickId = id;
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
				GuiLoadedModules.this.drawCenteredString(fontRenderer, (String) this.moduleNames.get(id), this.width / 2, yPosition + 1, 16777215);
				fontRenderer.setBidiFlag(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
