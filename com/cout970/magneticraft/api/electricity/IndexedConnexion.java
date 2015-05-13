package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

public class IndexedConnexion {
	
	public VecInt con;
	public IElectricConductor cond;
	public ICompatibilityInterface inter;
	public int side;
	
	public IndexedConnexion(VecInt c,ICompatibilityInterface e, int side) {
		con = c;
		inter = e;
		this.side = side;
	}

	public IndexedConnexion(VecInt c, IElectricConductor e, int side) {
		con = c;
		cond = e;
		this.side = side;
	}
}
