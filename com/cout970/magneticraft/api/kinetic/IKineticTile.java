package com.cout970.magneticraft.api.kinetic;

import com.cout970.magneticraft.api.util.MgDirection;

public interface IKineticTile {

	public IKineticConductor getKineticConductor(MgDirection dir);

	public MgDirection[] getValidSides();
}
