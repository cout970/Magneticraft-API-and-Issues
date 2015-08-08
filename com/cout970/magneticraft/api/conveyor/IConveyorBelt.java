package com.cout970.magneticraft.api.conveyor;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.Orientation;

import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author Cout970
 *
 */
public interface IConveyorBelt {

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

	public boolean addItem(MgDirection in, int pos, IItemBox it, boolean simulated);
	public boolean removeItem(IItemBox it, boolean isLeft, boolean simulated);

	public IConveyorBeltLane getSideLane(boolean left);

	public MgDirection getDir();

	public boolean extract(IItemBox box, boolean isOnLeft, boolean simulated);
	public boolean inject(int pos, IItemBox box, boolean isOnLeft, boolean simulated);

	public TileEntity getParent();

	public Orientation getOrientation();
	public void onChange();
}
