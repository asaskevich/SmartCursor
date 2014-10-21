package com.asaskevich.smartcursor.modules.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IEntityProcessor;
import com.asaskevich.smartcursor.utils.Utils;

public class EntityHorseModule
		implements IEntityProcessor {
	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof EntityHorse) {
			EntityHorse horse = (EntityHorse) entity;
			list.add(StatCollector.translateToLocal("smartcursor.mob.jumpStrength") + String.format("%.3f", Utils.round(horse.getHorseJumpStrength(), 4)));
			list.add(StatCollector.translateToLocal("smartcursor.mob.horseSpeed") + String.format("%.3f", Utils.round(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue(), 4)));
			if (horse.isTame()) list.add(StatCollector.translateToLocal("smartcursor.mob.tamed"));
			else list.add(StatCollector.translateToLocal("smartcursor.mob.notTamed"));
		}
	}
}
