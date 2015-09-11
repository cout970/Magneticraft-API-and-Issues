package com.cout970.magneticraft.api.computer;

import net.minecraft.tileentity.TileEntity;


public interface IPeripheral {

    public int getAddress();

    public void setAddress(int address);

    public boolean isActive();

    public String getName();

    public int readByte(int pointer);

    public void writeByte(int pointer, int data);

    public TileEntity getParent();
}
