package com.cout970.magneticraft.api.heat;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

/**
 * 
 * @author Cout970
 *
 */
public class HeatConductor implements IHeatConductor{

	public static final double SPECIFIC_HEAT = 0.2D;
	private TileEntity parent;
	private double temperature = 25;//temperature in celsius degrees
	private double mass;
	private double thermalResistance;
	private double maxHeat;
	
	public HeatConductor(TileEntity p, double max){
		this(p, max, 1000, 0.5D);
	}
	
	public HeatConductor(TileEntity p, double max,double mass){
		this(p, max, mass, 0.5D);
	}
	
	public HeatConductor(TileEntity p, double max,double mass, double res){
		parent = p;
		maxHeat = max;
		this.mass = mass;
		thermalResistance = res;
	}
	
	@Override
	public double getMaxTemp() {
		return maxHeat;
	}

	@Override
	public double getTemperature() {
		if(Double.isNaN(temperature)){
			temperature = 25;
		}
		if(temperature < -273.15){
			temperature = -273.15;
		}
		return temperature;
	}

	@Override
	public TileEntity getParent() {
		return parent;
	}

	@Override
	public void iterate() {
		TileEntity t = getParent();
		World w = t.getWorldObj();
		if(w.isRemote)return;
		if(this.getTemperature() >= getMaxTemp()){
			onBlockOverHeat();
		}
		for(MgDirection d : getValidConnections()){
			TileEntity tile = MgUtils.getTileEntity(t, d);
			if(tile == null)continue;
			CompoundHeatCables comp = MgUtils.getHeatCond(tile, d.toVecInt());
			if(comp != null){
				for(IHeatConductor h : comp.list()){
					if(h == null)continue;
					if(h.isAbleToconnect(this, d.opposite().toVecInt()) && this.isAbleToconnect(h, d.toVecInt())){
						double diff = this.getTemperature()-h.getTemperature();
						double resistance = this.getResistance()+h.getResistance();
						double change = ((diff * 0.5D)/resistance)*EnergyConversor.FUELtoCALORIES(1);
						drainCalories(change);
						h.applyCalories(change);
					}
				}
			}
		}
	}

	public double getResistance() {
		return thermalResistance;
	}

	@Override
	public void applyCalories(double j) {
		temperature += j/(getMass())*SPECIFIC_HEAT;
	}

	@Override
	public void drainCalories(double j) {
		temperature -= j/(getMass())*SPECIFIC_HEAT;
	}

	@Override
	public void save(NBTTagCompound nbt) {
		nbt.setDouble("Heat", temperature);
		nbt.setDouble("Mass", mass);
		nbt.setDouble("MaxHeat", maxHeat);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		temperature = nbt.getDouble("Heat");
		mass = nbt.getDouble("Mass");
		maxHeat = nbt.getDouble("MaxHeat");
	}

	@Override
	public void setTemperature(double h) {
		temperature = h;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public void onBlockOverHeat() {
		TileEntity t = getParent();
		World w = t.getWorldObj();
		w.setBlock(t.xCoord, t.yCoord, t.zCoord, Blocks.lava);
		w.notifyBlockOfNeighborChange(t.xCoord, t.yCoord, t.zCoord, Blocks.lava);
	}

	@Override
	public MgDirection[] getValidConnections() {
		return MgDirection.values();
	}

	@Override
	public boolean isAbleToconnect(IHeatConductor cond, VecInt dir) {
		return true;
	}
}
