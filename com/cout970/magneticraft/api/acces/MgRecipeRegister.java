package com.cout970.magneticraft.api.acces;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MgRecipeRegister {

	public static List<RecipeRefinery> refinery = new ArrayList<RecipeRefinery>();
	public static List<RecipeCrusher> crusher = new ArrayList<RecipeCrusher>();
	public static List<RecipeGrinder> grinder = new ArrayList<RecipeGrinder>();
	
	public static boolean registerCrusherRecipe(ItemStack in, ItemStack out0, ItemStack out1, int prob1, ItemStack out2, int prob2){
		if(in == null || out0 == null)return false;
		RecipeCrusher a = new RecipeCrusher(in,out0,out1,prob1,out2,prob2);
		if(!crusher.contains(a)){
			crusher.add(a);
			return true;
		}
		return false;
	}
	
	public static boolean registerGrinderRecipe(ItemStack in, ItemStack out0, ItemStack out1, float prob1, ItemStack out2, float prob2){
		if(in == null || out0 == null)return false;
		RecipeGrinder a = new RecipeGrinder(in,out0,out1,prob1,out2,prob2);
		if(!grinder.contains(a)){
			grinder.add(a);
			return true;
		}
		return false;
	}
	
	public static boolean registerRefineryRecipe(FluidStack in, FluidStack a, FluidStack b, FluidStack c){
		if(in == null)return false;
		RecipeRefinery recipe = new RecipeRefinery(in, a, b, c);
		if(!refinery.contains(recipe)){
			refinery.add(recipe);
			return true;
		}
		return false;
	}
}
