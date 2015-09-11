package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.EnergyConversor;

/**
 * @author Cout970
 */
public class ElectricConstants {

    public static double MAX_VOLTAGE = 240;

    public static double MACHINE_DISCHARGE = MAX_VOLTAGE * 7 / 12;
    public static double MACHINE_CHARGE = MAX_VOLTAGE * 8 / 12;
    public static double MACHINE_WORK = MAX_VOLTAGE / 2;
    public static double GENERATOR_DISCHARGE = MAX_VOLTAGE * 8 / 12;
    public static double GENERATOR_CHARGE = MAX_VOLTAGE * 9 / 12;
    public static double BATTERY_DISCHARGE = MAX_VOLTAGE * 9 / 12;
    public static double BATTERY_CHARGE = MAX_VOLTAGE * 10 / 12;
    public static double RESISTANCE_COPPER_LOW = 0.01D;
    public static double RESISTANCE_COPPER_MED = 0.05D;
    public static double RESISTANCE_COPPER_HIGH = 0.25D;
    public static double ALTERNATOR_DISCHARGE = MAX_VOLTAGE;
    public static double ENERGY_INTERFACE_LEVEL = MAX_VOLTAGE / 2;
    public static double CONVERSION_SPEED = EnergyConversor.RFtoW(5);
    public static double RESISTANE_COPPER_WIRE = RESISTANCE_COPPER_LOW;
}
