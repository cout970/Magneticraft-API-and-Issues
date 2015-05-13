package com.cout970.magneticraft.api.heat;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;

public class HeatConductor implements IHeatConductor{

	private TileEntity parent;
	private double temperature = 25;
	private double mass;
	private double thermalResistance;
	private double maxHeat;
	private double[] flow = new double[6];
	
	public HeatConductor(TileEntity p, double max,double mass){
		parent = p;
		maxHeat = max;
		this.mass = mass;
		thermalResistance = 0.5d;
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
			w.setBlock(t.xCoord, t.yCoord, t.zCoord, Blocks.lava);
			w.notifyBlockOfNeighborChange(t.xCoord, t.yCoord, t.zCoord, Blocks.lava);
		}
		for(MgDirection d : MgDirection.values()){
			TileEntity tile = MgUtils.getTileEntity(t, d);
			if(tile == null)continue;
			IHeatConductor h = MgUtils.getHeatCond(tile, d.getVecInt());
			if(h != null){
				double diff = this.temperature-h.getTemperature();
				double resistance = this.thermalResistance+h.getResistance();
				double k = 50D,s= 0.007;
				double change = flow[d.ordinal()];
				flow[d.ordinal()] += (diff- change*resistance*0.01)*s;
				change += (diff * k)/resistance;
				this.applyCalories((int)-change);
				h.applyCalories((int)change);
			}
		}
	}

	public double getResistance() {
		return thermalResistance;
	}

	@Override
	public void applyCalories(double j) {
		temperature += j/(getMass());
	}

	@Override
	public void drainCalories(double j) {
		temperature -= j/(getMass());
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

}
