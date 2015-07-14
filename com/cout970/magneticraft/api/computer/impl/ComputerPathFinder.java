package com.cout970.magneticraft.api.computer.impl;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.computer.IBusConnectable;
import com.cout970.magneticraft.api.computer.IBusWire;
import com.cout970.magneticraft.api.computer.IComputer;
import com.cout970.magneticraft.api.util.PathFinder;
import com.cout970.magneticraft.api.util.VecInt;

public class ComputerPathFinder extends PathFinder{

	public IBusConnectable result;
	public int address;
	public World w;
	
	public ComputerPathFinder(TileEntity t, int address) {
		this.address = address;
		w = t.getWorldObj();
	}
	
	@Override
	public boolean step(VecInt coord) {
		TileEntity t = w.getTileEntity(coord.getX(), coord.getY(), coord.getZ());
		
		if(t instanceof IBusConnectable && ((IBusConnectable) t).getAddress() == address){
			result = (IBusConnectable) t;
			return false;
		}else if(t instanceof IComputer){
			int i = 0;
			while(((IComputer) t).getDrive(i) != null){
				if(((IComputer) t).getDrive(i) instanceof IBusConnectable && ((IBusConnectable) ((IComputer) t).getDrive(i)).getAddress() == address){
					result = (IBusConnectable) ((IComputer) t).getDrive(i);
					
					return false;
				}
				i++;
			}
		}else if(t instanceof IBusWire){
			addNeigBlocks(coord);
		}
		return true;
	}

}
