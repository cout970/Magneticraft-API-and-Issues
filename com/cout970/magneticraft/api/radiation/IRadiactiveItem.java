package com.cout970.magneticraft.api.radiation;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Cout970
 */
public interface IRadiactiveItem {

    public static final String NBT_GRAMS_NAME = "gramos";//spanish word fro grams

    public double getGrams(ItemStack itemStack);//amount of radioactive nuclei, correspond to the durability

    public void setGrams(ItemStack itemStack, double n);//set durability

    public double getDecayConstant(ItemStack itemStack);//decay rate

    public double getEnergyPerFision(ItemStack itemStack);

    public ResourceLocation getResourceLocation();//for reactor representation

}
