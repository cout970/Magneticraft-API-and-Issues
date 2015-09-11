package com.cout970.magneticraft.api.util;


public enum Orientation {

    UP_NORTH(1, MgDirection.NORTH),
    UP_EAST(1, MgDirection.EAST),
    UP_SOUTH(1, MgDirection.SOUTH),
    UP_WEST(1, MgDirection.WEST),
    NORTH(0, MgDirection.NORTH),
    EAST(0, MgDirection.EAST),
    SOUTH(0, MgDirection.SOUTH),
    WEST(0, MgDirection.WEST),
    DOWN_NORTH(-1, MgDirection.NORTH),
    DOWN_EAST(-1, MgDirection.EAST),
    DOWN_SOUTH(-1, MgDirection.SOUTH),
    DOWN_WEST(-1, MgDirection.WEST);

    private int level;
    private MgDirection dir;

    private Orientation(int l, MgDirection dir) {
        level = l;
        this.dir = dir;
    }

    public MgDirection getDirection() {
        return dir;
    }

    public int getLevel() {
        return level;
    }

    public static Orientation find(int level, MgDirection dir) {
        for (Orientation o : values()) {
            if (o.level == level && o.dir == dir) return o;
        }
        return null;
    }

    public Orientation rotateY(boolean left) {
        return find(level, dir.step(left ? MgDirection.DOWN : MgDirection.UP));
    }

    public int toMeta() {
        return ordinal();
    }

    public static Orientation fromMeta(int meta) {
        return values()[meta % values().length];
    }
}