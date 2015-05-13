package com.cout970.magneticraft.api.electricity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CableCompound{
	
	private LinkedList<IElectricConductor> conds = new LinkedList<IElectricConductor>();

	public CableCompound(IElectricConductor c) {
		conds.add(c);
	}	
	
	public CableCompound(Collection<IElectricConductor> c) {
		conds.addAll(c);
	}
	
	public IElectricConductor getCond(int index){
		return conds.get(index);
	}
	
	public int count(){
		return conds.size();
	}

	public List<IElectricConductor> list() {
		return conds;
	}

	public void add(IElectricConductor c) {
		conds.add(c);
	}
}
