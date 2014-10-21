package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.entity.Entity;

public interface IEntityProcessor {
	public boolean	isEnabled	= true;

	void process(List<String> list, Entity entity);
}
