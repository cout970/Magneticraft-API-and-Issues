package com.cout970.magneticraft.api.electricity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Cout970
 *
 */
public class CompoundElectricCables{
	
	private LinkedList<IElectricConductor> conds = new LinkedList<IElectricConductor>();

	public CompoundElectricCables(IElectricConductor c) {
		conds.add(c);
	}	
	
	public CompoundElectricCables(Collection<IElectricConductor> c) {
		conds.addAll(c);
	}
	
	public CompoundElectricCables(IElectricConductor a, IElectricConductor b) {
		conds.add(a);
		conds.add(b);
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
