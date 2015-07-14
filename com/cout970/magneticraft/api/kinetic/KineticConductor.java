package com.cout970.magneticraft.api.kinetic;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Log;

import net.minecraft.tileentity.TileEntity;

public class KineticConductor implements IKineticConductor{

	public TileEntity tile;
	public KineticType type;
	public double lose = 0.1;
	public float rotation;
	public double mass = 1;
	public double speed;
	public double lastSpeed;
	public long time;
	
	public KineticNetwork net;
	
	public KineticConductor(TileEntity p) {
		tile = p;
		type = KineticType.Transport;
	}
	
	public KineticConductor(TileEntity p, double lose, double mass) {
		tile = p;
		this.lose = lose;
		this.mass = mass;
		type = KineticType.Consumer;
	}
	
	public KineticConductor(TileEntity p, double lose) {
		tile = p;
		this.lose = lose;
		type = KineticType.Consumer;
	}
	
	@Override
	public double getLose() {
		return lose;
	}

	@Override
	public void iterate() {
		lastSpeed = speed;
		speed = 0;
	}

	@Override
	public KineticNetwork getNetwork() {
		return net;
	}

	@Override
	public void setNetwork(KineticNetwork net) {
		this.net = net;
	}

	@Override
	public TileEntity getParent() {
		return tile;
	}

	@Override
	public KineticType getFunction() {
		return type;
	}

	@Override
	public double getWork() {
		return 0.5*getMass()*getSpeed()*getSpeed();
	}

	@Override
	public MgDirection[] getValidSides() {
		return ((IKineticTile)tile).getValidSides();
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public double getSpeed() {
		return lastSpeed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed += speed;
	}

	@Override
	public void setLose(double lose) {
		this.lose = lose;
	}

	@Override
	public void setMass(double mass) {
		this.mass = mass;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float angle) {
		rotation = angle;
	}

	@Override
	public double getDelta() {
		long aux = time;
		time = System.nanoTime();
		return (double)((time-aux)*1E-6);
	}
	
}
