package com.cout970.magneticraft.api.electricity;

public class ElectricConstants {

	public static final double MAX_VOLTAGE 			= 240;//120;//generators voltage limit
	public static final double COMPT_DRAIN 			= MAX_VOLTAGE/2;//60;//voltage until transfer to an ICompatibilityInterface from a Conductor
	public static final double COMPT_APPLY 			= MAX_VOLTAGE*8d/12d;//80;//voltage until transfer to an Conductor from an ICompatibilityInterface
	public static final double CONVERSION_SPEED 	= 84000/MAX_VOLTAGE;//700;//Voltage * speed = energy transfer
	public static final double MACHINE_DISCHARGE 	= MAX_VOLTAGE*7/12;//70;
	public static final double MACHINE_CHARGE 		= MAX_VOLTAGE*8/12;//80;
	public static final double MACHINE_WORK 		= MAX_VOLTAGE/2;//60;
	public static final double GENERATOR_DISCHARGE 	= MAX_VOLTAGE*8/12;//80;
	public static final double GENERATOR_CHARGE 	= MAX_VOLTAGE*9/12;//90;
	public static final double BATTERY_DISCHARGE 	= MAX_VOLTAGE*9/12;//90;
	public static final double BATTERY_CHARGE 		= MAX_VOLTAGE*10/12;//100;
	public static final double RESISTANCE_COPPER_2X2= 0.01D;
	public static final double RESISTANCE_COPPER_4X4= 0.005D;
}
