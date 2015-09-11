package com.cout970.magneticraft.api.util;

public class VectorOffset {

	private VecInt coords;
	private VecInt direction;
	
	public VectorOffset(VecInt coords, VecInt direction){
		this.coords = coords;
		this.direction = direction;
	}
	
	public VecInt getCoords(){
		return coords.copy();
	}
	
	public VecInt getDirection(){
		return direction.copy();
	}
}
