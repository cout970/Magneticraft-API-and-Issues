package com.cout970.magneticraft.api.computer;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleMemoryController extends IHardwareModule{

	public int readWord(int pos);
	public byte readByte(int pos);
	
	public void writeByte(int pos, byte dato);
	public void writeWord(int pos, int dato);
	
	public boolean isLittleEndian();
	public void setLittelEndian(boolean little);
	
	public int getMemorySize();
	public void clear();
	
	public void loadMemory(NBTTagCompound nbt);
	public void saveMemory(NBTTagCompound nbt);
	
	public IComputer getComputer();
	public void setComputer(IComputer c);
}
