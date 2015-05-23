package com.cout970.magneticraft.api.computer.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.computer.IBusConnectable;
import com.cout970.magneticraft.api.computer.IHardDrive;
import com.cout970.magneticraft.api.computer.IModuleDiskDrive;

public class ModuleHardDrive implements IModuleDiskDrive, IBusConnectable{
	
	private ItemStack disk;
	private byte[] diskBuffer;
	private int sector = 0;
	private int address = 3;
	private int regAction = -1;

	@Override
	public byte[] getRawBuffer() {
		if(diskBuffer == null)diskBuffer = new byte[128];
		return diskBuffer;
	}

	@Override
	public int getSector() {
		return sector;
	}

	@Override
	public void setSector(int sector) {
		this.sector = sector;
	}

	@Override
	public void readToBuffer() {
		ItemStack item = getDisk();
		if(item != null){
			IHardDrive storage = (IHardDrive) item.getItem();
			if(storage.getSize(item) >= getSector())return;
			File f = storage.getAsociateFile(item);
			if(f == null)return;
			RandomAccessFile acces = null;
			try {
				acces = new RandomAccessFile(f, "r");
				acces.seek(getSector()*128);
				if(acces.read(diskBuffer) == 128)
					regAction = 0;
				else regAction = -1;
			} catch (IOException e) {
				regAction = -1;
				e.printStackTrace();
			}finally{
				try {
					if(acces != null){
						acces.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void writeToFile() {
		ItemStack item = getDisk();
		if(item != null){
			IHardDrive storage = (IHardDrive) item.getItem();
			if(storage.getSize(item) >= getSector())return;
			File f = storage.getAsociateFile(item);
			if(f == null)return;
			RandomAccessFile acces = null;
			try {
				acces = new RandomAccessFile(f, "rw");
				acces.seek(getSector()*128);
				acces.write(diskBuffer);
				regAction = 0;
			} catch (IOException e) {
				e.printStackTrace();
				regAction = -1;
			}finally{
				try {
					if(acces != null){
						acces.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ItemStack getDisk() {
		ItemStack i = disk;
		if(i != null && i.getItem() != null){
			if(i.getItem() instanceof IHardDrive)return i;
		}
		return null;
	}

	@Override
	public boolean insertDisk(ItemStack i) {
		if(i == null){
			disk = null;
			return true;
		}else if(i.getItem() != null && i.getItem() instanceof IHardDrive){
			if(disk == null){
				disk = i;
				return true;
			}
		}
		return false;
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
	public int getAddress() {
		return address;
	}

	@Override
	public void setAddress(int address) {
		this.address = address;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public int readByte(int pointer) {
		if(pointer < 0)return 0;
		if(pointer < 128)return diskBuffer[pointer];
		if(pointer == 128)return sector & 255;
		if(pointer == 129)return sector >> 8;
		if(pointer == 130)return regAction;
		return 0;
	}

	@Override
	public void writeByte(int pointer, int data) {
		if(pointer < 0)return;
		if(pointer < 128)diskBuffer[pointer] = (byte) data;
		if(pointer == 128)sector = (sector & 65280) | data & 255;
		if(pointer == 129)sector |= (sector & 255) | data << 8;
		if(pointer == 130)regAction = data;
	}

	@Override
	public void iterate() {

		if(regAction != -1 && getDisk() != null){
			IHardDrive drive = (IHardDrive)getDisk().getItem();
			if(regAction == 1){
				//copy in the buffer the disk label
				Arrays.fill(diskBuffer, (byte)0);
				byte[] label;
				try {
					label = drive.getDiskLabel(getDisk()).getBytes("US-ASCII");
					System.arraycopy(label, 0, this.diskBuffer, 0, Math.min(label.length, 128));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				regAction = 0;
			}else if(regAction == 2){
				//set the disk label from the buffer
				int length;
				for(length = 0; diskBuffer[length] != 0 && length < 64; length++){;}
				String label;
				try {
					label = new String(this.diskBuffer, 0, length, "US-ASCII");
					drive.setDiskLabel(getDisk(), label);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				regAction = 0; 
			}else if(regAction == 3){
				//copy in the buffer the serial number
				Arrays.fill(diskBuffer, (byte)0);
				drive.getAsociateFile(getDisk());
				byte[] serial;
				try {
					serial = drive.getSerialNumber(getDisk()).getBytes("US-ASCII");
					System.arraycopy(serial, 0, this.diskBuffer, 0, Math.min(serial.length, 128));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				regAction = 0; 
			}else if(regAction == 4){
				//reads the file and copy it into the buffer
				if(sector > drive.getSize(getDisk())){
					regAction = -1;
					return;
				}
				readToBuffer();
			}else if(regAction == 5){
				//write the contento of the buffer to the file
				if(sector > drive.getSize(getDisk())){
					regAction = -1;
					return;
				}
				writeToFile();
			}else{
				regAction = -1;
			}
		}
	}
}
