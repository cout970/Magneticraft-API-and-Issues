package com.cout970.magneticraft.api.electricity.wires;

public interface ITileElectricPole {

	public IElectricPole getPoleConnection();
	
	public ITileElectricPole getMainTile();
}
