package com.asaskevich.smartcursor.modules.player;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IPlayerProcessor;

public class PlayerScoreModule
		implements IPlayerProcessor {
	@Override
	public void process(List<String> list, EntityPlayer player) {
		list.add(StatCollector.translateToLocal("smartcursor.player.score") + player.getScore());
	}
	

	@Override
	public String getModuleName() {
		return "Score of player";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
