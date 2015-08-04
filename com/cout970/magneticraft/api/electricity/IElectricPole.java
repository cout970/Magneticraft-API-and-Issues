package com.cout970.magneticraft.api.electricity;

import java.util.List;

import com.cout970.magneticraft.api.util.VecDouble;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public interface IElectricPole{

	public List<IInterPoleWire> getConnectedConductors();
	
	public void iterate();
	
	public boolean canConnectWire(int tier, IElectricPole to);

	public TileEntity getParent();
	
	public IElectricConductor getConductor();
	
	public int getTier();
	
	public VecDouble[] getWireConnectors();
	
	public void onConnect(IInterPoleWire wire);
	
	public void onDisconnect(IInterPoleWire connection);
	
	public void save(NBTTagCompound nbt);
	
	public void load(NBTTagCompound nbt);

	public void disconectAll();
}
