package com.asaskevich.smartcursor.modules.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IEntityProcessor;

public class EntityAgeableModule
		implements IEntityProcessor {
	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof EntityAgeable) {
			EntityAgeable age = (EntityAgeable) entity;
			if (age.getGrowingAge() < 0) list.add(StatCollector.translateToLocal("smartcursor.mob.child") + Math.abs(age.getGrowingAge() / 20) + StatCollector.translateToLocal("smartcursor.mob.seconds"));
		}
	}
}
