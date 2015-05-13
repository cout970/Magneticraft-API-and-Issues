package com.cout970.magneticraft.api.acces;

import java.util.List;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockInfo;

public interface IThermopileDecay {

	public void onCheck(World w,List<BlockInfo> b,double tempHot,double tempCold);
}
