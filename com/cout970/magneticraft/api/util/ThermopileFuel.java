package com.cout970.magneticraft.api.util;

import com.cout970.magneticraft.api.acces.MgRecipeRegister;

/**
 * This Class if for register an fuel for the Termopile
 * @author Cout970
 *
 */
public class ThermopileFuel {

	public boolean heat;//true == heat source, false == cold source
	public BlockInfo source;
	public double temp;//always positive
	
	public ThermopileFuel(BlockInfo s,double t,boolean heat){
		source = s;
		temp = t;
		this.heat = heat;
	}
	
	public static ThermopileFuel getRecipe(BlockInfo p){
		for(ThermopileFuel f : MgRecipeRegister.thermopileSources){
			if(f.source.equals(p)){
				return f;
			}
		}
		return null;
	}
	
	public String toString(){
		return "Recipe Thermopile "+(heat ? "Hot": "Cold")+" Source, Temp: "+(heat ? temp : -temp);
	}
}
