package com.cout970.magneticraft.api.electricity;


public interface ITileElectricPole {

    public IElectricPole getPoleConnection();

    public ITileElectricPole getMainTile();
}
