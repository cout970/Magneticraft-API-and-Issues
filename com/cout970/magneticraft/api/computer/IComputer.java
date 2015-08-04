package com.cout970.magneticraft.api.computer;

import net.minecraft.tileentity.TileEntity;

public interface IComputer extends IPeripheralProvider{

	public IModuleCPU getCPU();
	public IModuleMemoryController getMemory();
	public TileEntity getParent();
	
}
