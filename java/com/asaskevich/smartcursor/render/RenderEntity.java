package com.asaskevich.smartcursor.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import com.asaskevich.smartcursor.RenderHandler;
import com.asaskevich.smartcursor.RenderHelper;
import com.asaskevich.smartcursor.SmartCursor;
import com.asaskevich.smartcursor.api.EntityModule;
import com.asaskevich.smartcursor.utils.Setting;
import com.asaskevich.smartcursor.utils.Utils;

public class RenderEntity {
	public Minecraft	mc;
	public int			width;
	public int			height;

	public RenderEntity() {
		this.mc = Minecraft.getMinecraft();
		this.width = mc.displayWidth;
		this.height = mc.displayHeight;
	}

	public void render(EntityLiving entity, RenderHandler render) {
		ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		FontRenderer fontRender = mc.fontRenderer;
		width = res.getScaledWidth();
		height = res.getScaledHeight();
		mc.entityRenderer.setupOverlayRendering();
		if (Setting.mobStyle != 3 && Setting.displayAdvInfoMob) {
			int color = 0xFFFFFF;
			int x = 4;
			int y = 4;
			List<String> list = new ArrayList<String>();
			list.add("");
			// Work with modules
			for (EntityModule module : SmartCursor.entityModules)
				module.process(list, entity);
			// Render
			String text = String.format("%s: %d/%d", entity.getCommandSenderName(), (int) entity.getHealth(), (int) entity.getMaxHealth());
			int maxW = fontRender.getStringWidth(text) + 16;
			for (int i = 1; i < list.size(); i++)
				maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
			if (Setting.showTooltipInRightCorner) x = width - maxW;
			// TODO
			RenderHelper.drawRect(x - 5, 0, x + maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1, 0x555555, Setting.transparent);
			RenderHelper.drawRect(x - 4, 0, x + maxW, 8 + fontRender.FONT_HEIGHT * list.size(), 0x010121, Setting.transparent);
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
		if (Setting.mobStyle == 0) {
			int color = 0xFFFFFF;
			int x = width / 2 + 4;
			int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
			String text = String.format("%.0fx", entity.getHealth());
			String mobName = entity.getCommandSenderName();
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
		} else if (Setting.mobStyle == 1) {
			float f = entity.getMaxHealth();
			float d = entity.getHealth();
			String mobName = entity.getCommandSenderName();
			int x = width / 2 - 25;
			int y = height / 2 + fontRender.FONT_HEIGHT * 2 + 4;
			Gui.drawRect(x - 1, y - 1, x + 52, y + fontRender.FONT_HEIGHT / 2 + 1, 0xFF00DD00);
			Gui.drawRect(x, y, x + (int) (d / f * 50), y + fontRender.FONT_HEIGHT / 2, 0xFFDD0000);
			fontRender.drawStringWithShadow(mobName, width / 2 - fontRender.getStringWidth(mobName) / 2, height / 2 + fontRender.FONT_HEIGHT + 2, 0xFFFFFF);
		} else if (Setting.mobStyle == 2) {
			int cnt = (int) entity.getHealth();
			int cntMax = (int) entity.getMaxHealth();
			if (entity.getMaxHealth() > Setting.maxHeartCount) {
				float d = entity.getMaxHealth() / (float) Setting.maxHeartCount;
				cnt = (int) (entity.getHealth() / d);
				cntMax = (int) (entity.getMaxHealth() / d);
			}
			String mobName = entity.getCommandSenderName();
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
