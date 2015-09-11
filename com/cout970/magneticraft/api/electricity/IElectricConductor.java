package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.IConnectable;

/**
 * @author Cout970
 */
public interface IElectricConductor extends IConnectable {

    //basic energy utility

    /**
     * @return the voltage stored in the conductor
     */
    public double getVoltage();

    /**
     * Used for high voltage cables
     *
     * @return 10^tier
     */
    public double getVoltageMultiplier();

    /**
     * @return the flow generated when energy pass through, should be constant
     */
    public double getIndScale();

    /**
     * @return the inverse of the capacity of the block, voltge capacity, no storage capacity
     */
    public double getInvCapacity();

    /**
     * this method should prepare the basic things for the iteration, like the connexions
     */
    public void recache();

    /**
     * this method add to the voltage the intensity in amps(I * 0.05 seconds/tick * getVoltageMultiplier())
     */
    public void computeVoltage();

    /**
     * @return Intensity that pass through in the last iteration, only for display
     */
    public double getIntensity();

    /**
     * @return the constant of resistance, must be positive and non cero.
     */
    public double getResistance();

    /**
     * @return constant always 0.5, used to get (v2 - v1)/2 ==> (v2 - v1)*0.5
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
     * remove Watts from the conductor, negative values are not allowed
     */
    public void drainPower(double amps);

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
     * reset the connexions
     */
    public void disconect();

    /**
     * @return true if recache method was called after to disconet()
     */
    public boolean isConected();

    /**
     * @return the Indexed connexions established, used to not repeat them.
     */
    public IIndexedConnection[] getConnections();

    /**
     * @param con connexion between two conductors
     * @return if the energy can flow on this connection
     */
    public boolean canFlowPower(IIndexedConnection con);

    /**
     * @return the tier of the conductor, used for high voltage.
     */
    public int getTier();

}
