package com.asaskevich.smartcursor.modules.block;

import com.asaskevich.smartcursor.api.IBlockProcessor;
import com.asaskevich.smartcursor.utils.ModIdentification;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class BlockHarvestModule
		implements IBlockProcessor {
	@Override
	public void process(List<String> list, Block block, int meta, World w, int x, int y, int z) {
		if (block.canHarvestBlock(Minecraft.getMinecraft().thePlayer, meta)) list.add(StatCollector.translateToLocal("smartcursor.block.harvestBlock"));
		else list.add(StatCollector.translateToLocal("smartcursor.block.cantHarvestBlock"));
		list.add(ModIdentification.nameFromStack(new ItemStack(block)));
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
