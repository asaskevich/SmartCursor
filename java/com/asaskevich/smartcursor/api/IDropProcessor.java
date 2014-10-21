package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IDropProcessor {
	void process(List<String> list, ItemStack stack);
}