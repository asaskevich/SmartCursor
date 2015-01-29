package com.asaskevich.smartcursor.modules.drop;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import com.asaskevich.smartcursor.api.IDropProcessor;
import com.asaskevich.smartcursor.utils.ModIdentification;

public class ItemModIdentificationModule
		implements IDropProcessor {
	@Override
	public String getModuleName() {
		return "Mod Identification for Blocks";
	}

	@Override
	public String getAuthor() {
		return "modmuss50";
	}

	@Override
	public void process(List<String> list, ItemStack stack) {
		list.add(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + ModIdentification.nameFromStack(stack) + EnumChatFormatting.RESET);
	}
}
