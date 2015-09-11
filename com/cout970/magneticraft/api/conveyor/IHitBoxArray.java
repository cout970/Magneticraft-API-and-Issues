package com.cout970.magneticraft.api.conveyor;

public interface IHitBoxArray {

    public int size();

    public boolean hasSpace(int pos);

    public void setOccupied(int pos, boolean occupied);

    public void clear();
}
