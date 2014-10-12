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
			if (entity instanceof EntityTameable) {
				EntityTameable tame = (EntityTameable) entity;
				if (tame.isTamed()) {
					if (tame.getOwner() != null) list.add(StatCollector.translateToLocal("smartcursor.mob.tamedBy") + tame.getOwner().getCommandSenderName());
					else list.add(StatCollector.translateToLocal("smartcursor.mob.tamed"));
				} else list.add(StatCollector.translateToLocal("smartcursor.mob.notTamed"));
				if (tame.isSitting()) list.add(StatCollector.translateToLocal("smartcursor.mob.isSitting"));
			}
			if (entity instanceof EntityHorse) {
				EntityHorse horse = (EntityHorse) entity;
				list.add(StatCollector.translateToLocal("smartcursor.mob.jumpStrength") + String.format("%.3f", Utils.round(horse.getHorseJumpStrength(), 4)));
				list.add(StatCollector.translateToLocal("smartcursor.mob.horseSpeed") + String.format("%.3f", Utils.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4)));
				if (horse.isTame()) list.add(StatCollector.translateToLocal("smartcursor.mob.tamed"));
				else list.add(StatCollector.translateToLocal("smartcursor.mob.notTamed"));
			}
			if (entity instanceof EntityVillager) {
				EntityVillager villager = (EntityVillager) entity;
				switch (villager.getProfession()) {
					case 0:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession_0"));
						break;
					case 1:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession_1"));
						break;
					case 2:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession_2"));
						break;
					case 3:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession_3"));
						break;
					case 4:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession_4"));
						break;
					default:
						list.add(StatCollector.translateToLocal("smartcursor.mob.profession"));
						break;
				}
				if (villager.isTrading()) list.add(StatCollector.translateToLocal("smartcursor.mob.trade"));
			}
			if (entity instanceof EntityAgeable) {
				EntityAgeable age = (EntityAgeable) entity;
				if (age.getGrowingAge() < 0) list.add(StatCollector.translateToLocal("smartcursor.mob.child") + Math.abs(age.getGrowingAge() / 20) + StatCollector.translateToLocal("smartcursor.mob.sec"));
			}
			if (entity.getTeam() != null) list.add(StatCollector.translateToLocal("smartcursor.player.team") + entity.getTeam().getRegisteredName());
			if (entity.isWet()) list.add(StatCollector.translateToLocal("smartcursor.mob.isWet"));
			if (entity.isSprinting()) list.add(StatCollector.translateToLocal("smartcursor.mob.isSprinting"));
			if (entity.isRiding()) list.add(StatCollector.translateToLocal("smartcursor.mob.isRiding"));
			if (entity.isBurning()) list.add(StatCollector.translateToLocal("smartcursor.mob.isBurning"));
			if (entity.isEntityInvulnerable()) list.add(StatCollector.translateToLocal("smartcursor.mob.isEntityInvulnerable"));
			if (entity.isEntityUndead()) list.add(StatCollector.translateToLocal("smartcursor.mob.isUndead"));
			if (entity.isImmuneToFire()) list.add(StatCollector.translateToLocal("smartcursor.mob.isImmuneToFire"));
			if (entity instanceof EntityMob || entity instanceof IMob) list.add(StatCollector.translateToLocal("smartcursor.mob.isAgressive"));
			else if (entity instanceof EntityAnimal) list.add(StatCollector.translateToLocal("smartcursor.mob.isPassive"));
			if (entity instanceof EntityCreature) {
				EntityCreature cre = (EntityCreature) entity;
				ItemStack[] items = cre.getLastActiveItems();
				boolean h = false;
				for (ItemStack item : items)
					if (item != null) h = true;
				if (h) {
					list.add(StatCollector.translateToLocal("smartcursor.player.equipment"));
					for (ItemStack item : items)
						if (item != null) list.add(" - " + item.getDisplayName() + (item.isItemEnchanted() ? StatCollector.translateToLocal("smartcursor.player.ench") : ""));
				}
			}
			String text = String.format("%s: %d/%d", entity.getCommandSenderName(), (int) entity.getHealth(), (int) entity.getMaxHealth());
			int maxW = fontRender.getStringWidth(text) + 16;
			for (int i = 1; i < list.size(); i++)
				maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
			if (Setting.showTooltipInRightCorner) x = width - maxW;
			// TODO
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
