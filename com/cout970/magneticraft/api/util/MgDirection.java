package com.cout970.magneticraft.api.util;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Own Implementation of ForgeDirection to be able to update to 1.8 faster
 * @author Cout970
 *
 */
public enum MgDirection {
 
	DOWN	(0,-1,0),
	UP		(0,1,0),
	NORTH	(0,0,-1),
	SOUTH	(0,0,1),
	WEST	(-1,0,0),
	EAST	(1,0,0);
	
	public static final MgDirection[] VALID_DIRECTIONS = {DOWN,UP,NORTH,SOUTH,WEST,EAST};
	public static final MgDirection[] OPPOSITES = {UP,DOWN,SOUTH,NORTH,EAST,WEST};
	public static final int[][] rotation ={
			{ 0, 1, 5, 4, 2, 3 },
			{ 0, 1, 4, 5, 3, 2 },
			{ 5, 4, 2, 3, 0, 1 },
			{ 4, 5, 2, 3, 1, 0 },
			{ 2, 3, 1, 0, 4, 5 }, 
			{ 3, 2, 0, 1, 4, 5 },
			{ 0, 1, 2, 3, 4, 5 }};
	
	private final VecInt offsets;
	
	MgDirection(int x,int y,int z){
		offsets = new VecInt(x, y, z);
	}
	
	public int getOffsetX(){
		return offsets.getX();
	}
	
	public int getOffsetY(){
		return offsets.getY();
	}
	
	public int getOffsetZ(){
		return offsets.getZ();
	}
	
	public MgDirection opposite(){
		return OPPOSITES[ordinal()];
	}
	
	public static MgDirection getDirection(int i){
		return values()[i % VALID_DIRECTIONS.length];
	}

	public VecInt toVecInt() {
		return offsets.copy();
	}

	public ForgeDirection toForgeDir() {
		return ForgeDirection.getOrientation(ordinal());
	}
	
	//anti-clock
	public MgDirection step(MgDirection axix){
		return MgDirection.getDirection(rotation[axix.ordinal()][ordinal()]);
	}

	public boolean isPerpendicular(MgDirection dir) {
		return Math.abs(dir.getOffsetX()) != Math.abs(getOffsetX()) || Math.abs(dir.getOffsetY()) != Math.abs(getOffsetY()) || Math.abs(dir.getOffsetZ()) != Math.abs(getOffsetZ());
	}
	
	public boolean isParallel(MgDirection dir) {
		return !isPerpendicular(dir);
	}
}
