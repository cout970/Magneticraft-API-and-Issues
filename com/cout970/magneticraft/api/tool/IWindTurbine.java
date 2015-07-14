package com.cout970.magneticraft.api.tool;

import com.cout970.magneticraft.api.util.IRenderizable;

/**
 * Interface to implement in an Item to be able to work in the Wind Turbine
 * @author Cout970
 *
 */
public interface IWindTurbine {

	/**
	 * should be more than 3 and be diferent for every type of turbine
	 * @return unique id
	 */
	public int getID();

	/**
	 * number of blocks from the base block, equal to radio
	 * @return
	 */
	public int getHeight();

	/**
	 * number of blocks from the base block, equal to radio
	 * @return
	 */
	public int getLenght();

	/**
	 * @return amount of enery produced in the best conditions
	 */
	public double getPotency();

	/**
	 * the render scale
	 * @return
	 */
	public float getScale();
	
	/**
	 * the the turbine item change, this method is called to generate an Object to render the new Turbine item
	 * @return the render object
	 */
	public IRenderizable initRender();
}
