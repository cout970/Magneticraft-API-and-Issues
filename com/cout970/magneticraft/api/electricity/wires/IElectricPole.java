package com.cout970.magneticraft.api.electricity.wires;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.VecDouble;

/**
 * 
 * @author Cout970
 *
 */
public interface IElectricPole{

	public List<WireConnection> getConnectedConductors();
	
	public void iterate();
	
	public boolean canConnectWire(int tier, IElectricPole to);

	public TileEntity getParent();
	
	public IElectricConductor getConductor();
	
	public int getTier();
	
	public VecDouble[] getWireConnector();
	
	public void onConnect(WireConnection wire);
	
	public void onDisconnect(WireConnection connection);
	
	public void save(NBTTagCompound nbt);
	
	public void load(NBTTagCompound nbt);

	public void disconectAll();
}
