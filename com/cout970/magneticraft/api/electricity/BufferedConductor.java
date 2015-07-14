package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.EnergyConversor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public class BufferedConductor extends ElectricConductor{

	public int storage;// in Joules
	public int maxStorage;
	public double min,max;

	public BufferedConductor(TileEntity tile, double resist, int storage, double min, double max) {
		super(tile, resist);
		maxStorage = storage;
		this.min = min;
		this.max = max;
	}

	public void iterate(){
		super.iterate();
		if (getVoltage() > max && storage < maxStorage){
			int change;
			change = (int) Math.min((getVoltage() - max)*80, EnergyConversor.RFtoW(100));
			change = Math.min(change, maxStorage - storage);
			drainPower(change);
			storage += change;
		}else if(getVoltage() < min && storage > 0){
			int change;
			change = (int) Math.min((min - getVoltage())*80, EnergyConversor.RFtoW(100));
			change = Math.min(change, storage);
			applyPower(change);
			storage -= change;
		}
	}

	@Override
	public int getStorage() {
		return storage;
	}

	@Override
	public int getMaxStorage() {
		return maxStorage;
	}

	@Override
	public void setStorage(int charge) {
		storage = charge;
	}
	
	@Override
	public void applyCharge(int charge) {
		storage += charge;
		if(storage > maxStorage)
			storage = maxStorage;
	}

	@Override
	public void drainCharge(int charge){
		storage -= charge;
		if(storage < 0)storage = 0;
	}

	@Override
	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		nbt.setInteger("Storage", storage);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		super.load(nbt);
		storage = nbt.getInteger("Storage");
	}	
}
