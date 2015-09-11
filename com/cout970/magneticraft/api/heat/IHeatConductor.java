package com.cout970.magneticraft.api.heat;

import com.cout970.magneticraft.api.util.IConnectable;

/**
 * @author Cout970
 */
public interface IHeatConductor extends IConnectable {

    /**
     * @return the temperature in celsius degrees
     */
    public double getTemperature();

    /**
     * Used for client side sync
     *
     * @param heat
     */
    public void setTemperature(double heat);

    /**
     * the temperature before the block is melted
     */
    public double getMaxTemp();

    /**
     * the amount of mass, usually 1000
     *
     * @return
     */
    public double getMass();

    public double getSpecificHeat();

    /**
     * Add some calories to the block
     *
     * @param j
     */
    public void applyCalories(double j);

    /**
     * remove some calories from the block
     *
     * @param j
     */
    public void drainCalories(double j);

    /**
     * the resistance of the heat to cross the block
     *
     * @return
     */
    public double getResistance();

    public void onBlockOverHeat();
}
