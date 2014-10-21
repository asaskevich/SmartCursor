package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerProcessor {
	void process(List<String> list, EntityPlayer player);
}
