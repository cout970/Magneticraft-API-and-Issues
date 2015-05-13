package com.cout970.magneticraft.api.conveyor;

import com.cout970.magneticraft.api.util.VecDouble;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBox {
	
	private ItemStack content;
//	private double x,y,z;
	private int position;
	private boolean isOnLeft;

	
	public ItemBox(ItemStack it) {
		content = it;
	}
	
//	public void setPos(double x, double y, double z){
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
//	
//	public void move(VecDouble dir){
//		x += dir.getX();
//		y += dir.getY();
//		z += dir.getZ();
//	}
//	
//	public double getX() {
//		return x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public double getZ() {
//		return z;
//	}	

	public ItemStack getContent() {
		return content;
	}

	public void setContent(ItemStack content) {
		this.content = content;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int positions) {
		this.position = positions;
	}

	public boolean isOnLeft() {
		return isOnLeft;
	}

	public void setOnLeft(boolean isLeft) {
		this.isOnLeft = isLeft;
	}

	public void save(NBTTagCompound t) {
		getContent().writeToNBT(t);
		t.setInteger("Pos", getPosition());
//		t.setDouble("pX", getX());
//		t.setDouble("pY", getY());
//		t.setDouble("pZ", getZ());
		t.setBoolean("Left", isOnLeft());
	}

	public void load(NBTTagCompound t) {
		setContent(ItemStack.loadItemStackFromNBT(t));
		setPosition(t.getInteger("Pos"));
//		setPos(t.getDouble("pX"), t.getDouble("pY"), t.getDouble("pZ"));
		setOnLeft(t.getBoolean("Left"));
	}
}
