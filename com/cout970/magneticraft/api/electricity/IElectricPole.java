package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecDouble;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * @author Cout970
 */
public interface IElectricPole {

    List<IInterPoleWire> getConnectedConductors();

    void iterate();

    boolean canConnectWire(int tier, IElectricPole to, boolean isManual);

    TileEntity getParent();

    IElectricConductor getConductor();

    int getTier();

    VecDouble[] getWireConnectors();

    void onConnect(IInterPoleWire wire);

    void onDisconnect(IInterPoleWire connection);

    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);

    void disconnectAll();
}
