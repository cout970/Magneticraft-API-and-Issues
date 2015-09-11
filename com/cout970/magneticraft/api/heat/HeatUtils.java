package com.cout970.magneticraft.api.heat;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class HeatUtils {

    /**
     * Created to implement ForgeMultipart HeatConductors in the future
     *
     * @param tile tile entity to get the conductor
     * @param d    vector from the method caller
     * @return the coductor is exist
     */
    public static IHeatConductor[] getHeatCond(TileEntity tile, VecInt d) {
        if (tile instanceof IHeatTile) return ((IHeatTile) tile).getHeatCond(d.getOpposite());
        if (tile instanceof TileMultipart) {
            ArrayList<IHeatConductor> comp = new ArrayList<IHeatConductor>();
            for (TMultiPart m : ((TileMultipart) tile).jPartList()) {
                if (m instanceof IHeatMultipart) {
                    comp.add(((IHeatMultipart) m).getHeatConductor());
                }
            }
            return comp.toArray(new IHeatConductor[comp.size()]);
        }
        return null;
    }

}
