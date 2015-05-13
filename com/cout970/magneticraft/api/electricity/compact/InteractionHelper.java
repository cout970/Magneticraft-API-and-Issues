package com.cout970.magneticraft.api.electricity.compact;

import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

import com.cout970.magneticraft.api.electricity.ICompatibilityInterface;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class InteractionHelper {

	public static ICompatibilityInterface processTile(TileEntity tile, VecInt f,int tier) {
		if(tier == 0 || tier == -1){
			if(tile instanceof IElectricGrid){
				if(((IElectricGrid) tile).getChargeHandler().getType() == ConnectType.BLOCK)
					return getElectricalGrid((IElectricGrid) tile);
			}
			if(tile instanceof IEnergyHandler && f.toMgDirection() != null){
				if(((IEnergyHandler) tile).canConnectEnergy(f.toMgDirection().getForgeDir())){
					return getEnergyHandler((IEnergyHandler)tile,f.toMgDirection());
				}
			}
		}
		return null;
	}
	
	public static ICompatibilityInterface getElectricalGrid(IElectricGrid g){
		return new RailcraftEnergyInteface(g);
	}

	private static ICompatibilityInterface getEnergyHandler(IEnergyHandler tile,MgDirection dir) {
		return new RFenergyInterface(tile, dir);
	}
}
