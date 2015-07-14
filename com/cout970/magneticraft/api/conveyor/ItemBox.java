package com.cout970.magneticraft.api.conveyor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author Cout970
 *
 */
public class ItemBox {
	
	private ItemStack content;
	private int position;
	private boolean isOnLeft;
	public long lastTick;
	
	public ItemBox(ItemStack it) {
		content = it;
	}

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
		t.setBoolean("Left", isOnLeft());
	}

	public void load(NBTTagCompound t) {
		setContent(ItemStack.loadItemStackFromNBT(t));
		setPosition(t.getInteger("Pos"));
		setOnLeft(t.getBoolean("Left"));
	}
}
