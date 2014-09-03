package com.asaskevich.smartcursor;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderHelper {	
	
	public static void drawRect(int x, int y, int p_73734_2_, int p_73734_3_, int p_73734_4_, int transparent) {
		int j1;
		if (x < p_73734_2_) {
			j1 = x;
			x = p_73734_2_;
			p_73734_2_ = j1;
		}
		if (y < p_73734_3_) {
			j1 = y;
			y = p_73734_3_;
			p_73734_3_ = j1;
		}
		float f3 = (float) (transparent) / 255.0F;
		float f = (float) (p_73734_4_ >> 16 & 255) / 255.0F;
		float f1 = (float) (p_73734_4_ >> 8 & 255) / 255.0F;
		float f2 = (float) (p_73734_4_ & 255) / 255.0F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(f, f1, f2, f3);
		tessellator.startDrawingQuads();
		tessellator.addVertex((double) x, (double) p_73734_3_, 0.0D);
		tessellator.addVertex((double) p_73734_2_, (double) p_73734_3_, 0.0D);
		tessellator.addVertex((double) p_73734_2_, (double) y, 0.0D);
		tessellator.addVertex((double) x, (double) y, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
