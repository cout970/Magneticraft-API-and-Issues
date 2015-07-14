package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * 
 * @author Cout970
 *
 */
public class IndexedConnection {
	
	public VecInt vecDir;				//direction from the source to the cond or interface
	public IElectricConductor source;	//source conductor
	public IElectricConductor cond;
	public IEnergyInterface inter;
	public int index; 					//used by the conductor for current flow
	
	public IndexedConnection(IElectricConductor s, VecInt c, IEnergyInterface e, int side) {
		vecDir = c;
		inter = e;
		source = s;
		this.index = side;
	}

	public IndexedConnection(IElectricConductor s, VecInt c, IElectricConductor e, int side) {
		vecDir = c;
		cond = e;
		source = s;
		this.index = side;
	}
}
