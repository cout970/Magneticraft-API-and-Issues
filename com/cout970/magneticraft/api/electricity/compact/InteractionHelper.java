package com.cout970.magneticraft.api.electricity.compact;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.tile.IEnergyStorage;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;
/**
 * 
 * @author Cout970
 *
 */
public class InteractionHelper {

	public static IEnergyInterface processTile(TileEntity tile, VecInt f, int tier) {
		if(tier == 0){
			if(tile instanceof IElectricGrid){
				if(((IElectricGrid) tile).getChargeHandler().getType() == ConnectType.BLOCK)
					return getElectricalGrid((IElectricGrid) tile);
			}
			if(tile instanceof IEnergyHandler && f.toMgDirection() != null){
				if(((IEnergyHandler) tile).canConnectEnergy(f.toMgDirection().toForgeDir())){
					return getEnergyHandler((IEnergyHandler)tile,f.toMgDirection());
				}
			}
			if(tile instanceof IEnergySink && f.toMgDirection() != null){
				if(((IEnergySink)tile).acceptsEnergyFrom(null, f.toMgDirection().toForgeDir())){
				return getEnergySink((IEnergySink) tile, f.toMgDirection());
				}
			}
		}
		return null;
	}

	public static IEnergyInterface getEnergySink(IEnergySink tile, MgDirection dir) {
		return new EU_EnergyInterfaceSink(tile, dir);
	}

	public static IEnergyInterface getElectricalGrid(IElectricGrid g){
		return new RailcraftChargeEnergyInteface(g);
	}

	public static IEnergyInterface getEnergyHandler(IEnergyHandler tile, MgDirection dir) {
		return new RF_EnergyInterface(tile, dir);
	}
}
