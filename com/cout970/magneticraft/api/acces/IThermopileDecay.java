package com.cout970.magneticraft.api.acces;

import java.util.List;

import com.cout970.magneticraft.api.util.BlockInfo;

import net.minecraft.world.World;

/**
 * 
 * @author Cout970
 *
 */
public interface IThermopileDecay {

	public void onCheck(World w, List<BlockInfo> b,double tempHot,double tempCold);
}
