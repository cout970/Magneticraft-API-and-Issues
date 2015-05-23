package com.cout970.magneticraft.api.computer;

import java.util.List;

import net.minecraft.tileentity.TileEntity;

public interface IComputer {

	public IModuleCPU getCPU();
	public IModuleMemoryController getMemory();
	public IModuleDiskDrive getDrive(int n);
	public List<IBusConnectable> getConetedDevices();
	public TileEntity getParent();
	
}
