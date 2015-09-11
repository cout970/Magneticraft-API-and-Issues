package com.cout970.magneticraft.api.electricity;

/**
 * @author Cout970
 */
public interface IElectricMultiPart {

    /**
     * used only for microparts
     *
     * @return
     */
    public IElectricConductor getElectricConductor(int tier);
}
