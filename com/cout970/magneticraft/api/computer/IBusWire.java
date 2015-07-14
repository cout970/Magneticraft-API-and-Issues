package com.cout970.magneticraft.api.computer;

import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.util.VecInt;

public interface IBusWire {
	
	/**
	 * @return possibles connections that the IBusWire can have.
	 */
	public VecInt[] getValidConnections();
	
	/**
	 * @return can conect with other conductor, used for microblocks with covers and for non full blocks like solar panels
	 */
	public boolean isAbleToConnect(IBusWire c, VecInt enumFacing);
	
	/**
	 * @return the type of cable or wire
	 */
	public ConnectionClass getConnectionClass(VecInt v);
}
