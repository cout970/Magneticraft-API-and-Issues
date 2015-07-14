package com.cout970.magneticraft.api.electricity.compact;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IndexedConnection;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

public class EU_EnergyInterfaceSink implements IEnergyInterface{

	private IEnergySink tile;
	private MgDirection dir;
	
	public EU_EnergyInterfaceSink(IEnergySink t, MgDirection dir) {
		tile = t;
		this.dir = dir;
	}
	
	@Override
	public double applyEnergy(double watts) {
		return watts - EnergyConversor.EUtoW(tile.injectEnergy(dir.toForgeDir(), EnergyConversor.WtoEU(watts), 512));
	}

	@Override
	public double getCapacity() {
		return -1;
	}

	@Override
	public double getEnergyStored() {
		return -1;
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.EUtoW(512);
	}

	@Override
	public boolean canConnect(VecInt f) {
		return true;
	}

	@Override
	public TileEntity getParent() {
		return null;
	}

	@Override
	public boolean canAcceptEnergy(IndexedConnection f) {
		return true;
	}

}
