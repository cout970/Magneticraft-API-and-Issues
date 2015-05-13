package com.cout970.magneticraft.api.electricity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.util.VecInt;

public interface IElectricConductor {

	//basic energy utility
	/**
	 * @return the tileEntity that stores the conductor
	 */
	public TileEntity getParent();
	/**
	 * @return the voltage stored in the conductor
	 */
	public double getVoltage();
	/**
	 * Used for high voltage cables
	 * @return 10^Vtier
	 */
	public double getVoltageMultiplier();
	/**
	 * @return the flow generated when energy pass through
	 */
	public double getIndScale();
	/**
	 * this method should prepare the basic things for the iteration
	 */
	public void recache();
	/**
	 * this method balances the energy in the adjacent conductors
	 */
	public void iterate();
	/**
	 * this method add to the voltage the intensity in amps(I * 0.05 seconds/tick)
	 */
	public void computeVoltage();
	/**
	 * @return Intensity that pass through in the last iteration
	 */
	public double getIntensity();
	/**
	 * @return the constant of resistance.
	 */
	public double getResistance();
	/**
	 * @return constant always 0.5, used to get (v2 - v1)/2 ==> (v2 - v1)*0.5
	 * 
	 */
	public double getCondParallel();
	/**
	 * Adds an intensity to the conductor, allow negative values
	 */
	public void applyCurrent(double amps);
	/**
	 * adds Watts to the conductor, negative values are not allowed
	 */
	public void applyPower(double amps);
	/**
	 *remove Watts from the conductor, negative values are not allowed
	 */
	public void drainPower(double amps);
	
	
	//save and load data 
	public void save(NBTTagCompound nbt);
	public void load(NBTTagCompound nbt);
	
	//sync client and server
	public void setResistence(double d);
	public void setVoltage(double d);
	
	//storage in the internal buffer, only for machines and batteries
	public int getStorage();
	public int getMaxStorage();
	public void setStorage(int charge);
	public void applyCharge(int charge);
	public void drainCharge(int charge);
	
	//cable connections
	/**
	 * rest the indexed connexions
	 */
	public void disconect();
	
	/**
	 * @return true if recache method was called after to disconet()
	 */
	public boolean isConected();
	
	/**
	 * @return the Indexed connexions established, used to not repeat them.
	 */
	public IndexedConnexion[] getConnexions();
	
	/**
	 * @return possibles connections that the conductor can have.
	 */
	public VecInt[] getValidConnections();
	
	/**
	 * @return can conect with other conductor, used for microblocks with covers and for non full blocks like solar panels
	 */
	public boolean isAbleToConnect(IElectricConductor c, VecInt enumFacing);
	
	/**
	 * @return the type of cable
	 */
	public ConnectionClass getConnectionClass(VecInt v);
	
	/**
	 * @return the tier of the conductor, used for high voltage.
	 */
	public int getTier();
	
}
