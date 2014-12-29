package com.asaskevich.smartcursor.modules.entity;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.IShearable;
import com.asaskevich.smartcursor.api.IEntityProcessor;

public class EntityShearableModule implements IEntityProcessor{

	@Override
	public String getModuleName() {
		return "Is entity shearable";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}

	@Override
	public void process(List<String> list, Entity entity) {
		if (entity instanceof IShearable)
			list.add(StatCollector.translateToLocal("smartcursor.block.shearable"));
		
	}}
