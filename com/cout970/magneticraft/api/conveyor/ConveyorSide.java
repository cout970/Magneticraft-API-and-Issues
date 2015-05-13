package com.cout970.magneticraft.api.conveyor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.conveyor.IConveyor.BeltInteraction;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.MgBeltUtils;

public class ConveyorSide {

	public IConveyor parent;
	public List<ItemBox> content = new ArrayList<ItemBox>();
	public boolean[] spaces = new boolean[16];
	public boolean isLeft;
	
	public ConveyorSide(IConveyor parent, boolean isLeft){
		this.parent = parent;
		this.isLeft = isLeft;
	}
	
	public void addvance(ItemBox b){
		setSpace(b.getPosition(),false);
		if(hasSpace(b.getPosition()+1)){
			b.setPosition(b.getPosition()+1);
		}
		setSpace(b.getPosition(),true);
	}
	
	public void setSpace(int pos, boolean value) {
		TileEntity tile = null;
		if(4+pos >= spaces.length){
			tile = MgUtils.getTileEntity(parent.getParent(), parent.getDir());
		}
		for(int i = 0;i<4;i++){
			if(i+pos >= spaces.length){
				setSpaceExtern(tile, i+pos-16, value);
			}else{
				spaces[pos+i] = value;
			}
		}
	}
	
	public void setSpaceExtern(TileEntity tile,int pos, boolean value) {
		if(MgBeltUtils.isBelt(tile)){
			IConveyor con = (IConveyor) tile;
			BeltInteraction iter = BeltInteraction.InterBelt(parent.getDir(), con.getDir());
			if(iter == IConveyor.BeltInteraction.DIRECT){
				con.getSideLane(isLeft).spaces[pos] = value;
			}else if(iter == IConveyor.BeltInteraction.LEFT_T){
				if(isLeft){
					for(int i = 2;i<6;i++)
						con.getSideLane(false).spaces[i] = value;
				}else{
					for(int i = 10;i<14;i++)
						con.getSideLane(false).spaces[i] = value;
				}
			}else if(iter == IConveyor.BeltInteraction.RIGHT_T){
				if(!isLeft){
					for(int i = 2;i<6;i++)
						con.getSideLane(true).spaces[i] = value;
				}else{
					for(int i = 10;i<14;i++)
						con.getSideLane(true).spaces[i] = value;
				}
			}
		}
	}

	public boolean hasSpace(int pos) {
		TileEntity tile = null;
		if(4+pos >= spaces.length){
			tile = MgUtils.getTileEntity(parent.getParent(), parent.getDir());
		}
		for(int i = 0;i<4;i++){
			if(i+pos >= spaces.length){
				if(!hasSpaceExtern(tile, i+pos-16))return false;
			}else{
				if(spaces[pos+i])return false;
			}
		}
		return true;
	}

	public boolean hasSpaceExtern(TileEntity tile, int pos) {
		if(MgBeltUtils.isBelt(tile)){
			IConveyor con = (IConveyor) tile;
			boolean temp = false;
			BeltInteraction iter = BeltInteraction.InterBelt(parent.getDir(), con.getDir());
			
			if(iter == IConveyor.BeltInteraction.DIRECT){
				return !con.getSideLane(isLeft).spaces[pos];
			}else if(iter == IConveyor.BeltInteraction.LEFT_T){
				if(isLeft){
					for(int i = 2;i<6;i++)
						temp |= con.getSideLane(false).spaces[i];
				}else{
					for(int i = 10;i<14;i++)
						temp |= con.getSideLane(false).spaces[i];
				}
				return !temp;
			}else if(iter == IConveyor.BeltInteraction.RIGHT_T){
				if(!isLeft){
					for(int i = 2;i<6;i++)
						temp |= con.getSideLane(true).spaces[i];
				}else{
					for(int i = 10;i<14;i++)
						temp |= con.getSideLane(true).spaces[i];
				}
				return !temp;
			}
		}
		return false;
	}
	
	public void save(NBTTagCompound nbt) {
		String side = isLeft ? "Left" : "Right";
		for(int i = 0; i< spaces.length;i++){
			nbt.setBoolean(side+"_"+i, spaces[i]);
		}
		NBTTagList list = new NBTTagList();
		nbt.setInteger(side+"Size", content.size());
		for(int i=0;i<content.size();i++){
			NBTTagCompound t = new NBTTagCompound();
			if(content.get(i) != null){
				content.get(i).save(t);
			}
			list.appendTag(t);
		}
		nbt.setTag(side+"_Boxes", list);
	}

	public void load(NBTTagCompound nbt) {
		String side = isLeft ? "Left" : "Right";
		content.clear();
		for(int i = 0; i< spaces.length;i++){
			spaces[i] = nbt.getBoolean(side+"_"+i);
		}
		int content_size = nbt.getInteger(side+"Size");
		ItemBox box = null;

		NBTTagList list = nbt.getTagList(side+"_Boxes",10);
		for(int i=0;i<content_size;i++){
			NBTTagCompound t = list.getCompoundTagAt(i);
			if(t.hasKey("Left")){
				box = new ItemBox(null);
				box.load(t);
				content.add(box);
			}
		}
	}
}
