package com.asaskevich.smartcursor.modules.player;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IPlayerProcessor;

public class PlayerEquipmentModule
		implements IPlayerProcessor {
	@Override
	public void process(List<String> list, EntityPlayer player) {
		ItemStack[] items = player.getLastActiveItems();
		boolean h = player.getHeldItem() != null;
		for (ItemStack item : items)
			if (item != null) h = true;
		if (h) {
			list.add(StatCollector.translateToLocal("smartcursor.player.equipment"));
			list.add(" - " + player.getHeldItem().getDisplayName() + (player.getHeldItem().isItemEnchanted() ? StatCollector.translateToLocal("smartcursor.player.ench") : ""));
			for (ItemStack item : items)
				if (item != null && item != player.getHeldItem()) list.add(" - " + item.getDisplayName() + (item.isItemEnchanted() ? StatCollector.translateToLocal("smartcursor.player.ench") : ""));
		}
	}

	@Override
	public String getModuleName() {
		return "Equipment of Player";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
