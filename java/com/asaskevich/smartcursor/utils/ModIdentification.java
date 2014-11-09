package com.asaskevich.smartcursor.utils;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;

public class ModIdentification {


	public static String nameFromStack(ItemStack stack) {
		try {
			ModContainer mod = GameData.findModOwner(GameData.itemRegistry.getNameForObject(stack.getItem()));
			String modname = mod == null ? "Minecraft" : mod.getName();
			return modname;
		} catch (NullPointerException e) {
			return "";
		}
	}

}
