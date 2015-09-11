package com.cout970.magneticraft.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IConnectable {

    public TileEntity getParent();

    public void iterate();

    public VecInt[] getValidConnections();

    public boolean isAbleToConnect(IConnectable cond, VecInt dir);

    public ConnectionClass getConnectionClass(VecInt v);

    //save and load data
    public void save(NBTTagCompound nbt);

    public void load(NBTTagCompound nbt);
}
