package com.cout970.magneticraft.api.computer;

public interface IModuleROM extends IHardwareComponent{

	public void loadToRAM(IModuleMemoryController ram);
}
