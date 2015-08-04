package com.cout970.magneticraft.api.computer;

import java.io.File;

import net.minecraft.item.ItemStack;
/**
 * 
 * @author Cout970
 *
 */
public interface IStorageDevice {

	/**
	 * If is null, an IOException was occurred
	 * @param i device ItemStack
	 * @return
	 */
	public File getAsociateFile(ItemStack i);
	
	/**
	 * Can be null
	 * @param i device ItemStack
	 * @return the disk label
	 */
	public String getDiskLabel(ItemStack i);
	public void setDiskLabel(ItemStack i, String label);
	
	/**
	 * Can be null, but after call getAsociateFile, must be a valid number, Also It's a 16 digits number in hexadecimal
	 * @param i device ItemStack
	 * @return the unique serial number used to get the save file, "disk_"+serialNumber+".img"
	 */
	public String getSerialNumber(ItemStack i);
	
	/**
	 * @param i device ItemStack
	 * @return the number of sector of 128bytes in the disk
	 */
	public int getSize(ItemStack i);
	
	/**
	 * @param i device ItemStack
	 * @return the amount of game ticks to wait to read or write in the disk 
	 */
	public int getAccessTime(ItemStack i);
	
	public boolean isHardDrive(ItemStack i);
	public boolean isFloppyDrive(ItemStack i);
}
