package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.entity.Entity;

public abstract class EntityModule {
	abstract public void process(List<String> list, Entity entity);
}
