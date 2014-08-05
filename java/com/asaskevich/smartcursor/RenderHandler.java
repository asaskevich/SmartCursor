package com.asaskevich.smartcursor;

import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import com.asaskevich.smartcursor.utils.EntityPonter;
import com.asaskevich.smartcursor.utils.Setting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
	private Minecraft mc;
	private int width;
	private int height;
	private Field curBlockDamage;
	private ResourceLocation iconSheet = new ResourceLocation("minecraft:textures/gui/icons.png");

	public RenderHandler(Minecraft mc) {
		this.mc = mc;
		this.width = mc.displayWidth;
		this.height = mc.displayHeight;
		this.curBlockDamage = null;
		try {
			for (Field field : PlayerControllerMP.class.getDeclaredFields())
				if (field.getName().equals("field_78770_f") || field.getName().equals("curBlockDamageMP")) {
					this.curBlockDamage = PlayerControllerMP.class.getDeclaredField(field.getName());
					curBlockDamage.setAccessible(true);
				}
		} catch (Exception exception) {
			System.err.println(exception);
		}
	}

	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent event) {
		if (!Setting.isEnabled) return;
		try {
			if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
				MovingObjectPosition mop = mc.renderViewEntity.rayTrace(200, 1F);
				EntityPonter.getEntityLookingAt(1F);
				// Block
				if (mop != null) {
					Block blockLookingAt = mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
					// /////////////
					if (curBlockDamage != null) {
						float damage = curBlockDamage.getFloat(Minecraft.getMinecraft().playerController);
						if (!Block.isEqualTo(blockLookingAt, Blocks.air) && damage > 0) {
							ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
							FontRenderer fontRender = mc.fontRenderer;
							width = res.getScaledWidth();
							height = res.getScaledHeight();
							int x = width / 2 + 4;
							int y = height / 2 + 2;
							if (Setting.blockDamageStyle == 0) {
								mc.entityRenderer.setupOverlayRendering();
								String text = String.format("%.1f%%", damage * 100);
								int color = 0xFFFFFF;
								fontRender.drawStringWithShadow(text, x, y, color);
							} else if (Setting.blockDamageStyle == 1) {
								x = width / 2 + 4;
								y = height / 2 + 4;
								Gui.drawRect(x - 1, y - 1, x + 31, y + fontRender.FONT_HEIGHT / 2 + 1, 0xFFDD0000);
								Gui.drawRect(x, y, x + (int) (damage * 30), y + fontRender.FONT_HEIGHT / 2, 0xFF00DD00);
							}
						}
					}
				}
				// Mob
				if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
					Entity target = mc.objectMouseOver.entityHit;
					if (target instanceof EntityLiving) {
						EntityLiving entity = (EntityLiving) target;
						ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						if (Setting.mobStyle == 0) {
							int color = 0xFFFFFF;
							int x = width / 2 + 4;
							int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
							String text = String.format("%.0fx", entity.getHealth());
							String mobName = entity.getCommandSenderName();
							fontRender.drawStringWithShadow(text, x, y, color);
							fontRender.drawStringWithShadow(mobName, width / 2 + 4, height / 2 + 2, color);
							mc.getTextureManager().bindTexture(iconSheet);
							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
							mc.getTextureManager().bindTexture(iconSheet);
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
							fontRender.drawStringWithShadow(mobName, width / 2 - fontRender.getStringWidth(mobName) / 2, height / 2
									+ fontRender.FONT_HEIGHT + 2, 0xFFFFFF);
						} else if (Setting.mobStyle == 2) {
							int cnt = (int) entity.getHealth();
							int cntMax = (int) entity.getMaxHealth();
							String mobName = entity.getCommandSenderName();
							int x = width / 2 - (cntMax * 5) / 2;
							int y = height / 2 + fontRender.FONT_HEIGHT * 2 + 4;
							fontRender.drawStringWithShadow(mobName, width / 2 - fontRender.getStringWidth(mobName) / 2, height / 2
									+ fontRender.FONT_HEIGHT + 2, 0xFFFFFF);
							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
							mc.getTextureManager().bindTexture(iconSheet);
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
					// Item
					if (target instanceof EntityItem && Setting.showDrop) {
						EntityItem item = (EntityItem) target;
						ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						int color = 0xFFFFFF;
						String text = (int) item.getEntityItem().stackSize + "x " + item.getEntityItem().getDisplayName();
						NBTTagList enchs = item.getEntityItem().getEnchantmentTagList();
						if (enchs != null && Setting.showEnch) {
							for (int i = 0; i < enchs.tagCount(); i++) {
								NBTTagCompound tag = enchs.getCompoundTagAt(i);
								short id = tag.getShort("id");
								short lvl = tag.getShort("lvl");
								Enchantment e = Enchantment.enchantmentsList[id];
								String enStr = e.getTranslatedName(lvl);
								int x = width / 2 + 4;
								int y = height / 2 + 2 + fontRender.FONT_HEIGHT * i;
								fontRender.drawStringWithShadow(enStr, x, y, color);
							}
						}
						int x = width / 2 - 4 - fontRender.getStringWidth(text);
						int y = height / 2 - 2 - fontRender.FONT_HEIGHT;
						fontRender.drawStringWithShadow(text, x, y, color);
						if (Setting.showDur) {
							int maxDamage = item.getEntityItem().getMaxDamage();
							int damage = item.getEntityItem().getItemDamage();
							if (damage > 0 && maxDamage > 0 && maxDamage - damage > 0) {
								x = width / 2 + 4;
								y = height / 2 - 2 - fontRender.FONT_HEIGHT;
								text = String.format("%d/%d", maxDamage - damage, maxDamage);
								fontRender.drawStringWithShadow(text, x, y, color);
							}
						}
					}
					// XPOrb
					if (target instanceof EntityXPOrb && Setting.showXPOrb) {
						EntityXPOrb xp = (EntityXPOrb) target;
						ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						int color = 0xFFFFFF;
						String text = (int) xp.xpValue + " XP";
						int x = width / 2 - 4 - fontRender.getStringWidth(text);
						int y = height / 2 + 2;
						fontRender.drawStringWithShadow(text, x, y, color);
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	// /////////////////////////////// Rendering
	public void invertRender() {
		Setting.isEnabled = !Setting.isEnabled;
	}

	// /////////////////////////////
	public String getStyleName() {
		if (Setting.blockDamageStyle == 0) return "PERCENTS";
		if (Setting.blockDamageStyle == 1) return "PROGRESS BAR";
		if (Setting.blockDamageStyle == 2) return "OFF";
		return "";
	}

	public void setNextStyle() {
		Setting.blockDamageStyle++;
		Setting.blockDamageStyle %= 3;
	}

	// /////////////////////////// Mob Indicator
	public String getMobStyleName() {
		if (Setting.mobStyle == 0) return "NUMERIC";
		if (Setting.mobStyle == 1) return "PROGRESS BAR";
		if (Setting.mobStyle == 2) return "ICONS";
		if (Setting.mobStyle == 3) return "OFF";
		return "";
	}

	public void setMobNextStyle() {
		Setting.mobStyle++;
		Setting.mobStyle %= 4;
	}

	// ////////////////////////// Drop info
	public void invertDropInfo() {
		Setting.showDrop = !Setting.showDrop;
	}

	public void invertEnchInfo() {
		Setting.showEnch = !Setting.showEnch;
	}

	public void invertDurInfo() {
		Setting.showDur = !Setting.showDur;
	}

	// showXPOrb
	public void invertXPInfo() {
		Setting.showXPOrb = !Setting.showXPOrb;
	}
}
