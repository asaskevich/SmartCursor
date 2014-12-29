package com.asaskevich.smartcursor.modules.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import com.asaskevich.smartcursor.api.IBlockProcessor;

public class BlockShearableModule
		implements IBlockProcessor {
	@Override
	public String getModuleName() {
		return "Is block shearable";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}

	@Override
	public void process(List<String> list, Block block, int meta, World theWorld, int blockX, int blockY, int blockZ) {
		if (block instanceof IShearable)
			list.add(StatCollector.translateToLocal("smartcursor.block.shearable"));
	}
}
