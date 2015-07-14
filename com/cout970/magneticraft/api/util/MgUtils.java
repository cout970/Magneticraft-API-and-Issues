package com.cout970.magneticraft.api.util;

import java.util.ArrayList;
import java.util.List;

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
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

import com.cout970.magneticraft.api.computer.IOpticFiber;
import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.IElectricMultiPart;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IndexedConnection;
import com.cout970.magneticraft.api.electricity.compact.InteractionHelper;
import com.cout970.magneticraft.api.electricity.wires.IElectricPole;
import com.cout970.magneticraft.api.electricity.wires.ITileElectricPole;
import com.cout970.magneticraft.api.heat.CompoundHeatCables;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatMultipart;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.kinetic.IKineticConductor;

/**
 * 
 * @author Cout970
 *
 */
public class MgUtils {

	/**
	 * Checks if a connection is already formed and should not repeat, used for Electric conductors connections 
	 * @param con
	 * @param opp
	 * @return
	 */
	public static boolean alreadyContains(IndexedConnection[] con, VecInt opp) {
		if(con == null)return false;
		if(opp == null)return false;
		for(IndexedConnection i : con)
			if(opp == i.vecDir)return true;
		return false;
	}

	/**
	 * Usefull method to get an adjacent TileEntity
	 * @param tile
	 * @param d
	 * @return
	 */
	public static TileEntity getTileEntity(TileEntity tile, VecInt d) {
		if(tile == null)return null;
		return tile.getWorldObj().getTileEntity(tile.xCoord+d.getX(), tile.yCoord+d.getY(), tile.zCoord+d.getZ());
	}
	
	/**
	 * Usefull method to get an adjacent TileEntity
	 * @param tile
	 * @param d
	 * @return
	 */
	public static TileEntity getTileEntity(TileEntity tile, MgDirection d){
		return tile.getWorldObj().getTileEntity(tile.xCoord+d.getOffsetX(), tile.yCoord+d.getOffsetY(), tile.zCoord+d.getOffsetZ());
	}
	
	/**
	 * Created to implement ForgeMultipart HeatConductors in the future
	 * @param tile tile entity to get the conductor 
	 * @param d vector from the method caller
	 * @return the coductor is exist
	 */
	public static CompoundHeatCables getHeatCond(TileEntity tile, VecInt d) {
		if(tile instanceof IHeatTile)return ((IHeatTile) tile).getHeatCond(d.getOpposite());
		if(tile instanceof TileMultipart){
			CompoundHeatCables comp = null;
			for(TMultiPart m : ((TileMultipart) tile).jPartList()){
				if(m instanceof IHeatMultipart){
					if(comp == null){
						comp = new CompoundHeatCables(((IHeatMultipart) m).getHeatConductor());
					}else{
						comp.add(((IHeatMultipart) m).getHeatConductor());
					}
				}
			}
			return comp;
		}
		return null;
	}

	/**
	 * Return the CableCompound in a Block, allowing multipart detection
	 * @param tile
	 * @param f
	 * @param tier
	 * @return
	 */
	public static CompoundElectricCables getElectricCond(TileEntity tile, VecInt f, int tier) {
		if(tile instanceof TileMultipart){
			CompoundElectricCables cab = null;
			for(TMultiPart m : ((TileMultipart) tile).jPartList()){
				if(m instanceof IElectricMultiPart && ((IElectricMultiPart) m).getElectricConductor(tier) != null){
					if(cab == null){
						cab = new CompoundElectricCables(((IElectricMultiPart) m).getElectricConductor(tier));
					}else{
						cab.add(((IElectricMultiPart) m).getElectricConductor(tier));
					}
				}
			}
			return cab;
		}
		if(tile instanceof IElectricTile)return ((IElectricTile) tile).getConds(f,tier);
		return null;
	}
	
	/**
	 * Find a Interface between to energy systems like railcraft change or RF 
	 * @param t
	 * @param i
	 * @param tier
	 * @return
	 */
	public static IEnergyInterface getInterface(TileEntity t,VecInt i,int tier){
		return InteractionHelper.processTile(t,i, tier);
	}

	/**
	 * checks if the tileEntity is a Conductor
	 * @param tile
	 * @param tier
	 * @return
	 */
	public static boolean isConductor(TileEntity tile, int tier){
		return getElectricCond(tile, VecInt.NULL_VECTOR, tier) != null;
	}

	/**
	 * Return the TileEntities adjacent to a Blocks
	 * @param t
	 * @return
	 */
	public static List<TileEntity> getNeig(TileEntity t) {
		List<TileEntity> list = new ArrayList<TileEntity>();
		for(MgDirection d : MgDirection.values()){
			TileEntity f = getTileEntity(t, d);
			if(f != null)list.add(f);
		}
		return list;
	}

	/**
	 * checks if an Expecific Block can be mined by a miner or by a BlockBreaker
	 * @param w
	 * @param info
	 * @return
	 */
	public static boolean isMineableBlock(World w, BlockInfo info) {
		if(info.getBlock() == Blocks.air)return false;
		if(info.getBlock() instanceof BlockLiquid)return false;
		if(info.getBlock() instanceof BlockFluidBase)return false;
		if(Block.isEqualTo(info.getBlock(),Blocks.mob_spawner))return false;
		if(info.getBlock() == Blocks.portal)return false;
		if(info.getBlock() == Blocks.end_portal)return false;
		if(info.getBlock() == Blocks.end_portal_frame)return false;
		if(info.getBlock().getBlockHardness(w, info.getX(), info.getY(), info.getZ()) == -1)return false;
		return true;
	}

	/**
	 * Checks if two fluidStacks are equal
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean areEcuals(FluidStack a, FluidStack b) {
		if(a == null && b == null)return true;
		if(a == null || b == null)return false;
		if(FluidRegistry.getFluidName(a).equalsIgnoreCase(FluidRegistry.getFluidName(b)))return true;
		return false;
	}
	
	/**
	 * checks if two itemStacks are equal or has the same id in OreDictionary
	 * @param a
	 * @param b
	 * @param meta
	 * @return
	 */
	public static boolean areEcuals(ItemStack a, ItemStack b, boolean meta){
		if(a == null && b == null)return true;
		if(a != null && b != null && a.getItem() != null && b.getItem() != null){
			if(OreDictionary.itemMatches(a, b, meta))return true;
			int[] c = OreDictionary.getOreIDs(a);
			int[] d = OreDictionary.getOreIDs(b);
			if(c.length > 0 && d.length > 0){
				for(int i : c){
					for(int j : d)
						if(i == j)return true;
				}
			}
		}
		return false;
	}

	public static TileEntity getTileEntity(World w, VecInt v) {
		return w.getTileEntity(v.getX(), v.getY(), v.getZ());
	}

	public static boolean contains(MgDirection[] vec, MgDirection d) {
		for(MgDirection dir: vec){
			if(dir == d)return true;
		}
		return false;
	}

	public static IElectricPole getElectricPole(TileEntity tile) {
		if(tile instanceof ITileElectricPole){
			if(((ITileElectricPole) tile).getMainTile() == null)return null;
			if(((ITileElectricPole) tile).getMainTile() == tile){
				return ((ITileElectricPole) tile).getPoleConnection();
			}else{
				return ((ITileElectricPole) tile).getMainTile().getPoleConnection();
			}
		}
		return null;
	}

	public static IOpticFiber getOpticFiber(TileEntity tile, MgDirection dir) {
		if(tile instanceof TileMultipart){
			for(TMultiPart p : ((TileMultipart) tile).jPartList()){
				if(p instanceof IOpticFiber){
					return (IOpticFiber) p;
				}
			}
		}
		return null;
	}
}
