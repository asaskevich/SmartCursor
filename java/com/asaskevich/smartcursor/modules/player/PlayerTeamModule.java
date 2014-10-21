package com.asaskevich.smartcursor.modules.player;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import com.asaskevich.smartcursor.api.IPlayerProcessor;

public class PlayerTeamModule
		implements IPlayerProcessor {
	@Override
	public void process(List<String> list, EntityPlayer player) {
		if (player.getTeam() != null) list.add(StatCollector.translateToLocal("smartcursor.player.team") + player.getTeam().getRegisteredName());
	}
}
