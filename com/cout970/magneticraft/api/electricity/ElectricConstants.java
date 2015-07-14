package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.EnergyConversor;

/**
 * 
 * @author Cout970
 *
 */
public class ElectricConstants {
																				
	public static final double MAX_VOLTAGE 				= 240;					
		
	public static final double MACHINE_DISCHARGE 		= MAX_VOLTAGE*7/12;
	public static final double MACHINE_CHARGE 			= MAX_VOLTAGE*8/12;
	public static final double MACHINE_WORK 			= MAX_VOLTAGE/2;
	public static final double GENERATOR_DISCHARGE 		= MAX_VOLTAGE*8/12;
	public static final double GENERATOR_CHARGE 		= MAX_VOLTAGE*9/12;
	public static final double BATTERY_DISCHARGE 		= MAX_VOLTAGE*9/12;
	public static final double BATTERY_CHARGE 			= MAX_VOLTAGE*10/12;
	public static final double RESISTANCE_COPPER_LOW	= 0.01D;
	public static final double RESISTANCE_COPPER_MED	= 0.05D;
	public static final double RESISTANCE_COPPER_HIG	= 0.25D;
	public static final double ALTERNATOR_DISCHARGE		= MAX_VOLTAGE;
	public static final double ENERGY_INTERFACE_LEVEL	= MAX_VOLTAGE/2;
	public static final double CONVERSION_SPEED 		= EnergyConversor.RFtoW(5);
	public static final double RESISTANE_COPPER_WIRE 	= RESISTANCE_COPPER_LOW;
}
