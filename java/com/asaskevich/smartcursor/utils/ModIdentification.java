package com.asaskevich.smartcursor.utils;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;

public class ModIdentification {


	@SuppressWarnings("deprecation")
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
