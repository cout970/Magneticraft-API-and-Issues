package com.cout970.magneticraft.api.electricity.compact;

import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.EnumAcces;
import com.cout970.magneticraft.api.electricity.ICompatibilityInterface;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;

public class RFenergyInterface implements ICompatibilityInterface{

	private IEnergyHandler tile;
	private MgDirection dir;
	
	public RFenergyInterface(IEnergyHandler g, MgDirection dir){
		tile = g;
		this.dir = dir;
	}
	
	@Override
	public double applyWatts(double watts) {		
		return EnergyConversor.RFtoW(tile.receiveEnergy(dir.getForgeDir(), (int) EnergyConversor.WtoRF(watts), false));
	}

	@Override
	public double drainWatts(double watts) {
		return EnergyConversor.RFtoW(tile.extractEnergy(dir.getForgeDir(), (int) EnergyConversor.WtoRF(watts), false));
	}

	@Override
	public double getCapacity() {
		return EnergyConversor.RFtoKW(tile.getMaxEnergyStored(dir.getForgeDir()));
	}

	@Override
	public double getEnergyStored() {
		return EnergyConversor.RFtoKW(tile.getEnergyStored(dir.getForgeDir()));
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.RFtoKW(80);
	}

	@Override
	public EnumAcces getBehavior() {
		return EnumAcces.BOTH;
	}

	@Override
	public TileEntity getParent() {
		return (TileEntity) tile;
	}

}
