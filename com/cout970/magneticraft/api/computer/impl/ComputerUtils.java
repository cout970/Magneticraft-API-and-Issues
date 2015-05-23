package com.cout970.magneticraft.api.computer.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import com.cout970.magneticraft.api.computer.IBusConnectable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

public class ComputerUtils {

	public static int getBitsFromInt(int inst, int start, int end,  boolean signed) {
		if(start > end) {
			int temp = end;
			end = start;
			start = temp;
		}
		int max = 0xFFFFFFFF;
		int mask = (max >>> (31-end)) & (max << start);
		inst = inst & mask;
		if(signed) {
			inst = inst << (31 - end);
			return inst >> (start + (31-end));
		}
		return inst >>> start;
	}

	public static InputStream getInputStream(String file) {
		return ComputerUtils.class.getResourceAsStream("/assets/magneticraft/cpu/"+file);
	}
	
	public static File getFileFromItemStack(ItemStack i){
		NBTTagCompound nbt = i.getTagCompound();
		if(nbt == null)return null;
		if(nbt.hasKey("SerialNumber")){
			return new File(getSaveDirectory(), "disk_"+nbt.getString("SerialNumber")+".img");
		}else{
			String aux = null;
			while(true){
				aux = genSerialNumber();
				File f = new File(getSaveDirectory(), "disk_"+aux+".img");
				try{
					if(f.createNewFile()){
						nbt.setString("SerialNumber", aux);
						return f;
					}
				}catch(IOException e){
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	private static String genSerialNumber() {
		String serial = "";
		Random r = new Random();
		for(int i = 0; i< 16; i++){
			serial += Integer.toHexString(r.nextInt(16));
		}
		return null;
	}

	public static File getSaveDirectory(){
		File f = new File(DimensionManager.getCurrentSaveRootDirectory(), "Magneticraft");
		f.mkdirs();
		return f;
	}

	public static IBusConnectable getBusByAddress(List<IBusConnectable> con, int i) {
		for(int j = 0; j < con.size() ;j++){
			if(con.get(j).getAddress() == i)return con.get(j);
		}
		return null;
	}
}
