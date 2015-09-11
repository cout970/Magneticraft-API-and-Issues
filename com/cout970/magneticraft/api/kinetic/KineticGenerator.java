package com.cout970.magneticraft.api.kinetic;

import net.minecraft.tileentity.TileEntity;

public class KineticGenerator extends KineticConductor implements IKineticController {

    private boolean update = false;

    public KineticGenerator(TileEntity p) {
        super(p);
        type = KineticType.Generator;
    }

    @Override
    public void iterate() {
        super.iterate();
        if (net == null) {
            net = new KineticNetwork(this);
            net.findComponents();
        }
        if (!update) {
            if (net.world.getTotalWorldTime() % 20 == 0) {
                net.findComponents();
                net.ajustRotation(rotation);
                net.preventUpdates();
            }
        } else update = false;
    }

    @Override
    public void preventUpdate() {
        update = true;
    }
}
