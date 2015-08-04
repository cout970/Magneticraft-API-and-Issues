package com.cout970.magneticraft.api.pressure;

import com.cout970.magneticraft.api.util.IConnectable;

public interface IPressureConductor extends IConnectable{
	
	/**
	 * Ideal Gas constant
	 */
	public static final double R = 8.3144621;

	public double getVolume();
	public void setVolume(double c);
	
	public double getPressure();
	public void setPressure(double p);
	
	public double getTemperature();
	public void setTemperature();
	
	public void applyGas(double j);
	public void drainGas(double j);
	
	public void onBlockExplode();
}
