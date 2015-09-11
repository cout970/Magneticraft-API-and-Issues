package com.cout970.magneticraft.api.conveyor.prefab;

import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.IConveyorBelt.BeltInteraction;
import com.cout970.magneticraft.api.conveyor.IConveyorBeltLane;
import com.cout970.magneticraft.api.conveyor.IHitBoxArray;
import com.cout970.magneticraft.api.conveyor.IItemBox;
import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cout970
 */
public class ConveyorBeltLane implements IConveyorBeltLane {

    protected IConveyorBelt parent;
    protected List<IItemBox> content = new ArrayList<IItemBox>();
    protected HitBoxArray hitBox = new HitBoxArray();
    protected boolean isLeft;

    public ConveyorBeltLane(IConveyorBelt parent, boolean isLeft) {
        this.parent = parent;
        this.isLeft = isLeft;
    }

    public void avance(IItemBox b) {
        setHitBoxSpace(b.getPosition(), false);
        if (hasHitBoxSpace(b.getPosition() + 1)) {
            b.setPosition(b.getPosition() + 1);
        }
        setHitBoxSpace(b.getPosition(), true);
    }

    public void setHitBoxSpace(int pos, boolean value) {
        TileEntity tile = null;
        if (4 + pos >= hitBox.size()) {
            tile = getFrontConveyor(parent);
        }
        for (int i = 0; i < 4; i++) {
            if (i + pos >= hitBox.size()) {
                setHitBoxSpaceExtern(tile, i + pos - 16, value);
            } else {
                hitBox.setOccupied(pos + i, value);
            }
        }
    }

    public void setHitBoxSpaceExtern(TileEntity tile, int pos, boolean value) {
        if (tile instanceof IConveyorBelt) {
            IConveyorBelt con = (IConveyorBelt) tile;
            BeltInteraction iter = BeltInteraction.InterBelt(parent.getDir(), con.getDir());
            if (iter == IConveyorBelt.BeltInteraction.DIRECT) {
                con.getSideLane(isLeft).getHitBoxes().setOccupied(pos % 16, value);
            } else if (iter == IConveyorBelt.BeltInteraction.LEFT_T) {
                if (isLeft) {
                    for (int i = 2; i < 6; i++)
                        con.getSideLane(false).getHitBoxes().setOccupied(i % 16, value);
                } else {
                    for (int i = 10; i < 14; i++)
                        con.getSideLane(false).getHitBoxes().setOccupied(i % 16, value);
                }
            } else if (iter == IConveyorBelt.BeltInteraction.RIGHT_T) {
                if (!isLeft) {
                    for (int i = 2; i < 6; i++)
                        con.getSideLane(true).getHitBoxes().setOccupied(i % 16, value);
                } else {
                    for (int i = 10; i < 14; i++)
                        con.getSideLane(true).getHitBoxes().setOccupied(i % 16, value);
                }
            }
        }
    }

    public boolean hasHitBoxSpace(int pos) {
        TileEntity tile = null;
        if (4 + pos >= hitBox.size()) {
            tile = getFrontConveyor(parent);
        }
        for (int i = 0; i < 4; i++) {
            if (i + pos >= hitBox.size()) {
                if (!hasHitBoxSpaceExtern(tile, i + pos - 16)) return false;
            } else {
                if (hitBox.hasSpace(pos + i)) return false;
            }
        }
        return true;
    }

    public boolean hasHitBoxSpaceExtern(TileEntity tile, int pos) {
        if (tile instanceof IConveyorBelt) {
            IConveyorBelt con = (IConveyorBelt) tile;
            boolean temp = false;
            BeltInteraction iter = BeltInteraction.InterBelt(parent.getDir(), con.getDir());

            if (iter == IConveyorBelt.BeltInteraction.DIRECT) {
                return !con.getSideLane(isLeft).getHitBoxes().hasSpace(pos % 16);
            } else if (iter == IConveyorBelt.BeltInteraction.LEFT_T) {
                if (isLeft) {
                    for (int i = 2; i < 6; i++)
                        temp |= con.getSideLane(false).getHitBoxes().hasSpace(i % 16);
                } else {
                    for (int i = 10; i < 14; i++)
                        temp |= con.getSideLane(false).getHitBoxes().hasSpace(i % 16);
                }
                return !temp;
            } else if (iter == IConveyorBelt.BeltInteraction.RIGHT_T) {
                if (!isLeft) {
                    for (int i = 2; i < 6; i++)
                        temp |= con.getSideLane(true).getHitBoxes().hasSpace(i % 16);
                } else {
                    for (int i = 10; i < 14; i++)
                        temp |= con.getSideLane(true).getHitBoxes().hasSpace(i % 16);
                }
                return !temp;
            }
        }
        return false;
    }

    public void save(NBTTagCompound nbt) {
        String side = isLeft ? "Left" : "Right";
        for (int i = 0; i < hitBox.size(); i++) {
            nbt.setBoolean(side + "_" + i, hitBox.hasSpace(i));
        }
        NBTTagList list = new NBTTagList();
        nbt.setInteger(side + "Size", content.size());
        for (int i = 0; i < content.size(); i++) {
            NBTTagCompound t = new NBTTagCompound();
            if (content.get(i) != null) {
                content.get(i).save(t);
            }
            list.appendTag(t);
        }
        nbt.setTag(side + "_Boxes", list);
    }

    public void load(NBTTagCompound nbt) {
        String side = isLeft ? "Left" : "Right";
        content.clear();
        for (int i = 0; i < hitBox.size(); i++) {
            getHitBoxes().setOccupied(i, nbt.getBoolean(side + "_" + i));
        }
        int content_size = nbt.getInteger(side + "Size");
        IItemBox box = null;

        NBTTagList list = nbt.getTagList(side + "_Boxes", 10);
        for (int i = 0; i < content_size; i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            if (t.hasKey("Left")) {
                box = new ItemBox(null);
                box.load(t);
                content.add(box);
            }
        }
    }

    public static TileEntity getFrontConveyor(IConveyorBelt c) {
        TileEntity t = c.getParent();
        if (c.getOrientation().getLevel() == 1)
            return MgUtils.getTileEntity(t, c.getDir().toVecInt().add(0, 1, 0));
        TileEntity retval = MgUtils.getTileEntity(t, c.getDir());
        if (!(retval instanceof IConveyorBelt)) {
            retval = MgUtils.getTileEntity(t, c.getDir().toVecInt().add(0, -1, 0));
        }
        return retval;
    }

    @Override
    public IConveyorBelt getConveyorBelt() {
        return parent;
    }

    @Override
    public List<IItemBox> getItemBoxes() {
        return content;
    }

    @Override
    public IHitBoxArray getHitBoxes() {
        return hitBox;
    }

    @Override
    public boolean isOnLeft() {
        return isLeft;
    }
}
