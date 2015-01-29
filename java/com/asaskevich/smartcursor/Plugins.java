package com.asaskevich.smartcursor;

import com.asaskevich.smartcursor.api.ModuleConnector;
import com.asaskevich.smartcursor.modules.block.BlockChestModule;
import com.asaskevich.smartcursor.modules.block.BlockGrowableModule;
import com.asaskevich.smartcursor.modules.block.BlockHarvestModule;
import com.asaskevich.smartcursor.modules.block.BlockModIdentificatorModule;
import com.asaskevich.smartcursor.modules.block.BlockShearableModule;
import com.asaskevich.smartcursor.modules.drop.ItemEnchBookModule;
import com.asaskevich.smartcursor.modules.drop.ItemEnchModule;
import com.asaskevich.smartcursor.modules.drop.ItemFoodModule;
import com.asaskevich.smartcursor.modules.drop.ItemModIdentificationModule;
import com.asaskevich.smartcursor.modules.drop.ItemUtilsModule;
import com.asaskevich.smartcursor.modules.entity.EntityAgeableModule;
import com.asaskevich.smartcursor.modules.entity.EntityEquipmentModule;
import com.asaskevich.smartcursor.modules.entity.EntityHorseModule;
import com.asaskevich.smartcursor.modules.entity.EntityShearableModule;
import com.asaskevich.smartcursor.modules.entity.EntityTameableModule;
import com.asaskevich.smartcursor.modules.entity.EntityUtilsModule;
import com.asaskevich.smartcursor.modules.entity.EntityVillagerModule;
import com.asaskevich.smartcursor.modules.player.PlayerEquipmentModule;
import com.asaskevich.smartcursor.modules.player.PlayerScoreModule;
import com.asaskevich.smartcursor.modules.player.PlayerTeamModule;

public class Plugins {
	public static void init() {
		ModuleConnector.connectModule(new BlockHarvestModule());
		ModuleConnector.connectModule(new ItemEnchBookModule());
		ModuleConnector.connectModule(new ItemEnchModule());
		ModuleConnector.connectModule(new ItemFoodModule());
		ModuleConnector.connectModule(new ItemUtilsModule());
		ModuleConnector.connectModule(new PlayerEquipmentModule());
		ModuleConnector.connectModule(new PlayerScoreModule());
		ModuleConnector.connectModule(new PlayerTeamModule());
		ModuleConnector.connectModule(new EntityAgeableModule());
		ModuleConnector.connectModule(new EntityEquipmentModule());
		ModuleConnector.connectModule(new EntityHorseModule());
		ModuleConnector.connectModule(new EntityTameableModule());
		ModuleConnector.connectModule(new EntityUtilsModule());
		ModuleConnector.connectModule(new EntityVillagerModule());
		ModuleConnector.connectModule(new BlockChestModule());
		ModuleConnector.connectModule(new BlockGrowableModule());
		ModuleConnector.connectModule(new BlockShearableModule());
		ModuleConnector.connectModule(new EntityShearableModule());
		ModuleConnector.connectModule(new BlockModIdentificatorModule());
		ModuleConnector.connectModule(new ItemModIdentificationModule());
	}
}
