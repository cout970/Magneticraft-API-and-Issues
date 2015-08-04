package com.cout970.magneticraft.api.kinetic;

import java.util.HashSet;
import java.util.LinkedList;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class KineticPathFinder{

	public LinkedList<IKineticConductor> conds;
	public World w;
	public LinkedList<ExtendedVec> scanPosition;
	public HashSet<VecInt> scanMap;
	
	public KineticPathFinder(World w, IKineticConductor cond){
		this.w = w;
		conds = new LinkedList<IKineticConductor>();
		scanPosition = new LinkedList<ExtendedVec>();
		scanMap = new HashSet<VecInt>();
		conds.addLast(cond);
		scanMap.add(new VecInt(cond.getParent()));
		for(MgDirection dir : cond.getValidSides()){
			addBlock(new ExtendedVec(new VecInt(cond.getParent()).add(dir.toVecInt()), dir));
		}
	}
	
	public void addBlock(ExtendedVec v){
		if(!scanMap.contains(v.vec)){
			scanPosition.addLast(v);
			scanMap.add(v.vec);
		}
	}
	
	public boolean iterate(){
		if(scanPosition.size() == 0){
			return false;
		}else{
			ExtendedVec vec = scanPosition.removeFirst();
			return step(vec);
		}
	}
	
	public boolean step(ExtendedVec vec) {
		TileEntity tile = MgUtils.getTileEntity(w,vec.vec);
		if(tile instanceof IKineticTile){
			IKineticTile k = (IKineticTile) tile;
			IKineticConductor cond = k.getKineticConductor(vec.dir);
			if(cond != null){
				conds.add(cond);
				for(MgDirection d : k.getValidSides()){
					addBlock(new ExtendedVec(vec.vec.copy().add(d.toVecInt()), d.opposite()));
				}
			}
		}
		return true;
	}
	
	public class ExtendedVec{
		
		public VecInt vec;
		public MgDirection dir;
		
		public ExtendedVec(VecInt vec, MgDirection d){
			this.vec = vec;
			dir = d;
		}
	}
}
