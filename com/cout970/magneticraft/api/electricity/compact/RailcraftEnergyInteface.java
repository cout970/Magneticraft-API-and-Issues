package com.cout970.magneticraft.api.electricity.compact;

import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.electricity.EnumAcces;
import com.cout970.magneticraft.api.electricity.ICompatibilityInterface;
import com.cout970.magneticraft.api.util.EnergyConversor;

public class RailcraftEnergyInteface implements ICompatibilityInterface{

	public ChargeHandler c;
	public IElectricGrid grid;
	
	public RailcraftEnergyInteface(IElectricGrid g) {
		c = g.getChargeHandler();
		grid = g;
	}
	
	@Override
	public double applyWatts(double watts) {
		double energy = Math.min(c.getCapacity()-c.getCharge(), EnergyConversor.WtoEU(watts));
		c.addCharge(energy);
		return EnergyConversor.EUtoW(energy);
	}

	@Override
	public double drainWatts(double watts) {
		double r = Math.min(EnergyConversor.WtoEU(watts), c.getCharge());
		c.addCharge(-r);
		return EnergyConversor.EUtoW(r);
	}

	@Override
	public double getCapacity() {
		return EnergyConversor.EUtoKW(c.getCapacity());
	}

	@Override
	public double getEnergyStored() {
		return EnergyConversor.EUtoKW(c.getCharge());
	}

	@Override
	public double getMaxFlow() {
		return EnergyConversor.EUtoKW(32);
	}

	@Override
	public EnumAcces getBehavior() {
		return EnumAcces.BOTH;
	}

	@Override
	public TileEntity getParent() {
		return grid.getTile();
	}
}
