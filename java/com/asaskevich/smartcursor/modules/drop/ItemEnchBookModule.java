package com.asaskevich.smartcursor.modules.drop;

import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IDropProcessor;

public class ItemEnchBookModule
		implements IDropProcessor {
	@Override
	public void process(List<String> list, ItemStack stack) {
		if (stack.getItem() instanceof ItemEnchantedBook) {
			ItemEnchantedBook book = (ItemEnchantedBook) stack.getItem();
			list.add(StatCollector.translateToLocal("smartcursor.item.enchBook"));
			NBTTagList nbttaglist = book.func_92110_g(stack);
			if (nbttaglist != null) {
				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
					short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					if (Enchantment.enchantmentsList[short1] != null) {
						list.add(" - " + Enchantment.enchantmentsList[short1].getTranslatedName(short2));
					}
				}
			}
		}
	}
	

	@Override
	public String getModuleName() {
		return "Display all enchantments for enchanted book";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
