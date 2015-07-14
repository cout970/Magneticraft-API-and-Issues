package com.cout970.magneticraft.api.acces;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.util.MgUtils;

/**
 * 
 * @author Cout970
 *
 */
public class RecipeGrinder {

	private ItemStack input;
	private ItemStack output;
	private ItemStack output2;
	private ItemStack output3;
	private float prob2;
	private float prob3;

	public RecipeGrinder(ItemStack input,ItemStack output,ItemStack output2,float prob2,ItemStack output3,float prob3){
		this.input = input;
		this.output = output;
		this.output2 = output2;
		this.output3 = output3;
		this.prob2 = prob2;
		this.prob3 = prob3;
	}
	
	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public ItemStack getOutput2() {
		return output2;
	}

	public ItemStack getOutput3() {
		return output3;
	}
	
	public float getProb2() {
		return prob2;
	}

	public float getProb3() {
		return prob3;
	}

	public static RecipeGrinder getRecipe(ItemStack i){
		for(RecipeGrinder r : MgRecipeRegister.grinder){
			if(r.matches(i))return r;
		}
		return null;
	}

	public boolean matches(ItemStack i) {
		if(MgUtils.areEcuals(input, i, true))return true;
		return false;
	}
	
	public String toString(){
		String s = "Grinder Recipe, Input: "+input.getDisplayName()+", Main Output: "+output.getDisplayName();
		if(output2 != null) s += ", Primary Output: "+output2.getDisplayName()+" with "+(int)(prob2*100)+"%";
		if(output3 != null) s += ", Secondary Output: "+output3.getDisplayName()+" with "+(int)(prob3*100)+"%";
		return s;
	}
}