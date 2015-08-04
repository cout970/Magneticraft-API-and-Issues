package com.cout970.magneticraft.api.computer.prefab;

import com.cout970.magneticraft.api.computer.IBusWire;
import com.cout970.magneticraft.api.computer.IPeripheral;
import com.cout970.magneticraft.api.computer.IPeripheralProvider;
import com.cout970.magneticraft.api.util.PathFinder;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ComputerPathFinder extends PathFinder{

	public IPeripheral result;
	public int address;
	public World w;
	
	public ComputerPathFinder(TileEntity t, int address) {
		this.address = address;
		w = t.getWorldObj();
	}
	
	@Override
	public boolean step(VecInt coord) {
		TileEntity t = w.getTileEntity(coord.getX(), coord.getY(), coord.getZ());

		if(t instanceof IPeripheral && ((IPeripheral) t).getAddress() == address){
			result = (IPeripheral) t;
			return false;
		}
		if(t instanceof IPeripheralProvider){
			IPeripheralProvider p = (IPeripheralProvider) t;
			if(p.getPeripherals() != null){
				for(IPeripheral per : p.getPeripherals()){
					if(per.getAddress() == address){
						result = per;
						return false;
					}
				}
			}
		}
		if(t instanceof IBusWire){
			addNeigBlocks(coord);
		}
		return true;
	}

}
