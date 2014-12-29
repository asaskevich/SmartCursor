package com.asaskevich.smartcursor.modules.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.utils.ModIdentification;

public class BlockModIdentificatorModule
		implements IBlockProcessor {
	@Override
	public String getModuleName() {
		return "Mod Identification for Blocks";
	}

	@Override
	public String getAuthor() {
		return "modmuss50";
	}

	@Override
	public void process(List<String> list, Block block, int meta, World theWorld, int blockX, int blockY, int blockZ) {
		list.add(ModIdentification.nameFromStack(new ItemStack(block)));
	}
}
