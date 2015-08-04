package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

public interface IIndexedConnection {

	public VecInt getOffset();
	public IElectricConductor getSource();
	public IElectricConductor getConductor();
	public IEnergyInterface getEnergyInterface();
	public int getIndex();
}
