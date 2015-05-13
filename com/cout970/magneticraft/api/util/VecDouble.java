package com.cout970.magneticraft.api.util;

import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.base.Objects;

public class VecDouble {

	public static final VecDouble NULL_VECTOR = new VecDouble(0, 0, 0);
	protected double x;
	protected double y;
	protected double z;

	public VecDouble(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VecDouble(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public VecDouble(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VecDouble(double[] ar) {
		this(ar[0],ar[1],ar[2]);
	}

	public VecDouble(VecInt vec) {
		this(vec.getX(),vec.getY(),vec.getZ());
	}

	public static VecDouble getConnexion(MgDirection d) {
		return new VecDouble(d.getOffsetX(), d.getOffsetY(), d.getOffsetZ());
	}
	
	public static VecDouble getConnexion(ForgeDirection d) {
		return new VecDouble(d.offsetX, d.offsetY, d.offsetZ);
	}

	public VecDouble getOpposite() {
		return new VecDouble(-x, -y, -z);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof VecDouble)) {
			return false;
		} else {
			VecDouble vec = (VecDouble) obj;
			return this.getX() != vec.getX() ? false
					: (this.getY() != vec.getY() ? false
							: this.getZ() == vec.getZ());
		}
	}

	public int hashCode() {
		return (int) ((this.getY() + this.getZ() * 31) * 31 + this.getX());
	}

	public double compareTo(VecDouble vec) {
		return this.getY() == vec.getY() ? (this.getZ() == vec.getZ() ? this
				.getX() - vec.getX() : this.getZ() - vec.getZ()) : this.getY()
				- vec.getY();
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("x", this.getX())
				.add("y", this.getY()).add("z", this.getZ()).toString();
	}

	public VecDouble multiply(double d) {
		x *= d;
		y *= d;
		z *= d;
		return this;
	}

	public VecDouble add(VecDouble v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public VecDouble add(double a, double b, double c) {
		x += a;
		y += b;
		z += c;
		return this;
	}

	public VecDouble copy() {
		return new VecDouble(x,y,z);
	}
}
