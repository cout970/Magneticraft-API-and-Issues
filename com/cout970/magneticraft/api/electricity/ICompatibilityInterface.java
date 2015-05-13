package com.cout970.magneticraft.api.electricity;

import net.minecraft.tileentity.TileEntity;

public interface ICompatibilityInterface{

	/**
	 * @param wats
	 * @return energy accepted from the other energy system
	 */
	public double applyWatts(double watts);
	
	/**
	 * @param watts
	 * @return energy extracted from the other energy system
	 */
	public double drainWatts(double watts);
	
	public double getCapacity();
	
	public double getEnergyStored();
	
	public double getMaxFlow();

	public EnumAcces getBehavior();
	
	public TileEntity getParent();
	
}
