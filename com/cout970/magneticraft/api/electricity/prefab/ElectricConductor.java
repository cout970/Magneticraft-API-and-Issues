package com.cout970.magneticraft.api.electricity.prefab;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ElectricConductor implements IElectricConductor{

	protected List<IIndexedConnection> con = new ArrayList<IIndexedConnection>(); //connexions between conductors
	protected boolean connected = false;	//true if recache method was called.
	protected TileEntity tile; 	//the tileEntity with this conductor, used to get the coords of this conductor, and the world
	protected double V; 			//the voltage difference between this conductor an the grownd
	protected double I; 			//the current flowing in the conductor, aka the charge moved.
	protected double R; 			//the electric resistance of the conductor
	protected int tier; 			//the tier of the conductor, 0 low voltage, 2 medium voltage, 4 high voltage
	protected long lastTick;		//this var save the world.getWorldTime() to not repeat the method computeVoltage
	protected double Iabs;			//the sum of all currents currents flowing on this conductor
	protected double Itot;		//the value of Iabs in the last tick
	protected double[] currents;	//the different currents flowing on this conductor
	
	
	public ElectricConductor(TileEntity tile, double resist){
		this(tile,0,resist);
	}
	
	public ElectricConductor(TileEntity tile, int tier, double resist){
		this.tile = tile;
		R = resist;
		this.tier = tier;
	}
	
	public ElectricConductor(TileEntity tile) {
		this(tile, ElectricConstants.RESISTANCE_COPPER_LOW);
	}

	@Override
	public TileEntity getParent() {
		return tile;
	}

	
	@Override
	/**
	 * if in this tick the method computeVoltage was not called, this method will call computeVoltage
	 * return the voltage on this conductor
	 */
	public double getVoltage() {
		long worldTime = this.getParent().getWorldObj().getTotalWorldTime();
		if ((worldTime & 65535L) == (long)this.lastTick){
            return this.V;
        }else{
        	 this.lastTick = (int)(worldTime & 65535L);
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
			for(VecInt f : getValidConnections()){//search for other conductors
				
				TileEntity target = MgUtils.getTileEntity(tile, f);
				IElectricConductor[] c = ElectricUtils.getElectricCond(target, f.getOpposite(), getTier());
				IEnergyInterface inter = ElectricUtils.getInterface(target, f.getOpposite(), getTier());
				
				if(c != null){
					for(IElectricConductor e : c){
						if(e == this)continue;
						if(this.isAbleToConnect(e, f) && e.isAbleToConnect(this, f.getOpposite())){
							if(!ElectricUtils.alreadyContains(e.getConnections(), f.getOpposite())){
								con.add(new IndexedConnection(this, f,e,sides));
								sides++;
							}
						}
					}
				}
				if(inter != null && inter.canConnect(f)){
					con.add(new IndexedConnection(this, f, inter,sides));
					sides++;
				}
			}
			if(currents == null){
				currents = new double[sides];
			}else{
				if(currents.length != sides){
					double[] temp = new double[sides];
					for(int i=0;i< Math.min(sides, currents.length);i++){
						temp[i] = currents[i];
					}
					currents = temp;
				}
			}
		}
	}

	@Override
	public void iterate() {
		TileEntity tile = getParent();
		World w = tile.getWorldObj();
		//only calculated on server side
		if(w.isRemote)return;
		tile.markDirty();
		//make sure the method computeVoltage was called
		this.getVoltage();
		for(IIndexedConnection f : con){
			valance(f, currents);
		}
	}
	
	public static void valance(IIndexedConnection f, double[] currents){
		IElectricConductor cond = f.getConductor();
		IEnergyInterface c = f.getEnergyInterface();
		if(cond != null && cond.canFlowPower(f)){
			//the resistance of the connection
			double resistence = (f.getSource().getResistance() + cond.getResistance());
			//the voltage differennce
			double deltaV = f.getSource().getVoltage() - cond.getVoltage();
			//sanity check for infinite current
			if(Double.isNaN(currents[f.getIndex()]))currents[f.getIndex()] = 0;
			//the extra current from the last tick
			double current = currents[f.getIndex()];
			// (V - I*R) I*R is the voltage difference that this conductor should have using the ohm's law, and V the real one
			//vDiff is the voltage difference bvetween the current voltager difference and the proper voltage difference using the ohm's law
			double vDiff = (deltaV - current * resistence);
			//make sure the vDiff is not in the incorrect direction when the resistance is too big
			vDiff = Math.min(vDiff, Math.abs(deltaV));
			vDiff = Math.max(vDiff, -Math.abs(deltaV));
			// add to the next tick current an extra to get the proper voltage difference on the two conductors
			currents[f.getIndex()] += (vDiff * f.getSource().getIndScale())/f.getSource().getVoltageMultiplier();	
			// to the extra current add the current generated by the voltage difference
			current += (deltaV * f.getSource().getCondParallel())/(f.getSource().getVoltageMultiplier());
			//moves the charge
			f.getSource().applyCurrent(-current);
			cond.applyCurrent(current);
		}
		if(c != null){
			if(f.getSource().getVoltage() > ElectricConstants.ENERGY_INTERFACE_LEVEL && c.canAcceptEnergy(f)){
				double watt = Math.min(c.getMaxFlow(), (f.getSource().getVoltage()-ElectricConstants.ENERGY_INTERFACE_LEVEL)*ElectricConstants.CONVERSION_SPEED);
				if(watt > 0)
					f.getSource().drainPower(c.applyEnergy(watt));
			}
		}
	}

	public int getTier() {
		return tier;
	}

	public double getIndScale() {
		return 0.07D;
	}

	@Override
	public void computeVoltage() {
		V += 0.05d * I * getInvCapacity();
		if(V < 0 || Double.isNaN(V))V = 0;
		if(V > ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier()*2)V = ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier()*2;
		I = 0;
		Itot = Iabs*0.5;
		Iabs = 0;
	}

	public double getInvCapacity(){
		return getVoltageMultiplier()*EnergyConversor.RFtoW(0.1D);
	}

	@Override
	public double getIntensity() {
		return Itot;
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

	public static final double Q1 = 0.1D, Q2 = 20.0D;
	
	@Override
	public void drainPower(double power) {
		power = power * getVoltageMultiplier();
		double square = V * V - Q1 * power;
        double draining = (square < 0.0D ? 0.0D : Math.sqrt(square)) - V;
        applyCurrent(Q2 * draining);
	}
	
	@Override
	public void applyPower(double power) {
		power = power * getVoltageMultiplier();
		double applying = Math.sqrt(V * V + Q1 * power) - V;
        applyCurrent(Q2 * applying);
    }

	@Override
	public void save(NBTTagCompound nbt) {
		nbt.setDouble("Volts",V);
		nbt.setDouble("Amperes",I);
		nbt.setDouble("Ohms",R);
		nbt.setDouble("Iabs",Iabs);
		nbt.setDouble("Itot",Itot);
		nbt.setInteger("Vtier", tier);
		nbt.setLong("lastTick", lastTick);
		if(currents != null){
			nbt.setByte("currents", (byte) currents.length);
			for(int j=0;j<currents.length;j++){
				nbt.setDouble("flow"+j, currents[j]);
			}
		}
	}

	@Override
	public void load(NBTTagCompound nbt) {
		V = nbt.getDouble("Volts");
		I = nbt.getDouble("Amperes");
		R = nbt.getDouble("Ohms");
		Iabs = nbt.getDouble("Iabs");
		Itot = nbt.getDouble("Itot");
		tier = nbt.getInteger("Vtier");
		lastTick = nbt.getLong("lastTick");
		int i = nbt.getByte("currents");
		if(i != 0){
			currents = new double[i];
			for(int j=0;j<i;j++){
				currents[j] = nbt.getDouble("flow"+j);
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
	public IIndexedConnection[] getConnections() {
		if(con == null)return null;
		return con.toArray(new IIndexedConnection[con.size()]);
	}

	@Override
	public VecInt[] getValidConnections() {
		return VecIntUtil.FORGE_DIRECTIONS;
	}

	@Override
	public boolean isAbleToConnect(IConnectable c, VecInt d) {
		return true;
	}

	@Override
	public void applyCharge(int charge) {}

	@Override
	public double getVoltageMultiplier() {
		return tier == 0 ? 1 : tier == 1 ? 10 : tier == 2 ? 100 : tier == 3 ? 1000 : tier == 4 ? 10000 : tier == 5 ? 100000 : tier == 6 ? 1000000 : 10000000;
	}

	@Override
	public ConnectionClass getConnectionClass(VecInt v) {
		return ConnectionClass.FULL_BLOCK;
	}

	@Override
	public boolean canFlowPower(IIndexedConnection con) {
		return true;
	}
}
