package com.asaskevich.smartcursor;

import com.asaskevich.smartcursor.api.Modules;
import com.asaskevich.smartcursor.modules.block.BlockHarvestModule;
import com.asaskevich.smartcursor.modules.drop.ItemEnchBookModule;
import com.asaskevich.smartcursor.modules.drop.ItemEnchModule;
import com.asaskevich.smartcursor.modules.drop.ItemFoodModule;
import com.asaskevich.smartcursor.modules.drop.ItemUtilsModule;
import com.asaskevich.smartcursor.modules.entity.EntityAgeableModule;
import com.asaskevich.smartcursor.modules.entity.EntityEquipmentModule;
import com.asaskevich.smartcursor.modules.entity.EntityHorseModule;
import com.asaskevich.smartcursor.modules.entity.EntityTameableModule;
import com.asaskevich.smartcursor.modules.entity.EntityUtilsModule;
import com.asaskevich.smartcursor.modules.entity.EntityVillagerModule;
import com.asaskevich.smartcursor.modules.player.PlayerEquipmentModule;
import com.asaskevich.smartcursor.modules.player.PlayerScoreModule;
import com.asaskevich.smartcursor.modules.player.PlayerTeamModule;

public class Plugins {
	public static void init() {
		Modules.registerModule(new BlockHarvestModule());
		Modules.registerModule(new ItemEnchBookModule());
		Modules.registerModule(new ItemEnchModule());
		Modules.registerModule(new ItemFoodModule());
		Modules.registerModule(new ItemUtilsModule());
		Modules.registerModule(new PlayerEquipmentModule());
		Modules.registerModule(new PlayerScoreModule());
		Modules.registerModule(new PlayerTeamModule());
		Modules.registerModule(new EntityAgeableModule());
		Modules.registerModule(new EntityEquipmentModule());
		Modules.registerModule(new EntityHorseModule());
		Modules.registerModule(new EntityTameableModule());
		Modules.registerModule(new EntityUtilsModule());
		Modules.registerModule(new EntityVillagerModule());
	}
}
