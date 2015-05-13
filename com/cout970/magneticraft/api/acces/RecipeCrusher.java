package com.cout970.magneticraft.api.acces;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.util.MgUtils;

public class RecipeCrusher {

	public final ItemStack input;
	public final ItemStack output;
	public final ItemStack output2;
	public final ItemStack output3;
	public int prob2;
	public int prob3;
	
	public RecipeCrusher(ItemStack input,ItemStack output,ItemStack output2,int prob2,ItemStack output3,int prob3){
		this.input = input;
		this.output = output;
		this.output2 = output2;
		this.output3 = output3;
		this.prob2 = prob2;
		this.prob3 = prob3;
	}
	
	public static RecipeCrusher getRecipe(ItemStack i){
		for(RecipeCrusher r : MgRecipeRegister.crusher){
			if(r.matches(i))return r;
		}
		return null;
	}

	public boolean matches(ItemStack i) {
		if(MgUtils.areEcuals(input, i, true))return true;
		return false;
	}
}
