package com.cout970.magneticraft.api.conveyor;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Orientation;

/**
 * 
 * @author Cout970
 *
 */
public interface IConveyor {

	public enum BeltInteraction{
		DIRECT,INVERSE,LEFT_T,RIGHT_T,NOTHING;

		public static BeltInteraction InterBelt(MgDirection a, MgDirection b){
			if(a == b)return BeltInteraction.DIRECT;
			if(a == b.opposite())return BeltInteraction.INVERSE;
			if(a == b.step(MgDirection.DOWN))return BeltInteraction.LEFT_T;
			if(a == b.step(MgDirection.UP))return BeltInteraction.RIGHT_T;
			return BeltInteraction.NOTHING;
		}
	}

	public boolean addItem(MgDirection in, int pos, ItemBox it, boolean simulated);
	public boolean removeItem(ItemBox it, boolean isLeft, boolean simulated);

	public ConveyorSide getSideLane(boolean left);//array of 16 booleans

	public MgDirection getDir();

	public boolean extract(ItemBox box, boolean isOnLeft, boolean simulated);
	public boolean inject(int pos, ItemBox box, boolean isOnLeft, boolean simulated);

	public TileEntity getParent();

	public Orientation getOrientation();
}
