package com.cout970.magneticraft.api.heat;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public interface IHeatConductor {

	/**
	 * the tile Entit that has the HeatConductor
	 * @return
	 */
	public TileEntity getParent();
	
	/**
	 * 
	 * @return the temperature in celsius degrees
	 */
	public double getTemperature();
	/**
	 * Used for client side sync
	 * @param heat
	 */
	public void setTemperature(double heat);
	
	/**
	 * the temperature before the block is melted
	 */
	public double getMaxTemp();
	
	/**
	 * the amount of mass, usually 1000
	 * @return
	 */
	public double getMass();
	
	/**
	 * Move the heat from a block to another
	 */
	public void iterate();
	
	/**
	 * Add some calories to the block
	 * @param j
	 */
	public void applyCalories(double j);
	
	/**
	 * remove some calories from the block
	 * @param j
	 */
	public void drainCalories(double j);
	
	/**
	 * the resistance of the heat to cross the block
	 * @return
	 */
	public double getResistance();
	
	public void onBlockOverHeat();
	
	public MgDirection[] getValidConnections();
	
	public boolean isAbleToconnect(IHeatConductor cond, VecInt dir);

	//save and load data 
	public void save(NBTTagCompound nbt);
	public void load(NBTTagCompound nbt);
}
