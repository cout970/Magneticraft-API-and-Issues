package com.cout970.magneticraft.api.pressure;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PressureUtils {

    public static IExplodable getExplodable(World world, VecInt pos) {
        TileEntity tile = pos.getTileEntity(world);
        if (tile instanceof TileMultipart) {
            TileMultipart mp = (TileMultipart) tile;
            for (TMultiPart part : mp.jPartList()) {
                if (part instanceof IExplodable) {
                    return (IExplodable) part;
                }
            }
        }
        if (tile instanceof IExplodable) {
            return (IExplodable) tile;
        }
        Block b = pos.getBlock(world);
        if (b instanceof IExplodable) {
            return (IExplodable) b;
        }
        return null;
    }

    public static List<IPressureConductor> getPressureCond(TileEntity tile, VecInt f) {
        List<IPressureConductor> conds = new ArrayList<IPressureConductor>();
        if (tile instanceof IPressurePipe) {
            for (IPressureConductor con : ((IPressurePipe) tile).getPressureConductor()) {
                if (con != null) {
                    conds.add(con);
                }
            }
        }
        if (tile instanceof TileMultipart) {
            for (TMultiPart part : ((TileMultipart) tile).jPartList()) {
                if (part instanceof IPressureMultipart) {
                    if (((IPressureMultipart) part).getPressureConductor() != null)
                        conds.add(((IPressureMultipart) part).getPressureConductor());
                }
            }
        }
        return conds;
    }
}
