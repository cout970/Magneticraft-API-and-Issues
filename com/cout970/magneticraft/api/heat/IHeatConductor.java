package com.cout970.magneticraft.api.heat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IHeatConductor {

	//basic heat utility
	public TileEntity getParent();
	
	public double getTemperature();
	public void setTemperature(double heat);
	public double getMaxTemp();
	public double getMass();
	
	public void iterate();
	public void applyCalories(double j);
	public void drainCalories(double j);
	public double getResistance();

	//save and load data 
	public void save(NBTTagCompound nbt);
	public void load(NBTTagCompound nbt);
}
