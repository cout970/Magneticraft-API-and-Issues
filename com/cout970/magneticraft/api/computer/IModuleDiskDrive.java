package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This can be implemented by a TileEntity or used as a Field, but is only needed for @IComputer
 *
 * @author Cout970
 */
public interface IModuleDiskDrive extends IHardwareComponent {

    /**
     * called every tick by the tileEntity
     */
    public void iterate();

    /**
     * Can't be null
     *
     * @return a 128 byte buffer
     */
    public byte[] getRawBuffer();

    /**
     * @return current sector to read or write
     */
    public int getSector();

    /**
     * @param the sector to read or write
     */
    public void setSector(int sector);

    /**
     * read from the disk to the buffer using the current sector
     */
    public void readToBuffer();

    /**
     * write the buffer into the disk in the current sector
     */
    public void writeToFile();

    /**
     * @return the disk inside os the drive
     */
    public ItemStack getDisk();

    /**
     * @param i ItemStack with an IStorageDevice
     * @return true if the disk is acepted, false if already ocuped or invalid disk
     */
    public boolean insertDisk(ItemStack i);

    /**
     * This only load the buffer and the sector, not the item inside
     *
     * @param nbt
     */
    public void load(NBTTagCompound nbt);

    /**
     * This only save the buffer and the sector, not the item inside
     *
     * @param nbt
     */
    public void save(NBTTagCompound nbt);
}
