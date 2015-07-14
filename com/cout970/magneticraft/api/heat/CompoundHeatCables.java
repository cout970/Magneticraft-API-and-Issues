package com.cout970.magneticraft.api.heat;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.cout970.magneticraft.api.electricity.IElectricConductor;

/**
 * 
 * @author Cout970
 *
 */
public class CompoundHeatCables {

	private LinkedList<IHeatConductor> conds = new LinkedList<IHeatConductor>();

	public CompoundHeatCables(IHeatConductor c) {
		conds.add(c);
	}	
	
	public CompoundHeatCables(Collection<IHeatConductor> c) {
		conds.addAll(c);
	}
	
	public IHeatConductor getCond(int index){
		if(index >= conds.size())return null;
		return conds.get(index);
	}
	
	public int count(){
		return conds.size();
	}

	public List<IHeatConductor> list() {
		return conds;
	}

	public void add(IHeatConductor c) {
		conds.add(c);
	}
}
