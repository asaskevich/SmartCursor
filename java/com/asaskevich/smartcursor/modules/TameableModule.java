package com.asaskevich.smartcursor.modules;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.EntityModule;

public class TameableModule
		extends EntityModule {
	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof EntityTameable) {
			EntityTameable tame = (EntityTameable) entity;
			if (tame.isTamed()) {
				if (tame.getOwner() != null) list.add(StatCollector.translateToLocal("smartcursor.mob.tamedBy") + tame.getOwner().getCommandSenderName());
				else list.add(StatCollector.translateToLocal("smartcursor.mob.tamed"));
			} else list.add(StatCollector.translateToLocal("smartcursor.mob.notTamed"));
			if (tame.isSitting()) list.add(StatCollector.translateToLocal("smartcursor.mob.isSitting"));
		}
	}
}
