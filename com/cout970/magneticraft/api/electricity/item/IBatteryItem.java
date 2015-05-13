package com.cout970.magneticraft.api.electricity.item;

import net.minecraft.item.ItemStack;

public interface IBatteryItem {

	public int getCharge(ItemStack it);

	//energy should be positive
	public void discharge(ItemStack it, int energy);

	public int charge(ItemStack stack, int energy);

	public int getMaxCharge();
}