package com.asaskevich.smartcursor.modules.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import com.asaskevich.smartcursor.api.IBlockProcessor;

public class BlockChestModule
		implements IBlockProcessor {
	@Override
	public String getModuleName() {
		return "Display chest information";
	}

	@Override
	public String getAuthor() {
		return "asaskevich";
	}

	@Override
	public void process(List<String> list, Block block, int meta, World theWorld, int blockX, int blockY, int blockZ) {
		if (block.hasTileEntity(meta)) {
			TileEntity tileEntity = theWorld.getTileEntity(blockX, blockY, blockZ);
			if (tileEntity instanceof IInventory) {
				IInventory inv = (IInventory) tileEntity;
				String name = inv.getInventoryName();
				int size = inv.getSizeInventory();
				list.add(name + ": " + StatCollector.translateToLocal("smartcursor.block.inventorySize") + size);
			}
		}
	}
}
