package com.asaskevich.smartcursor.modules.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import com.asaskevich.smartcursor.api.IBlockProcessor;

public class BlockHarvestModule
		implements IBlockProcessor {
	@Override
	public void process(List<String> list, Block block, int meta, World w, int x, int y, int z) {
		if (block.canHarvestBlock(Minecraft.getMinecraft().thePlayer, meta)) list.add(StatCollector.translateToLocal("smartcursor.block.harvestBlock"));
		else list.add(StatCollector.translateToLocal("smartcursor.block.cantHarvestBlock"));		
	}

	@Override
	public String getModuleName() {
		return "Can I harvest block or not?";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}
}
