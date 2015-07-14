package com.cout970.magneticraft.api.electricity.compact;

import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.EnumAcces;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IndexedConnection;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

/**
 * 
 * @author Cout970
 *
 */
public class RF_EnergyInterface implements IEnergyInterface{

	private IEnergyHandler tile;
	private MgDirection dir;
	
	public RF_EnergyInterface(IEnergyHandler g, MgDirection dir){
		tile = g;
		this.dir = dir;
	}
	
	@Override
	public double applyEnergy(double watts) {		
		return EnergyConversor.RFtoW(tile.receiveEnergy(dir.toForgeDir(), (int) EnergyConversor.WtoRF(watts), false));
	}

	@Override
	public double getCapacity() {
		return EnergyConversor.RFtoW(tile.getMaxEnergyStored(dir.toForgeDir()));
	}

	@Override
	public double getEnergyStored() {
		return EnergyConversor.RFtoW(tile.getEnergyStored(dir.toForgeDir()));
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.RFtoW(80);
	}

	@Override
	public TileEntity getParent() {
		return (TileEntity) tile;
	}

	@Override
	public boolean canConnect(VecInt f) {
		return tile.canConnectEnergy(dir.toForgeDir());
	}

	@Override
	public boolean canAcceptEnergy(IndexedConnection f) {
		return true;
	}

}
