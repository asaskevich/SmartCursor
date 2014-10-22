package com.asaskevich.smartcursor.modules.drop;

import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IDropProcessor;

public class ItemEnchModule
		implements IDropProcessor {
	@Override
	public void process(List<String> list, ItemStack stack) {
		if (stack.isItemEnchanted()) {
			list.add(StatCollector.translateToLocal("smartcursor.item.enchItem"));
			NBTTagList enchs = stack.getEnchantmentTagList();
			if (enchs != null) {
				for (int i = 0; i < enchs.tagCount(); i++) {
					NBTTagCompound tag = enchs.getCompoundTagAt(i);
					short id = tag.getShort("id");
					short lvl = tag.getShort("lvl");
					Enchantment e = Enchantment.enchantmentsList[id];
					String enStr = e.getTranslatedName(lvl);
					list.add(" - " + enStr);
				}
			}
		}
	}
	

	@Override
	public String getModuleName() {
		return "Enchantments for Item";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
