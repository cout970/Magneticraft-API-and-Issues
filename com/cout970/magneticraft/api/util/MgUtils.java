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

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.ICompatibilityInterface;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.IElectricMultiPart;
import com.cout970.magneticraft.api.electricity.IndexedConnexion;
import com.cout970.magneticraft.api.electricity.compact.InteractionHelper;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;

public class MgUtils {
	
	public static IHeatConductor getHeatCond(TileEntity tile, VecInt d) {
		if(tile instanceof IHeatTile)return ((IHeatTile) tile).getHeatCond(d.getOpposite());
		return null;
	}

	public static boolean alreadyContains(IndexedConnexion[] con, VecInt opp) {
		if(con == null)return false;
		if(opp == null)return false;
		for(IndexedConnexion i : con)
			if(opp == i.con)return true;
		return false;
	}

	public static TileEntity getTileEntity(TileEntity tile, VecInt d) {
		if(tile == null)return null;
		return tile.getWorldObj().getTileEntity(tile.xCoord+d.getX(), tile.yCoord+d.getY(), tile.zCoord+d.getZ());
	}

	public static CableCompound getConductor(TileEntity tile, VecInt f, int tier) {
		if(tile instanceof TileMultipart){
			CableCompound cab = null;
			for(TMultiPart m : ((TileMultipart) tile).jPartList()){
				if(m instanceof IElectricMultiPart && ((IElectricMultiPart) m).getCond(tier) != null){
					if(cab == null){
						cab = new CableCompound(((IElectricMultiPart) m).getCond(tier));
					}else{
						cab.add(((IElectricMultiPart) m).getCond(tier));
					}
				}
			}
			return cab;
		}
		if(tile instanceof IElectricTile)return ((IElectricTile) tile).getConds(f,tier);
		return null;
	}
	
	public static ICompatibilityInterface getInterface(TileEntity t,VecInt i,int tier){
		return InteractionHelper.processTile(t,i, tier);
	}

	public static boolean isConductor(TileEntity tile, int tier){
		return getConductor(tile, VecInt.NULL_VECTOR, tier) != null;
	}

	public static TileEntity getTileEntity(TileEntity tile, MgDirection d){
		return tile.getWorldObj().getTileEntity(tile.xCoord+d.getOffsetX(), tile.yCoord+d.getOffsetY(), tile.zCoord+d.getOffsetZ());
	}

	public static List<TileEntity> getNeig(TileEntity t) {
		List<TileEntity> list = new ArrayList<TileEntity>();
		for(MgDirection d : MgDirection.values()){
			TileEntity f = getTileEntity(t, d);
			if(f != null)list.add(f);
		}
		return list;
	}

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

	public static boolean areEcuals(FluidStack a, FluidStack b) {
		if(a == null && b == null)return true;
		if(a == null || b == null)return false;
		if(FluidRegistry.getFluidName(a).equalsIgnoreCase(FluidRegistry.getFluidName(b)))return true;
		return false;
	}
	
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
}
