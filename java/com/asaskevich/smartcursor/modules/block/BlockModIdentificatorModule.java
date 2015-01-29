package com.asaskevich.smartcursor.modules.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
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
		Item item = block.getItem(theWorld, blockX, blockY, blockZ);
		ItemStack stack = new ItemStack(block);
		if (item != null) stack = new ItemStack(block.getItem(theWorld, blockX, blockY, blockZ));
		list.add(EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + ModIdentification.nameFromStack(stack) + EnumChatFormatting.RESET);
	}
}
