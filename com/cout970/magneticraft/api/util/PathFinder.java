package com.cout970.magneticraft.api.util;

import java.util.HashSet;
import java.util.LinkedList;

public abstract class PathFinder {

	public LinkedList<VecInt> scanPosition;
	public HashSet<VecInt> scanMap;
	
	public void init(){
		scanPosition = new LinkedList<VecInt>();
		scanMap = new HashSet<VecInt>();
	}
	
	public void addBlock(VecInt v){
		if(!scanMap.contains(v)){
			scanPosition.addLast(v);
			scanMap.add(v);
		}
	}
	
	public void addNeigBlocks(VecInt pos){
		for(MgDirection d : MgDirection.values()){
			addBlock(d.toVecInt().add(pos));
		}
	}
	
	public abstract boolean step(VecInt coord);
	
	public boolean iterate(){
		if(scanPosition.size() == 0){
			return false;
		}else{
			VecInt vec = scanPosition.removeFirst();
			return step(vec);
		}
	}
}
