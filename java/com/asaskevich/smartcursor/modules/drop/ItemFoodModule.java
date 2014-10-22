package com.asaskevich.smartcursor.modules.drop;

import java.util.List;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IDropProcessor;

public class ItemFoodModule
		implements IDropProcessor {
	@Override
	public void process(List<String> list, ItemStack stack) {
		if (stack.getItem() instanceof ItemFood) {
			ItemFood food = (ItemFood) stack.getItem();
			list.add(StatCollector.translateToLocal("smartcursor.item.healAmount") + food.func_150905_g(stack));
			if (food.isWolfsFavoriteMeat()) list.add(StatCollector.translateToLocal("smartcursor.item.wolfsMeat"));
		}
		if (stack.getItem().isPotionIngredient(stack)) list.add(StatCollector.translateToLocal("smartcursor.item.useInPotions"));
	}
	

	@Override
	public String getModuleName() {
		return "Expanded Info about Food";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
