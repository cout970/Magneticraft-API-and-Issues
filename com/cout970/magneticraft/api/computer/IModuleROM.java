package com.cout970.magneticraft.api.computer;

public interface IModuleROM extends IHardwareModule{

	public void loadToRAM(IModuleMemoryController ram);
}
