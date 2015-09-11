package com.cout970.magneticraft.api.util;

import net.minecraft.block.Block;

/**
 * An Object to store all posible data from a single block
 *
 * @author Cout970
 */
public class BlockInfo {

    private Block b;
    private int meta;
    private int x, y, z;

    public BlockInfo(Block b, int meta) {
        this.b = b;
        this.meta = meta;
    }

    public BlockInfo(Block b, int meta, int x, int y, int z) {
        this(b, meta);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockInfo(Block b2, int meta2, VecInt a) {
        this(b2, meta2, a.getX(), a.getY(), a.getZ());
    }

    public Block getBlock() {
        return b;
    }

    /**
     * if meta is not used or ignored should be -1
     *
     * @return
     */
    public int getMeta() {
        return meta;
    }

    public int[] getPosition() {
        return new int[]{x, y, z};
    }

    public boolean equals(Object o) {
        if (o instanceof BlockInfo) {
            if (((BlockInfo) o).getBlock() == this.b && (meta == -1 || ((BlockInfo) o).getMeta() == meta)) return true;
        }
        return false;
    }

    public String toString() {
        return "Block: " + b.getUnlocalizedName() + " Metadata: " + meta + " Pos: " + x + " " + y + " " + z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
