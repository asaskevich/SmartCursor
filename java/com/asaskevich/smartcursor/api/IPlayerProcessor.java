package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerProcessor extends IModule{
	void process(List<String> list, EntityPlayer player);
}
