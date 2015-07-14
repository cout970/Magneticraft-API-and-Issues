package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public interface IEnergyInterface{

	/**
	 * @param wats
	 * @return energy accepted from the other energy system
	 */
	public double applyEnergy(double watts);
	
	/**
	 * the capacity to store energy
	 * @return
	 */
	public double getCapacity();
	
	/**
	 * amount of energy stored
	 * @return
	 */
	public double getEnergyStored();
	
	/**
	 * max amount of energy per tick, should be in Watts
	 * @return
	 */
	public double getMaxFlow();

	/**
	 * 
	 * @param f
	 * @return
	 */
	public boolean canConnect(VecInt f);
	
	/**
	 * the tileEntity that has the block 
	 * @return
	 */
	public TileEntity getParent();

	public boolean canAcceptEnergy(IndexedConnection f);
	
}
