package com.asaskevich.smartcursor.modules.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IEntityProcessor;

public class EntityEquipmentModule
		implements IEntityProcessor {
	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof EntityCreature) {
			EntityCreature cre = (EntityCreature) entity;
			ItemStack[] items = cre.getLastActiveItems();
			boolean h = false;
			for (ItemStack item : items)
				if (item != null) h = true;
			if (h) {
				list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("smartcursor.player.equipment"));
				for (ItemStack item : items)
					if (item != null) list.add(" * " + item.getDisplayName() + EnumChatFormatting.GOLD + (item.isItemEnchanted() ? StatCollector.translateToLocal("smartcursor.player.ench") : ""));
			}
		}
	}

	@Override
	public String getModuleName() {
		return "Equipment of mob";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
