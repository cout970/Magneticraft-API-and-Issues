package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;

/**
 * This class should be implemented in an Item
 * @author Cout970
 *
 */
public interface IFloppyDisk extends IStorageDevice{

	/**
	 * @param i device ItemStack
	 * @return the amount of game ticks to wait to read or write in the disk 
	 */
	public int getAccessTime(ItemStack i);
}
