package com.asaskevich.smartcursor.modules.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IEntityProcessor;

public class EntityVillagerModule
		implements IEntityProcessor {
	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) entity;
			switch (villager.getProfession()) {
				case 0:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession_0"));
					break;
				case 1:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession_1"));
					break;
				case 2:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession_2"));
					break;
				case 3:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession_3"));
					break;
				case 4:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession_4"));
					break;
				default:
					list.add(StatCollector.translateToLocal("smartcursor.mob.profession"));
					break;
			}
			if (villager.isTrading()) list.add(StatCollector.translateToLocal("smartcursor.mob.trade"));
		}
	}
	

	@Override
	public String getModuleName() {
		return "Villager's profession";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
