package com.cout970.magneticraft.api.electricity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.util.Log;

public class Conductor implements IElectricConductor{

	public List<IndexedConnexion> con = new ArrayList<IndexedConnexion>();
	public boolean connected = false;
	public TileEntity tile;
	public double V;
	public double I;
	public double R;
	public int Vtier;
	public long lastTick;
	public double Iabs;
	private double[] flow;
	
	public Conductor(TileEntity tile, double resist){
		this(tile,0,resist);
	}
	
	public Conductor(TileEntity tile, int tier, double resist){
		this.tile = tile;
		R = resist;
		Vtier = tier;
	}
	
	public Conductor(TileEntity tile) {
		this(tile, ElectricConstants.RESISTANCE_COPPER_2X2);
	}

	@Override
	public TileEntity getParent() {
		return tile;
	}

	@Override
	public double getVoltage() {
		
		long WorldTime = this.getParent().getWorldObj().getWorldTime();
		if ((WorldTime & 65535L) == (long)this.lastTick){
            return this.V;
        }else{
        	 this.lastTick = (int)(WorldTime & 65535L);
             this.computeVoltage();
             return this.V;
        }
	}

	@Override
	public void recache(){
		if(!connected){
			connected = true;
			con.clear();
			int sides = 0;
			for(VecInt f : getValidConnections()){
				
				TileEntity target = MgUtils.getTileEntity(tile, f);
				CableCompound c = MgUtils.getConductor(target, f.getOpposite(), getTier());
				ICompatibilityInterface inter = MgUtils.getInterface(target, f.getOpposite(), getTier());
				if(c != null){
					for(IElectricConductor e : c.list()){
						if(e == this)continue;
						if(this.isAbleToConnect(e, f) && e.isAbleToConnect(this, f.getOpposite())){
							if(!MgUtils.alreadyContains(e.getConnexions(), f.getOpposite())){
								con.add(new IndexedConnexion(f,e,sides));
								sides++;
							}
						}
					}
				}
				if(inter != null){
					con.add(new IndexedConnexion(f, inter,sides));
					sides++;
				}
			}
			if(flow == null){
				flow = new double[sides];
			}else{
				if(flow.length != sides){
					double[] temp = new double[sides];
					for(int i=0;i< Math.min(sides, flow.length);i++){
						temp[i] = flow[i];
					}
					flow = temp;
				}
			}
		}
	}

	@Override
	public void iterate() {
		TileEntity tile = getParent();
		World w = tile.getWorldObj();
		if(w.isRemote)return;
		tile.markDirty();
		this.getVoltage();
		for(IndexedConnexion f : con){
			IElectricConductor cond = f.cond;
			ICompatibilityInterface c = f.inter;
			if(cond != null){
				double resistence = (this.getResistance() + cond.getResistance());
				double difference = this.V - cond.getVoltage();
				double change = flow[f.side];
				flow[f.side] += ((difference - change * resistence) * getIndScale())/getVoltageMultiplier();
				change += (difference * getCondParallel())/getVoltageMultiplier();
				this.applyCurrent(-change);
				cond.applyCurrent(change);
			}
			if(c != null){
				if(c.getBehavior() != null){
					if(V > ElectricConstants.COMPT_DRAIN && c.getBehavior().canAccept()){
						double kw = Math.min(c.getMaxFlow(), V-ElectricConstants.COMPT_DRAIN);
						drainPower(c.applyWatts(kw*ElectricConstants.CONVERSION_SPEED));
					}
					if(V < ElectricConstants.COMPT_APPLY && c.getBehavior().canExtract()){
						double kw = Math.min(c.getMaxFlow(), ElectricConstants.COMPT_APPLY-V);
						applyPower(c.drainWatts(kw*ElectricConstants.CONVERSION_SPEED));
					}
				}
			}
		}
	}

	public int getTier() {
		return Vtier;
	}

	public double getIndScale() {
		return 0.05D;
	}

	@Override
	public void computeVoltage() {
		V += 0.05d * I * getVoltageMultiplier();
		if(V < 0)V = 0;
		I = 0;
		Iabs = 0;
	}

	@Override
	public double getIntensity() {
		return Iabs;
	}

	@Override
	public double getResistance() {
		return R;
	}

	@Override
	public double getCondParallel() {
		return 0.5D;
	}

	@Override
	public void applyCurrent(double amps) {
		getVoltage();
		I += amps;
		Iabs += Math.abs(amps/getVoltageMultiplier());
	}

	public static final double Q1 = 0.1D,Q2 = 20.0D;
	
	@Override
	public void drainPower(double power) {
		power = power * getVoltageMultiplier();
		//sqrt(V^2-(power))-V
		double square = this.V * this.V - Q1 * power;
        double draining = square < 0.0D ? 0.0D : Math.sqrt(square) - this.V;
        this.applyCurrent(Q2 * draining);
	}
	
	@Override
	public void applyPower(double power) {
		power = power * getVoltageMultiplier();
		//sqrt(V^2+(power))-V
		double square = Math.sqrt(this.V * this.V + Q1 * power) - this.V;
        this.applyCurrent(Q2 * square);
	}

	@Override
	public void save(NBTTagCompound nbt) {
		nbt.setDouble("Volts",V);
		nbt.setDouble("Amperes",I);
		nbt.setDouble("Ohms",R);
		nbt.setInteger("Vtier", Vtier);
		nbt.setLong("lastTick", lastTick);
		if(flow != null){
			nbt.setByte("currents", (byte) flow.length);
			for(int j=0;j<flow.length;j++){
				nbt.setDouble("flow"+j, flow[j]);
			}
		}
	}

	@Override
	public void load(NBTTagCompound nbt) {
		V = nbt.getDouble("Volts");
		I = nbt.getDouble("Amperes");
//		R = nbt.getDouble("Ohms");
		Vtier = nbt.getInteger("Vtier");
		lastTick = nbt.getLong("lastTick");
		int i = nbt.getByte("currents");
		if(i != 0){
			flow = new double[i];
			for(int j=0;j<i;j++){
				flow[j] = nbt.getDouble("flow"+j);
			}
		}
	}


	@Override
	public void setResistence(double d) {
		R = d;
	}

	@Override
	public void setVoltage(double d) {
		V = d;
	}

	@Override
	public int getStorage() {
		return 0;
	}

	@Override
	public int getMaxStorage() {
		return 0;
	}

	@Override
	public void setStorage(int charge){}

	@Override
	public void drainCharge(int charge) {}

	@Override
	public void disconect() {
		connected = false;
	}

	@Override
	public boolean isConected() {
		return connected;
	}

	@Override
	public IndexedConnexion[] getConnexions() {
		if(con == null)return null;
		IndexedConnexion[] a = new IndexedConnexion[0];
		return con.toArray(a);
	}

	@Override
	public VecInt[] getValidConnections() {
		return VecIntUtil.FORGE_DIRECTIONS;
	}

	@Override
	public boolean isAbleToConnect(IElectricConductor c, VecInt d) {
		return true;
	}

	@Override
	public void applyCharge(int charge) {}

	@Override
	public double getVoltageMultiplier() {
		return Vtier == 0 ? 1 : Vtier == 1 ? 10 : Vtier == 2 ? 100 : Vtier == 3 ? 1000 : Vtier == 4 ? 10000 : Vtier == 5 ? 100000 : Vtier == 6 ? 1000000 : 10000000;
	}

	@Override
	public ConnectionClass getConnectionClass(VecInt v) {
		return ConnectionClass.FULL_BLOCK;
	}
}
