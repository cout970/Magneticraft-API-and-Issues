package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IInterPoleWire {

	public void setWorld(World w);
	public World getWorld();
	public void iterate();
	public double getEnergyFlow();
	public void setEnergyFlow(double energyFlow);
	public IElectricPole getStart();
	public IElectricPole getEnd();
	public VecInt vecStart();
	public VecInt vecEnd();
	public double getDistance();
	public void save(NBTTagCompound nbt);
	public void load(NBTTagCompound nbt);
}
