package com.asaskevich.smartcursor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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
						if (Setting.mobStyle != 3 && Setting.displayMobAdv) {
							int color = 0xFFFFFF;
							int x = 4;
							int y = 4;
							List<String> list = new ArrayList<String>();
							list.add("");
							if (entity instanceof EntityTameable) {
								EntityTameable tame = (EntityTameable) entity;
								if (tame.isTamed()) list.add("Tamed by " + tame.getOwnerName());
								else list.add("Not tamed!");
								if (tame.isSitting()) list.add("Mob sitting");
							}
							if (entity instanceof EntityHorse) {
								EntityHorse horse = (EntityHorse) entity;
								list.add("Jump Strength: " + String.format("%.3f", horse.getHorseJumpStrength()));
								if (horse.isTame()) list.add("Tamed by " + horse.getOwnerName());
								else list.add("Not tamed!");
							}
							if (entity instanceof EntityVillager) {
								EntityVillager villager = (EntityVillager) entity;
								switch (villager.getProfession()) {
									case 0:
										list.add("Profession: Farmer");
										break;
									case 1:
										list.add("Profession: Librarian");
										break;
									case 2:
										list.add("Profession: Priest");
										break;
									case 3:
										list.add("Profession: Blacksmith");
										break;
									case 4:
										list.add("Profession: Butcher");
										break;
									default:
										list.add("Profession: Generic");
										break;
								}
							}
							if (entity instanceof EntityAgeable) {
								EntityAgeable age = (EntityAgeable) entity;
								if (age.getGrowingAge() < 0)
									list.add("Child, will grow after ~" + Math.abs(age.getGrowingAge() / 20) + " sec.");
							}
							if (entity instanceof EntityCreature) {
								EntityCreature cre = (EntityCreature) entity;
								if (cre.hasHome())
									list.add("Mob has home at [" + cre.getHomePosition().posX + "] [" + cre.getHomePosition().posZ + "]");
							}
							if (entity.isBurning()) list.add("Mob is burning");
							if (entity.isEntityInvulnerable()) list.add("Mob is invulnerable");
							if (entity.isEntityUndead()) list.add("Mob is undead");
							if (entity.isImmuneToFire()) list.add("Mob is immune to fire");
							if (entity instanceof EntityMob) list.add("Mob is agressive!");
							else if (entity instanceof EntityAnimal) list.add("Mob is passive");
							String text = String.format("%s: %d/%d", entity.getCommandSenderName(), (int) entity.getHealth(),
									(int) entity.getMaxHealth());
							int maxW = fontRender.getStringWidth(text) + 16;
							for (int i = 1; i < list.size(); i++)
								maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
							Gui.drawRect(0, 0, maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1, 0xFF555555);
							Gui.drawRect(0, 0, maxW, 8 + fontRender.FONT_HEIGHT * list.size(), 0xFF212121);
							// Icons
							mc.entityRenderer.setupOverlayRendering();
							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
							mc.getTextureManager().bindTexture(iconSheet);
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							GL11.glScalef(fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8, fontRender.FONT_HEIGHT / 8);
							mc.ingameGUI.drawTexturedModalRect(6 + fontRender.getStringWidth(text), 4, 34, 0, 9, 9);
							mc.ingameGUI.drawTexturedModalRect(6 + fontRender.getStringWidth(text), 4, 52, 0, 8, 8);
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
							if (entity.getMaxHealth() > Setting.maxHeartCount) {
								float d = entity.getMaxHealth() / (float) Setting.maxHeartCount;
								cnt = (int) (entity.getHealth() / d);
								cntMax = (int) (entity.getMaxHealth() / d);
							}
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
						if (Setting.dropStyle == 0) {
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
						} else {
							ItemStack it = item.getEntityItem();
							int color = 0xFFFFFF;
							int x = 4;
							int y = 4;
							List<String> list = new ArrayList<String>();
							list.add("");
							int maxDamage = item.getEntityItem().getMaxDamage();
							int damage = item.getEntityItem().getItemDamage();
							String text = "";
							if (damage > 0 && maxDamage > 0 && maxDamage - damage > 0) text = String.format("%s: %d/%d",
									it.getDisplayName(), maxDamage - damage, maxDamage);
							else text = it.getDisplayName();
							if (it.getItem() instanceof ItemEnchantedBook) {
								ItemEnchantedBook book = (ItemEnchantedBook) it.getItem();
								list.add("Book has following enchantments:");
								NBTTagList nbttaglist = book.func_92110_g(it);
								if (nbttaglist != null) {
									for (int i = 0; i < nbttaglist.tagCount(); ++i) {
										short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
										short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
										if (Enchantment.enchantmentsList[short1] != null) {
											list.add(" - " + Enchantment.enchantmentsList[short1].getTranslatedName(short2));
										}
									}
								}
							}
							if (it.getItem() instanceof ItemFood) {
								ItemFood food = (ItemFood) it.getItem();
								list.add("Heal amount: " + food.func_150905_g(it));
								if (food.isWolfsFavoriteMeat()) list.add("Wolfs favorite meat!");
							}
							if (it.getItem().isPotionIngredient(it)) list.add("Possible to use in potions");
							list.add("Count: " + it.stackSize);
							if (it.isStackable())
								list.add("Stackable" + (it.getMaxStackSize() > 1 ? " in " + it.getMaxStackSize() + " items" : ""));
							if (it.isItemDamaged()) list.add("Item damaged");
							if (it.isItemEnchantable()) list.add("Item enchantable");
							if (it.getHasSubtypes()) list.add("Item has subtypes");
							if (it.hasEffect()) list.add("Item has effect");
							if (it.isItemEnchanted()) {
								list.add("Item enchanted with following:");
								NBTTagList enchs = item.getEntityItem().getEnchantmentTagList();
								if (enchs != null) {
									for (int i = 0; i < enchs.tagCount(); i++) {
										NBTTagCompound tag = enchs.getCompoundTagAt(i);
										short id = tag.getShort("id");
										short lvl = tag.getShort("lvl");
										Enchantment e = Enchantment.enchantmentsList[id];
										String enStr = e.getTranslatedName(lvl);
										list.add(" - " + enStr);
									}
								}
							}
							int maxW = fontRender.getStringWidth(text) + 16;
							for (int i = 1; i < list.size(); i++)
								maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
							Gui.drawRect(0, 0, maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1, 0xFF555555);
							Gui.drawRect(0, 0, maxW, 8 + fontRender.FONT_HEIGHT * list.size(), 0xFF212121);
							for (int i = 1; i < list.size(); i++)
								fontRender.drawStringWithShadow(list.get(i), x, y + fontRender.FONT_HEIGHT * i, color);
							fontRender.drawStringWithShadow(text, x, y, color);
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

	public String getDropStyleName() {
		if (Setting.dropStyle == 0) return "NEAR CURSOR";
		if (Setting.dropStyle == 1) return "IN CORNER";
		return "";
	}

	public void setDropNextStyle() {
		Setting.dropStyle++;
		Setting.dropStyle %= 2;
	}

	// showXPOrb
	public void invertXPInfo() {
		Setting.showXPOrb = !Setting.showXPOrb;
	}

	public void invertMobInfo() {
		Setting.displayMobAdv = !Setting.displayMobAdv;
	}

	public void nextMaxHeart() {
		Setting.maxHeartCount += 5;
		Setting.maxHeartCount %= 55;
		if (Setting.maxHeartCount < 10) Setting.maxHeartCount = 10;
	}

	public void nextLookDistance() {
		Setting.lookDistance += 5;
		Setting.lookDistance %= 95;
		if (Setting.lookDistance < 10) Setting.lookDistance = 10;
	}
}
