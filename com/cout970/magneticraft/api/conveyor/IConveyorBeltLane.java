package com.cout970.magneticraft.api.conveyor;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface IConveyorBeltLane {

	public IConveyorBelt getConveyorBelt();
	
	public List<IItemBox> getItemBoxes();
	
	public IHitBoxArray getHitBoxes();
	
	public boolean isOnLeft();
	
	public void setHitBoxSpace(int pos, boolean value);
	public void setHitBoxSpaceExtern(TileEntity tile,int pos, boolean value);
	
	public boolean hasHitBoxSpace(int pos);
	public boolean hasHitBoxSpaceExtern(TileEntity tile, int pos);
	
	public void save(NBTTagCompound nbt);
	public void load(NBTTagCompound nbt);
}
