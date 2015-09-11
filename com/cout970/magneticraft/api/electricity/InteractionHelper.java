package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class InteractionHelper {

    private static List<IEnergyInterfaceFactory> handlers = new ArrayList<IEnergyInterfaceFactory>();

    public static IEnergyInterface processTile(TileEntity tile, VecInt f, int tier) {
        for (IEnergyInterfaceFactory factory : handlers) {
            if (factory.shouldHandleTile(tile, f, tier)) {
                return factory.getEnergyInterface(tile, f, tier);
            }
        }
        return null;
    }

    public static boolean registerEnergyInterfaceFactory(IEnergyInterfaceFactory factory) {
        if (handlers.contains(factory)) return false;
        handlers.add(factory);
        return true;
    }

    public static List<IEnergyInterfaceFactory> getRegisteredFactories() {
        return handlers;
    }
}
