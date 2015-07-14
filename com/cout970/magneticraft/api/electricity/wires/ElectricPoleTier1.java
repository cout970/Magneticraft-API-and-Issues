package com.cout970.magneticraft.api.electricity.wires;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

public class ElectricPoleTier1 implements IElectricPole{
	
	protected List<WireConnection> connections = new ArrayList<WireConnection>();
	protected List<WireLoadInfo> buffer = new ArrayList<WireLoadInfo>();
	protected IElectricConductor cond;
	protected TileEntity parent;
	//wire render only
	public int glList = -1;

	public ElectricPoleTier1(TileEntity tile, IElectricConductor cond){
		parent = tile;
		this.cond = cond;
	}
	
	@Override
	public void disconectAll(){
		ArrayList<WireConnection> list = new ArrayList<WireConnection>();
		list.addAll(connections);
		for(WireConnection con : list){
			if(con.getStart() != null)con.getStart().onDisconnect(con);
			if(con.getEnd() != null)con.getEnd().onDisconnect(con);
		}
	}

	@Override
	public void iterate() {
		if(!buffer.isEmpty()){
			connections.clear();
			for(int i = 0; i < buffer.size(); i++){
				WireLoadInfo info = buffer.get(i);
				WireConnection con = new WireConnection(info.start, info.end, parent.getWorldObj());
				connections.add(con);
			}
			refreshList();
			buffer.clear();
		}
		if(parent.getWorldObj().isRemote)return;
		for(WireConnection c : connections){
			if(c.getStart() == this){
				c.iterate();
			}
		}
	}

	@Override
	public List<WireConnection> getConnectedConductors() {
		return connections;
	}

	@Override
	public boolean canConnectWire(int tier, IElectricPole to) {
		if(to == this)return false;
		if(tier != 0)return false;
		for(WireConnection con : connections){
			if(con.vecStart().equals(new VecInt(to.getParent())))return false;
			if(con.vecEnd().equals(new VecInt(to.getParent())))return false;
		}
		return true;
	}

	@Override
	public void onDisconnect(WireConnection conn) {
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
	public VecDouble[] getWireConnector() {
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
	public void onConnect(WireConnection wire) {
		connections.add(wire);
		refreshList();
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
		for(WireConnection c : connections){
			NBTTagCompound tag = new NBTTagCompound();
			c.save(tag);
			nbtList.appendTag(tag);
		}
		for(WireLoadInfo info : buffer){
			NBTTagCompound tag = new NBTTagCompound();
			info.start.save(nbt,"Start");
			info.end.save(nbt,"End");
			nbt.setDouble("EnergyFlow", info.flow);
			nbtList.appendTag(tag);
		}
		nbt.setTag("connect", nbtList);
	}

	@Override
	public void load(NBTTagCompound nbt) {
		NBTTagList nbtList = nbt.getTagList("connect", 10);
		buffer.clear();
		for(int i = 0; i < nbtList.tagCount(); i++){
			NBTTagCompound tag = nbtList.getCompoundTagAt(i);
			WireLoadInfo wire = WireConnection.loadFromNBT(tag);
			if(wire != null){
				buffer.add(wire);
			}
		}
		if(buffer.isEmpty())disconectAll();
	}	
	
	public static void findConnections(IElectricPole pole){
		pole.disconectAll();
		int rad = 16;
		for(int x = -rad; x <= rad; x++){
			for(int z = -rad; z <= rad; z++){
				for(int y = -5; y <= 5; y++){
					if(x == 0 && z == 0)continue;
					TileEntity t = new VecInt(pole.getParent()).add(x, y, z).getTileEntity(pole.getParent().getWorldObj());
					IElectricPole p = MgUtils.getElectricPole(t);
					if(p == null)continue;
					if(p.canConnectWire(0, pole) && pole.canConnectWire(0, p)){
						WireConnection wire = new WireConnection(new VecInt(pole.getParent()), new VecInt(p.getParent()), pole.getParent().getWorldObj());
						pole.onConnect(wire);
						p.onConnect(wire);
					}
				}
			}
		}
	}
}
