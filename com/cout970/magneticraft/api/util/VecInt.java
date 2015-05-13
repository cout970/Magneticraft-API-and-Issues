package com.cout970.magneticraft.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import com.google.common.base.Objects;

/**
 * This api is similar to BlockCoord in minecraft 1.8
 * 
 * @author Cout970
 *
 */
public class VecInt {

	public static final VecInt NULL_VECTOR = new VecInt(0, 0, 0);
	protected int x;
	protected int y;
	protected int z;

	public VecInt(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VecInt(double x, double y, double z) {
		this(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
	}

	public VecInt(int[] ar) {
		this(ar[0],ar[1],ar[2]);
	}


	public VecInt(TileEntity tile) {
		this(tile.xCoord, tile.yCoord, tile.zCoord);
	}

	public static VecInt getConnexion(MgDirection d) {
		return new VecInt(d.getOffsetX(), d.getOffsetY(), d.getOffsetZ());
	}
	
	public static VecInt getConnexion(ForgeDirection d) {
		return new VecInt(d.offsetX, d.offsetY, d.offsetZ);
	}

	public VecInt getOpposite() {
		return new VecInt(-x, -y, -z);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof VecInt)) {
			return false;
		} else {
			VecInt vecInt = (VecInt) obj;
			return this.getX() != vecInt.getX() ? false
					: (this.getY() != vecInt.getY() ? false
							: this.getZ() == vecInt.getZ());
		}
	}

	public int hashCode() {
		return (this.getY() + this.getZ() * 31) * 31 + this.getX();
	}

	public int compareTo(VecInt vec) {
		return this.getY() == vec.getY() ? (this.getZ() == vec.getZ() ? this
				.getX() - vec.getX() : this.getZ() - vec.getZ()) : this.getY()
				- vec.getY();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("x", this.getX())
				.add("y", this.getY()).add("z", this.getZ()).toString();
	}

	public MgDirection toMgDirection() {
		for (MgDirection d : MgDirection.values())
			if (d.getOffsetX() == x && d.getOffsetY() == y && d.getOffsetZ() == z)
				return d;
		return null;
	}

	public VecInt multiply(int i) {
		x *= i;
		y *= i;
		z *= i;
		return this;
	}

	public VecInt add(VecInt v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public VecInt add(int a, int b, int c) {
		x += a;
		y += b;
		z += c;
		return this;
	}

	public VecInt copy() {
		return new VecInt(x,y,z);
	}

	public void save(NBTTagCompound nbt) {
		nbt.setInteger("X", x);
		nbt.setInteger("Y", y);
		nbt.setInteger("Z", z);
	}

	public static VecInt load(NBTTagCompound nbt) {
		return new VecInt(nbt.getInteger("X"),nbt.getInteger("Y"),nbt.getInteger("Z"));
	}
}
