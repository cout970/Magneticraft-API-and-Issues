package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * interface for TileEntities
 * @author Cout970
 *
 */
public interface IElectricTile {

	/**
	 * @args dir the origin point of the called
	 * if the args are NULL_VECTOR and tier -1 this should always return the conductor.
	 * the cablecompound can't be empty, it must be null or has at least one element
	 * @return the compound of Conductors in the block
	 */
	public CableCompound getConds(VecInt dir,int Vtier);
}
