package com.asaskevich.smartcursor.modules.drop;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IDropProcessor;

public class ItemUtilsModule
		implements IDropProcessor {
	@Override
	public void process(List<String> list, ItemStack stack) {
		if (stack.isStackable())
			list.add(StatCollector.translateToLocal("smartcursor.item.stackable")
					+ (stack.getMaxStackSize() > 1 ? StatCollector.translateToLocal("smartcursor.item.in") + stack.getMaxStackSize() + StatCollector.translateToLocal("smartcursor.item.items") : ""));
		if (stack.isItemDamaged()) list.add(StatCollector.translateToLocal("smartcursor.item.isDamaged"));
		if (stack.isItemEnchantable()) list.add(StatCollector.translateToLocal("smartcursor.item.enchantable"));
		if (stack.getHasSubtypes()) list.add(StatCollector.translateToLocal("smartcursor.item.hasSubtypes"));
		if (stack.hasEffect()) list.add(StatCollector.translateToLocal("smartcursor.item.hasEffect"));
	}
}
