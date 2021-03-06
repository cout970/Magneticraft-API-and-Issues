package com.cout970.magneticraft.api.util;


/**
 * @author Cout970
 */
public enum ConnectionClass {

    FULL_BLOCK,
    SLAB_BOTTOM(MgDirection.DOWN),
    SLAB_TOP(MgDirection.UP),
    SLAB_NORTH(MgDirection.NORTH),
    SLAB_SOUTH(MgDirection.SOUTH),
    SLAB_WEST(MgDirection.WEST),
    SLAB_EAST(MgDirection.EAST),
    CABLE_LOW,
    Cable_MEDIUM,
    CABLE_HIGH,
    CABLE_HUGE;

    public MgDirection orientation;

    private ConnectionClass(MgDirection dir) {
        orientation = dir;
    }

    private ConnectionClass() {
    }

    public static boolean isSlabCompatible(ConnectionClass a, ConnectionClass b) {
        if (a.orientation == null || b.orientation == null) return false;
        if (a.orientation.isParallel(b.orientation)) return false;
        return true;
    }
}
