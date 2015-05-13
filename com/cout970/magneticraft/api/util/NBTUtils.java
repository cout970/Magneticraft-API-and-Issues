package com.cout970.magneticraft.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtils {

	public static void sanityCheck(ItemStack i){
		if(i.stackTagCompound == null){
			i.stackTagCompound = new NBTTagCompound();
		}
	}

	public static double getDouble(String string, ItemStack stack) {
		sanityCheck(stack);
		return stack.stackTagCompound.getDouble(string);
	}

	public static void setDouble(String string, ItemStack stack, double n) {
		sanityCheck(stack);
		stack.stackTagCompound.setDouble(string, n);
	}

	public static int getInteger(String string, ItemStack stack) {
		sanityCheck(stack);
		return stack.stackTagCompound.getInteger(string);
	}
	
	public static void setInteger(String string, ItemStack stack, int n) {
		sanityCheck(stack);
		stack.stackTagCompound.setInteger(string, n);
	}
}
