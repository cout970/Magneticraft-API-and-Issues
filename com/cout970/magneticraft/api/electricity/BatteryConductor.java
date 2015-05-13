package com.cout970.magneticraft.api.electricity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BatteryConductor extends Conductor{

	public int Storage;
	public int maxStorage;
	public double min,max;

	public BatteryConductor(TileEntity tile, double resist, int storage, double min, double max) {
		super(tile, resist);
		maxStorage = storage;
		this.min = min;
		this.max = max;
	}

	public void iterate(){
		super.iterate();
		if (getVoltage() > max && Storage < maxStorage){
			int change;
			change = (int) Math.min((getVoltage() - max)*10, 200);
			change = Math.min(change, maxStorage - Storage);
			drainPower((double)(change * 100));
			Storage += change;
		}else if(getVoltage() < min && Storage > 0){
			int change;
			change = (int) Math.min((min - getVoltage())*10, 200);
			change = Math.min(change, Storage);
			applyPower((double)(change * 100));
			Storage -= change;
		}
	}

	@Override
	public int getStorage() {
		return Storage;
	}

	@Override
	public int getMaxStorage() {
		return maxStorage;
	}

	@Override
	public void setStorage(int charge) {
		Storage = charge;
	}
	
	@Override
	public void applyCharge(int charge) {
		Storage += charge;
		if(Storage > maxStorage)
			Storage = maxStorage;
	}

	@Override
	public void drainCharge(int charge){
		Storage -= charge;
		if(Storage < 0)Storage = 0;
	}

	@Override
	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		nbt.setInteger("Storage", Storage);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		super.load(nbt);
		Storage = nbt.getInteger("Storage");
	}	
}
