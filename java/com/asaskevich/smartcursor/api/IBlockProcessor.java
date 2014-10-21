package com.asaskevich.smartcursor.api;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IBlockProcessor {
	void process(List<String> list, Block block, int meta, World theWorld, int blockX, int blockY, int blockZ);
}
