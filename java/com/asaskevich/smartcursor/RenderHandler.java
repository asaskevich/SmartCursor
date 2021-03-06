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
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.api.IDropProcessor;
import com.asaskevich.smartcursor.mod.ModInfo;
import com.asaskevich.smartcursor.render.RenderEntity;
import com.asaskevich.smartcursor.render.RenderPlayer;
import com.asaskevich.smartcursor.utils.EntityPonter;
import com.asaskevich.smartcursor.utils.ModIdentification;
import com.asaskevich.smartcursor.utils.Setting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
	public Minecraft		mc;
	public int				width;
	public int				height;
	public Field			curBlockDamage;
	public ResourceLocation	iconSheet	= new ResourceLocation("minecraft:textures/gui/icons.png");
	private RenderPlayer	renderPlayer;
	private RenderEntity	renderEntity;
	private RenderItem		itemRender;

	public RenderHandler(Minecraft mc) {
		this.mc = mc;
		this.width = mc.displayWidth;
		this.height = mc.displayHeight;
		this.renderPlayer = new RenderPlayer();
		this.renderEntity = new RenderEntity();
		this.itemRender = new RenderItem();
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
			if (event.type == RenderGameOverlayEvent.ElementType.DEBUG) {
				ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
				FontRenderer fontRender = mc.fontRenderer;
				width = res.getScaledWidth();
				height = res.getScaledHeight();
				int y = height - fontRender.FONT_HEIGHT - 5;
				int x = 5;
				mc.entityRenderer.setupOverlayRendering();
				int color = 0xFFFFFF;
				fontRender.drawStringWithShadow("SmartCursor v" + ModInfo.VERSION, x, y, color);
			} else if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
				MovingObjectPosition mop = mc.renderViewEntity.rayTrace(15, 1F);
				EntityPonter.getEntityLookingAt(1F);
				// Block
				if (mop != null) {
					Block blockLookingAt = mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
					// /////////////
					if (curBlockDamage != null) {
						float damage = curBlockDamage.getFloat(Minecraft.getMinecraft().playerController);
						if (!Block.isEqualTo(blockLookingAt, Blocks.air) && damage > 0) {
							ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
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
					if (mc.objectMouseOver.entityHit == null && !Block.isEqualTo(blockLookingAt, Blocks.air) && Setting.showBlockInformation) {
						EntityPonter.pointedEntity = null;
						int color = 0xFFFFFF;
						int x = 4;
						int y = 4;
						List<String> list = new ArrayList<String>();
						int meta = mc.theWorld.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
						Item item = blockLookingAt.getItem(mc.theWorld, mop.blockX, mop.blockY, mop.blockZ);
						ItemStack stack = new ItemStack(blockLookingAt);
						if (item != null) stack = new ItemStack(blockLookingAt.getItem(mc.theWorld, mop.blockX, mop.blockY, mop.blockZ));
						stack.setItemDamage(meta);
						list.add(EnumChatFormatting.BOLD + "" + EnumChatFormatting.GOLD + stack.getDisplayName() + EnumChatFormatting.RESET);
						// Work with modules
						for (IBlockProcessor module : Modules.blockModules)
							if (Modules.isActiveModule(module.getClass().getCanonicalName())) module.process(list, blockLookingAt, meta, mc.theWorld, mop.blockX, mop.blockY, mop.blockZ);
						// ////
						ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
						FontRenderer fontRender = mc.fontRenderer;
						width = res.getScaledWidth();
						height = res.getScaledHeight();
						mc.entityRenderer.setupOverlayRendering();
						int maxW = 0;
						maxW = Math.max(maxW, fontRender.getStringWidth(list.get(0)) + 16);
						for (int i = 1; i < list.size(); i++)
							maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
						if (Setting.showTooltipInRightCorner) x = width - maxW;
						RenderHelper.drawRect(x - 5, 0, x + maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1 + 8, 0x555555, Setting.transparent);
						RenderHelper.drawRect(x - 4, 0, x + maxW, 8 + fontRender.FONT_HEIGHT * list.size() + 8, 0x010121, Setting.transparent);
						renderItem(stack, 0, x, y, 1);
						fontRender.drawStringWithShadow(list.get(0), x + 20, y + 4, color);
						for (int i = 1; i < list.size(); i++)
							fontRender.drawStringWithShadow(list.get(i), x, y + 8 + fontRender.FONT_HEIGHT * i, color);
					} else {
						if (EntityPonter.pointedEntity != null) {
							// Mobs
							Entity target = EntityPonter.pointedEntity;
							if (target instanceof EntityPlayer && Setting.showPlayerInformation) {
								EntityPlayer player = (EntityPlayer) target;
								renderPlayer.render(player, this);
							}
							if (target instanceof EntityLiving) {
								EntityLiving entity = (EntityLiving) target;
								renderEntity.render(entity, this);
							} // Item
							if (target instanceof EntityItem && Setting.showDropInformation) {
								EntityItem item = (EntityItem) target;
								ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
								FontRenderer fontRender = mc.fontRenderer;
								width = res.getScaledWidth();
								height = res.getScaledHeight();
								mc.entityRenderer.setupOverlayRendering();
								if (Setting.dropStyle == 0) {
									int color = 0xFFFFFF;
									String text = (int) item.getEntityItem().stackSize + "x " + item.getEntityItem().getDisplayName();
									NBTTagList enchs = item.getEntityItem().getEnchantmentTagList();
									if (enchs != null && Setting.showEnchantments) {
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
									if (Setting.showDurability) {
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
									if (damage > 0 && maxDamage > 0 && maxDamage - damage > 0) text = String.format(EnumChatFormatting.BOLD + "" + EnumChatFormatting.GOLD + "%s:" + EnumChatFormatting.RESET + " %d/%d", it.getDisplayName(),
											maxDamage - damage, maxDamage);
									else text = EnumChatFormatting.BOLD + "" + EnumChatFormatting.GOLD + it.getDisplayName() + EnumChatFormatting.RESET;
									list.add(StatCollector.translateToLocal("smartcursor.item.count") + it.stackSize);
									// Work with modules
									for (IDropProcessor module : Modules.dropModules)
										if (Modules.isActiveModule(module.getClass().getCanonicalName())) module.process(list, it);
									// ///////////////
									int maxW = 0;
									maxW = Math.max(maxW, fontRender.getStringWidth(text) + 16);
									for (int i = 1; i < list.size(); i++)
										maxW = Math.max(maxW, fontRender.getStringWidth(list.get(i)) + 8);
									if (Setting.showTooltipInRightCorner) x = width - maxW;
									RenderHelper.drawRect(x - 5, 0, x + maxW + 1, 8 + fontRender.FONT_HEIGHT * list.size() + 1 + 8, 0x555555, Setting.transparent);
									RenderHelper.drawRect(x - 4, 0, x + maxW, 8 + fontRender.FONT_HEIGHT * list.size() + 8, 0x010121, Setting.transparent);
									renderItem(it, 0, x, y, 1);
									for (int i = 1; i < list.size(); i++)
										fontRender.drawStringWithShadow(list.get(i), x, y + 8 + fontRender.FONT_HEIGHT * i, color);
									fontRender.drawStringWithShadow(text, x + 20, y + 4, color);
								}
							}
							// XPOrb
							if (target instanceof EntityXPOrb && Setting.showXPOrb) {
								EntityXPOrb xp = (EntityXPOrb) target;
								ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
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
				}
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.err.println(e);
			e.printStackTrace(System.out);
		}
	}

	public void renderItem(ItemStack itemstack, int slot, int x, int y, float partialTick) {
		if (itemstack != null) {
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), itemstack, x, y);
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	// /////////////////////////////// Rendering
	public void invertRender() {
		Setting.isEnabled = !Setting.isEnabled;
	}

	// /////////////////////////////
	public String getStyleName() {
		if (Setting.blockDamageStyle == 0) return StatCollector.translateToLocal("smartcursor.style.percents");
		if (Setting.blockDamageStyle == 1) return StatCollector.translateToLocal("smartcursor.style.progressBar");
		if (Setting.blockDamageStyle == 2) return "OFF";
		return "";
	}

	public void setNextStyle() {
		Setting.blockDamageStyle++;
		Setting.blockDamageStyle %= 3;
	}

	// /////////////////////////// Mob Indicator
	public String getMobStyleName() {
		if (Setting.mobStyle == 0) return StatCollector.translateToLocal("smartcursor.style.numeric");
		if (Setting.mobStyle == 1) return StatCollector.translateToLocal("smartcursor.style.progressBar");
		if (Setting.mobStyle == 2) return StatCollector.translateToLocal("smartcursor.style.icons");
		if (Setting.mobStyle == 3) return "OFF";
		return "";
	}

	public void setMobNextStyle() {
		Setting.mobStyle++;
		Setting.mobStyle %= 4;
	}

	// ////////////////////////// Drop info
	public void invertDropInfo() {
		Setting.showDropInformation = !Setting.showDropInformation;
	}

	public void invertEnchInfo() {
		Setting.showEnchantments = !Setting.showEnchantments;
	}

	public void invertDurInfo() {
		Setting.showDurability = !Setting.showDurability;
	}

	public String getDropStyleName() {
		if (Setting.dropStyle == 0) return StatCollector.translateToLocal("smartcursor.pos.center");
		if (Setting.dropStyle == 1) return StatCollector.translateToLocal("smartcursor.pos.inCorner");
		return "";
	}

	public void setDropNextStyle() {
		Setting.dropStyle++;
		Setting.dropStyle %= 2;
	}

	public String getPlayerStyleName() {
		if (Setting.playerStyle == 0) return StatCollector.translateToLocal("smartcursor.pos.inCorner");
		if (Setting.playerStyle == 1) return StatCollector.translateToLocal("smartcursor.style.numeric");
		if (Setting.playerStyle == 2) return StatCollector.translateToLocal("smartcursor.style.progressBar");
		if (Setting.playerStyle == 3) return StatCollector.translateToLocal("smartcursor.style.icons");
		return "";
	}

	public void setPlayerNextStyle() {
		Setting.playerStyle++;
		Setting.playerStyle %= 4;
	}

	// showXPOrb
	public void invertXPInfo() {
		Setting.showXPOrb = !Setting.showXPOrb;
	}

	public void invertMobInfo() {
		Setting.displayAdvInfoMob = !Setting.displayAdvInfoMob;
	}

	public void invertBlockInfo() {
		Setting.showBlockInformation = !Setting.showBlockInformation;
	}

	public void invertPlayerInfo() {
		Setting.showPlayerInformation = !Setting.showPlayerInformation;
	}

	public void invertTooltipPlaceInfo() {
		Setting.showTooltipInRightCorner = !Setting.showTooltipInRightCorner;
	}

	// ////////////////////////
	@SubscribeEvent
	public void addTooltipText(ItemTooltipEvent event) {
		ItemStack stack = event.itemStack;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            	if (event.showAdvancedItemTooltips)
            		{
        	  		event.toolTip.add("Name: " + GameData.getItemRegistry().getNameForObject(stack.getItem()));
        	  		event.toolTip.add("Unlocalized name:" + stack.getUnlocalizedName());
        	  		for (int id : OreDictionary.getOreIDs(stack)) event.toolTip.add(EnumChatFormatting.ITALIC + OreDictionary.getOreName(id));
            		}
        	}
		event.toolTip.add(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + ModIdentification.nameFromStack(stack) + EnumChatFormatting.RESET);
	}
}
