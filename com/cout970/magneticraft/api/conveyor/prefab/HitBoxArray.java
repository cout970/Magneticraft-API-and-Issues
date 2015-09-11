package com.cout970.magneticraft.api.conveyor.prefab;

import com.cout970.magneticraft.api.conveyor.IHitBoxArray;

import java.util.Arrays;

public class HitBoxArray implements IHitBoxArray {

    protected boolean[] spaces = new boolean[16];

    public int size() {
        return spaces.length;
    }

    @Override
    public boolean hasSpace(int pos) {
        return spaces[pos];
    }

    @Override
    public void setOccupied(int pos, boolean occupied) {
        spaces[pos] = occupied;
    }

    @Override
    public void clear() {
        Arrays.fill(spaces, false);
    }
}
