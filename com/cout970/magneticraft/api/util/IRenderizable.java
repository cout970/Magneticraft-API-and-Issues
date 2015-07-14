package com.cout970.magneticraft.api.util;

import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Cout970
 *
 */
public interface IRenderizable {

	public ResourceLocation getTexture();
	
	/**
	 * both methods render at the same time but the statir is not afected by rotation or translation
	 * @param f5 render scale usually 0.0625f
	 */
	public void renderStatic(float f5);
	public void renderDynamic(float f5, float additionalData);
}
