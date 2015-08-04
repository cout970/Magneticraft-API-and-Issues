package com.cout970.magneticraft.api.electricity.prefab;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.IInterPoleWire;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class ElectricPoleTier1 implements IElectricPole{
	
	protected List<IInterPoleWire> connections = new ArrayList<IInterPoleWire>();
	protected IElectricConductor cond;
	protected TileEntity parent;
	public boolean update = true;
	//wire render only
	public int glList = -1;

	public ElectricPoleTier1(TileEntity tile, IElectricConductor cond){
		parent = tile;
		this.cond = cond;
	}
	
	@Override
	public void disconectAll(){
		update = true;
		ArrayList<IInterPoleWire> list = new ArrayList<IInterPoleWire>();
		list.addAll(connections);
		for(IInterPoleWire con : list){
			if(con.getWorld() == null){
				if(parent == null)continue;
				con.setWorld(parent.getWorldObj());
			}
			if(con.getStart() != null)con.getStart().onDisconnect(con);
			if(con.getEnd() != null)con.getEnd().onDisconnect(con);
		}
	}

	@Override
	public void iterate() {
		if(update){
			update = false;
			for(IInterPoleWire c : connections){
				c.setWorld(getParent().getWorldObj());
			}
			refreshList();
		}
		if(parent.getWorldObj().isRemote)return;
		for(IInterPoleWire c : connections){
			if(c.getStart() == this){
				c.iterate();
			}
		}
	}

	@Override
	public List<IInterPoleWire> getConnectedConductors() {
		return connections;
	}

	@Override
	public boolean canConnectWire(int tier, IElectricPole to) {
		if(to == this)return false;
		if(tier != 0)return false;
		VecDouble vec = new VecDouble(getParent()).add(new VecDouble(to.getParent()).getOpposite());
		if(vec.mag() > 16)return false;
		return true;
	}

	@Override
	public void onDisconnect(IInterPoleWire conn) {
		connections.remove(conn);
		refreshList();
	}

	@Override
	public int getTier(){
		return 0;
	}

	@Override
	public TileEntity getParent() {
		return parent;
	}

	@Override
	public IElectricConductor getConductor() {
		return cond;
	}

	@Override
	public VecDouble[] getWireConnectors() {
		switch(getParent().getBlockMetadata()-6){
		case 0: return new VecDouble[]{new VecDouble(1.845, 0.75, 0.5), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.845, 0.75, 0.5)};
		case 4: return new VecDouble[]{new VecDouble(-0.845, 0.75, 0.5), new VecDouble(0.5, 1, 0.5), new VecDouble(1.845, 0.75, 0.5)};

		case 1: return new VecDouble[]{new VecDouble(1.45, 0.75, 1.45), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.45, 0.75, -0.45)};
		case 5: return new VecDouble[]{new VecDouble(-0.45, 0.75, -0.45), new VecDouble(0.5, 1, 0.5), new VecDouble(1.45, 0.75, 1.45)};

		case 3: return new VecDouble[]{new VecDouble(-0.45, 0.75, 1.45), new VecDouble(0.5, 1, 0.5), new VecDouble(1.45, 0.75, -0.45)};
		case 7: return new VecDouble[]{new VecDouble(1.45, 0.75, -0.45), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.45, 0.75, 1.45)};

		case 2: return new VecDouble[]{new VecDouble(0.5, 0.75, 1.845), new VecDouble(0.5, 1, 0.5), new VecDouble(0.5, 0.75, -0.845)};
		case 6: return new VecDouble[]{new VecDouble(0.5, 0.75, -0.845), new VecDouble(0.5, 1, 0.5), new VecDouble(0.5, 0.75, 1.845)};
		}
		return new VecDouble[]{};
	}

	@Override
	public void onConnect(IInterPoleWire wire) {
		connections.add(wire);
		update = true;
	}
	
	private void refreshList(){
		if(getParent().getWorldObj().isRemote){
			if(glList != -1){
				GL11.glDeleteLists(glList, 1);
				glList = -1;
			}
		}
	}

	@Override
	public void save(NBTTagCompound nbt) {
		NBTTagList nbtList = new NBTTagList();
		for(IInterPoleWire c : connections){
			NBTTagCompound tag = new NBTTagCompound();
			c.save(tag);
			nbtList.appendTag(tag);
		}
		nbt.setTag("connect", nbtList);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		update = true;
		NBTTagList nbtList = nbt.getTagList("connect", 10);
		connections.clear();
		for(int i = 0; i < nbtList.tagCount(); i++){
			NBTTagCompound tag = nbtList.getCompoundTagAt(i);
			InterPoleWire p = new InterPoleWire();
			p.load(tag);
			connections.add(p);
		}
	}	
	
	public static void findConnections(IElectricPole pole){
		pole.disconectAll();
		int rad = 16;
		for(int x = -rad; x <= rad; x++){
			for(int z = -rad; z <= rad; z++){
				for(int y = -5; y <= 5; y++){
					if(x == 0 && z == 0)continue;
					TileEntity t = new VecInt(pole.getParent()).add(x, y, z).getTileEntity(pole.getParent().getWorldObj());
					IElectricPole p = ElectricUtils.getElectricPole(t);
					if(p == null)continue;
					if(p.canConnectWire(0, pole) && pole.canConnectWire(0, p)){
						InterPoleWire wire = new InterPoleWire(new VecInt(pole.getParent()), new VecInt(p.getParent()));
						wire.setWorld(p.getParent().getWorldObj());
						pole.onConnect(wire);
						p.onConnect(wire);
					}
				}
			}
		}
	}
}
