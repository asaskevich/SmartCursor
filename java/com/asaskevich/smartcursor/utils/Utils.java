package com.asaskevich.smartcursor.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Utils {
	public static double round(double value, int places) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static NBTTagCompound convertEntityToNbt(Entity entity) {
		NBTTagCompound tag = new NBTTagCompound();
		entity.writeToNBT(tag);
		return tag;
	}

	public static void getPoisons(Entity entity) {
		try {
			NBTTagCompound tag = convertEntityToNbt(entity);
			NBTTagList list = (NBTTagList) tag.getTag("ActiveEffects");
			System.out.println("Tag count: " + list.tagCount());
		} catch (Exception e) {}
	}
}
