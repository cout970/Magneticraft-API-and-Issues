package com.cout970.magneticraft.api.util;

import net.minecraft.tileentity.TileEntity;

public class BlockPosition extends VecInt
{

    public BlockPosition(int x, int y, int z){
        super(x, y, z);
    }

    public BlockPosition(double x, double y, double z){
        super(x, y, z);
    }

    public BlockPosition(VecInt vec){
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    public BlockPosition(int[] i) {
    	this(i[0],i[1],i[2]);
    }

	public BlockPosition(TileEntity t) {
		this(t.xCoord, t.yCoord, t.zCoord);
	}

	public int[] intArray() {
		return new int[]{getX(),getY(),getZ()};
	}
}