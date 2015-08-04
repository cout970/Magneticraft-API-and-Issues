package com.cout970.magneticraft.api.heat;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * 
 * @author Cout970
 *
 */
public interface IHeatTile {

	/**
	 * return de conductor in the block, if the VecInt is VecInt.NULL_VECTOR the method must return the conductor always
	 * @param c
	 * @return
	 */
	public IHeatConductor[] getHeatCond(VecInt c);

}
