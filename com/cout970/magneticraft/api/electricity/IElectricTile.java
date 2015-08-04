package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * interface for TileEntities
 * @author Cout970
 *
 */
public interface IElectricTile {

	/**
	 * @args dir the origin point of the caller
	 * if the args are NULL_VECTOR this should always return the conductor if the block has one or more.
	 * the cablecompound can't be empty, it must be null or have at least one element
	 * @return the compound of Conductors in the block
	 */
	public IElectricConductor[] getConds(VecInt dir,int Vtier);
}
