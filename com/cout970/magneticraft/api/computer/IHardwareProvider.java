package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;

public interface IHardwareProvider {

	public IHardwareComponent getHardware(ItemStack item);
	
	public ModuleType getModuleType(ItemStack item);
	
	public enum ModuleType{
		CPU,RAM,ROM,DISK,GPU,PERIPHERAL;
	}
}
