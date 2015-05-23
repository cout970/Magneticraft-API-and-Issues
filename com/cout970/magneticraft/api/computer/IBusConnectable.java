package com.cout970.magneticraft.api.computer;

import com.cout970.magneticraft.api.util.MgDirection;

public interface IBusConnectable {

	public int getAddress();
	public void setAddress(int address);
	
	public boolean isActive();
	
	public int readByte(int pointer);
	public void writeByte(int pointer, int data);
}
