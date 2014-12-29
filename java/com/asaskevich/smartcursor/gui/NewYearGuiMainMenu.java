package com.asaskevich.smartcursor.gui;

import java.util.LinkedList;
import net.minecraft.client.gui.GuiMainMenu;

public class NewYearGuiMainMenu
		extends GuiMainMenu {
	LinkedList<Snow>	snow	= new LinkedList<Snow>();
	public int posX, color;

	@Override
	public void initGui() {
		super.initGui();
		this.snow.clear();
		this.posX = -100;
		this.color = (int) (Integer.MAX_VALUE / 100000 * Math.random()) + 100000;
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		for (int i = 0; i < (1000 - snow.size()) * Math.random(); i++)
			snow.add(new Snow(width));
		for (int i = 0; i < snow.size(); i++) {
			Snow s = snow.get(i);
			s.moveDraw(height);
			this.drawString(this.fontRendererObj, "*", s.x, s.y, -1);
		}
		for (int i = snow.size() - 1; i >= 0; i--)
			if (snow.get(i).lifeTime > 500 || Math.random() < 0.015) snow.remove(i);
		this.drawString(this.fontRendererObj, "Happy New Year!", posX, 10, color);
		posX ++;
		if (posX > width + 5) {
			this.posX = -40;
			this.color = (int) (Integer.MAX_VALUE / 100000 * Math.random());			
		}
	}
}

class Snow {
	public int	x, y, lifeTime;

	public Snow(int w) {
		this.x = (int) (Math.random() * w);
		this.y = 0;
		this.lifeTime = 0;
	}

	public void moveDraw(int h) {
		this.y += 1;
		this.y = Math.min(this.y, h - 10);
		this.lifeTime++;
	}
}