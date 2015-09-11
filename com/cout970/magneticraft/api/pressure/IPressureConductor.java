package com.cout970.magneticraft.api.pressure;

import com.cout970.magneticraft.api.util.IConnectable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IPressureConductor extends IConnectable {

    /**
     * Ideal Gas constant
     * units: (Pa*m^3)/(mol*K) or J/(mol*K)
     */
    public static final double R = 8.3144621;

    /**
     * the volume should be in mB mili Buckets
     * volume must be more than 0
     *
     * @return
     */
    public double getVolume();

    public void setVolume(double c);

    /**
     * the pressure should be in Pa (Pascals)
     *
     * @return
     */
    public double getPressure();

    public double getMaxPressure();

    /**
     * moles of gas in the conductor
     *
     * @return
     */
    public double getMoles();

    public void setMoles(double moles);

    /**
     * temperature of the gas in kelvin
     *
     * @return
     */
    public double getTemperature();

    public void setTemperature(double temp);

    /**
     * add or remove gass from the conductor, the values must be positive and the units moles
     */

    /**
     * @param gas
     * @return the amount accepted by the conductor, will be 0 if the fluid is not valid(no gas or diferent fluid) or the same amount as the fluidstack.
     */
    public int applyGas(FluidStack gas, boolean doFill);

    public FluidStack drainGas(int amount, boolean doDrain);

    public void onBlockExplode();

    public Fluid getFluid();

    public void setFluid(Fluid fluid);
}
