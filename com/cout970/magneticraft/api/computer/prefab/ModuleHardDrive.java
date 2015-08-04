package com.cout970.magneticraft.api.computer.prefab;

import com.cout970.magneticraft.api.computer.IStorageDevice;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ModuleHardDrive extends ModuleDisckDrive{
	
	
	public ModuleHardDrive(TileEntity t) {
		super(t);
	}

	@Override
	public String getName() {
		return "HardDrive";
	}
	
	@Override
	public void load(NBTTagCompound nbt) {
		diskBuffer = nbt.getByteArray("Hard_Drive_Buffer");
		sector = nbt.getInteger("Hard_Drive_Sector");
		address = nbt.getInteger("Hard_Drive_Address");
		regAction = nbt.getInteger("Hard_Drive_Action");
	}

	@Override
	public void save(NBTTagCompound nbt) {
		nbt.setByteArray("Hard_Drive_Buffer", getRawBuffer());
		nbt.setInteger("Hard_Drive_Sector", sector);
		nbt.setInteger("Hard_Drive_Address", address);
		nbt.setInteger("Hard_Drive_Action", regAction);
	}
	
	@Override
	public ItemStack getDisk() {
		ItemStack i = disk;
		if(i != null && i.getItem() != null){
			if(i.getItem() instanceof IStorageDevice && ((IStorageDevice) i.getItem()).isHardDrive(i)){
				return i;
			}
		}
		return null;
	}

	@Override
	public boolean insertDisk(ItemStack i) {
		if(i == null){
			disk = null;
			return true;
		}else if(i.getItem() != null && i.getItem() instanceof IStorageDevice && ((IStorageDevice) i.getItem()).isHardDrive(i)){
			if(disk == null){
				disk = i;
				return true;
			}
		}
		return false;
	}
}
