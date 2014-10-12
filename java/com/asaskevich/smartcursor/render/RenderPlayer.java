package com.asaskevich.smartcursor.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.RenderHelper;
import com.asaskevich.smartcursor.utils.Setting;

public class RenderPlayer {
	public Minecraft	mc;
	public int			width;
	public int			height;

	public RenderPlayer() {
		this.mc = Minecraft.getMinecraft();
		this.width = mc.displayWidth;
		this.height = mc.displayHeight;
	}

	public void render(EntityPlayer player, RenderHandler render) {
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		FontRenderer fontRender = mc.fontRenderer;
		width = res.getScaledWidth();
		height = res.getScaledHeight();
		mc.entityRenderer.setupOverlayRendering();
		int color = 0xFFFFFF;
		if (Setting.playerStyle == 0) {
			int x = 4;
			int y = 4;
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add("Score: " + player.getScore());
			if (player.getTeam() != null) list.add("Team: " + player.getTeam().getRegisteredName());
			ItemStack[] items = player.getLastActiveItems();
			boolean h = player.getHeldItem() != null;
			for (ItemStack item : items)
				if (item != null) h = true;
			if (h) {
				list.add("Equipment:");
				list.add(" - " + player.getHeldItem().getDisplayName() + (player.getHeldItem().isItemEnchanted() ? " [Ench]" : ""));
				for (ItemStack item : items)
					if (item != null && item != player.getHeldItem()) list.add(" - " + item.getDisplayName() + (item.isItemEnchanted() ? " [Ench]" : ""));
			}
			String text = String.format("%s: %d/%d", player.getDisplayName(), (int) player.getHealth(), (int) player.getMaxHealth());
			int maxW = fontRender.getStringWidth(text) + 16;
			for (int i = 1; i < list.size(); i++)
				maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
			if (Setting.showTooltipInRightCorner) x = width - maxW;
			RenderHelper.drawRect(x - 5, 0, x + maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1, 0x555555, Setting.transparent);
			RenderHelper.drawRect(x - 4, 0, x + maxW, 8 + fontRender.FONT_HEIGHT * list.size(), 0x00212121, Setting.transparent);
			// Icons
			mc.entityRenderer.setupOverlayRendering();
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			mc.getTextureManager().bindTexture(render.iconSheet);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glScalef(fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8);
			mc.ingameGUI.drawTexturedModalRect(x + 4 + fontRender.getStringWidth(text), 4, 34, 0, 9, 9);
			mc.ingameGUI.drawTexturedModalRect(x + 4 + fontRender.getStringWidth(text), 4, 52, 0, 8, 8);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			for (int i = 1; i < list.size(); i++)
				fontRender.drawStringWithShadow(list.get(i), x, y + fontRender.FONT_HEIGHT * i, color);
			fontRender.drawStringWithShadow(text, x, y, color);
		}
		if (Setting.playerStyle == 1) {
			color = 0xFFFFFF;
			int x = width / 2 + 4;
			int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
			String text = String.format("%.0fx", player.getHealth());
			String mobName = player.getCommandSenderName();
			fontRender.drawStringWithShadow(text, x, y, color);
			fontRender.drawStringWithShadow(mobName, width / 2 + 4, height / 2 + 2, color);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			mc.getTextureManager().bindTexture(render.iconSheet);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glScalef(fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8);
			mc.ingameGUI.drawTexturedModalRect(x + 4 + fontRender.getStringWidth(text), y, 52, 0, 8, 8);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		} else if (Setting.playerStyle == 2) {
			float f = player.getMaxHealth();
			float d = player.getHealth();
			String mobName = player.getCommandSenderName();
			int x = width / 2 - 25;
			int y = height / 2 + fontRender.FONT_HEIGHT * 2 + 4;
			Gui.drawRect(x - 1, y - 1, x + 52, y + fontRender.FONT_HEIGHT / 2 + 1, 0xFF00DD00);
			Gui.drawRect(x, y, x + (int) (d / f * 50), y + fontRender.FONT_HEIGHT / 2, 0xFFDD0000);
			fontRender.drawStringWithShadow(mobName, width / 2 - fontRender.getStringWidth(mobName) / 2, height / 2 + fontRender.FONT_HEIGHT + 2, 0xFFFFFF);
		} else if (Setting.playerStyle == 3) {
			int cnt = (int) player.getHealth();
			int cntMax = (int) player.getMaxHealth();
			if (player.getMaxHealth() > Setting.maxHeartCount) {
				float d = player.getMaxHealth() / (float) Setting.maxHeartCount;
				cnt = (int) (player.getHealth() / d);
				cntMax = (int) (player.getMaxHealth() / d);
			}
			String mobName = player.getCommandSenderName();
			int x = width / 2 - (cntMax * 5) / 2;
			int y = height / 2 + fontRender.FONT_HEIGHT * 2 + 4;
			fontRender.drawStringWithShadow(mobName, width / 2 - fontRender.getStringWidth(mobName) / 2, height / 2 + fontRender.FONT_HEIGHT + 2, 0xFFFFFF);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			mc.getTextureManager().bindTexture(render.iconSheet);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glScalef(fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8);
			for (int i = 0; i < cntMax; i++) {
				mc.ingameGUI.drawTexturedModalRect(x + i * 5, y, 34, 0, 9, 9);
				if (i < cnt) mc.ingameGUI.drawTexturedModalRect(x + i * 5, y, 52, 0, 8, 8);
			}
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
}
