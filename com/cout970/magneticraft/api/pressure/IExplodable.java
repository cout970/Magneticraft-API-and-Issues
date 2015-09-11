package com.cout970.magneticraft.api.pressure;

import net.minecraft.world.World;

public interface IExplodable {
    void explode(World world, int x, int y, int z, boolean explodeNeighbors);
}
