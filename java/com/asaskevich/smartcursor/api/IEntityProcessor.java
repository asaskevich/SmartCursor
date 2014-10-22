package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.entity.Entity;

public interface IEntityProcessor
		extends IModule {
	void process(List<String> list, Entity entity);
}
