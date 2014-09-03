package com.asaskevich.smartcursor.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomGuiOptionSlider extends GuiButton {
	private float currentValue;
	public boolean someThing;
	private final float minValue;
	private final float maxValue;
	public float value = 0F;
	private String label;
	private GuiAdvancedSettings gui;

	public CustomGuiOptionSlider(int id, int posX, int posY) {
		this(id, posX, posY, 0.0F, 1.0F);
	}

	public CustomGuiOptionSlider(int id, int posX, int posY, float minValue, float maxValue) {
		super(id, posX, posY, 150, 20, "");
		this.currentValue = 1.0F;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.displayString = "";
	}

	public CustomGuiOptionSlider(int id, int posX, int posY, int sizeX, int sizeY, String text, float minValue, float maxValue, float step,
			float initValue, GuiAdvancedSettings gui) {
		super(id, posX, posY, sizeX, sizeY, "");
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.label = text;
		this.value = initValue;
		this.currentValue = (this.value - this.minValue) / (this.maxValue - this.minValue);
		this.displayString = text + ": " + (int) this.value;
		this.someThing = false;
		this.mouseDragged(Minecraft.getMinecraft(), posX + (int) (sizeX * this.currentValue), posY);
		this.gui = gui;
	}

	public int getHoverState(boolean p_146114_1_) {
		return 0;
	}

	protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
		if (this.someThing) {
			this.currentValue = (float) (p_146119_2_ - (this.xPosition + 4)) / (float) (this.width - 8);
			if (this.currentValue < 0.0F) {
				this.currentValue = 0.0F;
			}
			if (this.currentValue > 1.0F) {
				this.currentValue = 1.0F;
			}
			value = (this.maxValue - this.minValue) * (this.currentValue) + this.minValue;
			this.displayString = this.label + ": " + (int) this.value;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(this.xPosition + (int) (this.currentValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
		this.drawTexturedModalRect(this.xPosition + (int) (this.currentValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4,
				20);
	}

	public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
		if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
			this.currentValue = (float) (p_146116_2_ - (this.xPosition + 4)) / (float) (this.width - 8);
			if (this.currentValue < 0.0F) {
				this.currentValue = 0.0F;
			}
			if (this.currentValue > 1.0F) {
				this.currentValue = 1.0F;
			}
			value = (this.maxValue - this.minValue) * (this.currentValue) + this.minValue;
			this.displayString = this.label + ": " + (int) this.value;
			this.someThing = true;
			return true;
		} else {
			return false;
		}
	}

	public void mouseReleased(int x, int y) {
		this.someThing = false;
		gui.updateSettings(this);
	}
}