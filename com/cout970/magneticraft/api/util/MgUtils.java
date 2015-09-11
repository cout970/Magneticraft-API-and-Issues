package com.cout970.magneticraft.api.util;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.api.computer.IOpticFiber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cout970
 */
public class MgUtils {

    /**
     * Useful method to get an adjacent TileEntity
     *
     * @param tile
     * @param d
     * @return
     */
    public static TileEntity getTileEntity(TileEntity tile, VecInt d) {
        return tile.getWorldObj().getTileEntity(tile.xCoord + d.getX(), tile.yCoord + d.getY(), tile.zCoord + d.getZ());
    }

    /**
     * Useful method to get an adjacent TileEntity
     *
     * @param tile
     * @param d
     * @return
     */
    public static TileEntity getTileEntity(TileEntity tile, MgDirection d) {
        return tile.getWorldObj().getTileEntity(tile.xCoord + d.getOffsetX(), tile.yCoord + d.getOffsetY(), tile.zCoord + d.getOffsetZ());
    }

    /**
     * Return the TileEntities adjacent to a Blocks
     *
     * @param t
     * @return
     */
    public static List<TileEntity> getNeig(TileEntity t) {
        List<TileEntity> list = new ArrayList<TileEntity>();
        for (MgDirection d : MgDirection.values()) {
            TileEntity f = getTileEntity(t, d);
            if (f != null) list.add(f);
        }
        return list;
    }

    /**
     * checks if specified Block can be mined by a miner or by a BlockBreaker
     *
     * @param w
     * @param info
     * @return
     */
    public static boolean isMineableBlock(World w, BlockInfo info) {
        if (info.getBlock() == Blocks.air) return false;
        if (info.getBlock() instanceof BlockLiquid) return false;
        if (info.getBlock() instanceof BlockFluidBase) return false;
        if (Block.isEqualTo(info.getBlock(), Blocks.mob_spawner)) return false;
        if (info.getBlock() == Blocks.portal) return false;
        if (info.getBlock() == Blocks.end_portal) return false;
        if (info.getBlock() == Blocks.end_portal_frame) return false;
        if (info.getBlock().getBlockHardness(w, info.getX(), info.getY(), info.getZ()) == -1) return false;
        return true;
    }

    /**
     * Checks if two fluidStacks are equal
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean areEqual(FluidStack a, FluidStack b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (FluidRegistry.getFluidName(a).equalsIgnoreCase(FluidRegistry.getFluidName(b))) return true;
        return false;
    }

    /**
     * checks if two itemStacks are equal or have the same id in OreDictionary
     *
     * @param a
     * @param b
     * @param meta
     * @return
     */
    public static boolean areEqual(ItemStack a, ItemStack b, boolean meta) {
        if (a == null && b == null) return true;
        if (a != null && b != null && a.getItem() != null && b.getItem() != null) {
            if (OreDictionary.itemMatches(a, b, meta)) return true;
            int[] c = OreDictionary.getOreIDs(a);
            int[] d = OreDictionary.getOreIDs(b);
            if (c.length > 0 && d.length > 0) {
                for (int i : c) {
                    for (int j : d)
                        if (i == j) return true;
                }
            }
        }
        return false;
    }

    public static TileEntity getTileEntity(World w, VecInt v) {
        return w.getTileEntity(v.getX(), v.getY(), v.getZ());
    }

    public static boolean contains(MgDirection[] vec, MgDirection d) {
        for (MgDirection dir : vec) {
            if (dir == d) return true;
        }
        return false;
    }

    public static IOpticFiber getOpticFiber(TileEntity tile, MgDirection dir) {
        if (tile instanceof TileMultipart) {
            for (TMultiPart p : ((TileMultipart) tile).jPartList()) {
                if (p instanceof IOpticFiber) {
                    return (IOpticFiber) p;
                }
            }
        }
        return null;
    }
}
