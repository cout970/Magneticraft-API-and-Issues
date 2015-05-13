package com.cout970.magneticraft.api.acces;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.ThermopileFuel;

public class MgRegister {

	public static List<ThermopileFuel> ThermopileSources = new ArrayList<ThermopileFuel>();
	public static List<IThermopileDecay> ThermopileDecais = new ArrayList<IThermopileDecay>();
	public static List<RecipeBiomassBurner> BiomassBurner = new ArrayList<RecipeBiomassBurner>();
	
	
	public static boolean addBiomassBurnerRecipe(ItemStack item,int burnTime, boolean ignoreNBT){
		if(item == null && burnTime <= 0)return false;
		RecipeBiomassBurner r = new RecipeBiomassBurner(item, burnTime, !ignoreNBT);
		if(!BiomassBurner.contains(r)){
			BiomassBurner.add(r);
			return true;
		}
		return false;
	}
	
	/**
	 * @param b block that interact
	 * @param temp heat or cold value, negatives values are not allowed
	 * @param heat true if is heat registration or false if is cold
	 * @return true if the register work
	 */
	public static boolean addThermopileSource(BlockInfo b,double temp,boolean heat){
		if(b == null || temp == 0)return false;
		if(temp < 0)return false;
		ThermopileFuel f = new ThermopileFuel(b, temp, heat);
		if(!ThermopileSources.contains(f)){
			ThermopileSources.add(f);
		}
		return true;
	}
	
	/**
	 * @param t thermopile listener 
	 * @return false if the registry fails
	 */
	public static boolean addThermopileDecay(IThermopileDecay t){
		if(t == null || ThermopileDecais.contains(t))return false;
		ThermopileDecais.add(t);
		return true;
	}
}
