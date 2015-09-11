package com.cout970.magneticraft.api.conveyor.prefab;

import com.cout970.magneticraft.api.conveyor.IItemBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Cout970
 */
public class ItemBox implements IItemBox {

    protected ItemStack content;
    protected int position;
    protected boolean isOnLeft;
    protected long lastTick;

    public ItemBox(ItemStack it) {
        content = (it == null) ? null : it.copy();
    }

    public ItemStack getContent() {
        return content;
    }

    public void setContent(ItemStack content) {
        this.content = (content == null) ? null : content.copy();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int positions) {
        this.position = positions;
    }

    public boolean isOnLeft() {
        return isOnLeft;
    }

    public void setOnLeft(boolean isLeft) {
        this.isOnLeft = isLeft;
    }

    public void save(NBTTagCompound t) {
        getContent().writeToNBT(t);
        t.setInteger("Pos", getPosition());
        t.setBoolean("Left", isOnLeft());
    }

    public void load(NBTTagCompound t) {
        setContent(ItemStack.loadItemStackFromNBT(t));
        setPosition(t.getInteger("Pos"));
        setOnLeft(t.getBoolean("Left"));
    }

    @Override
    public long getLastUpdateTick() {
        return lastTick;
    }

    @Override
    public void setLastUpdateTick(long tick) {
        lastTick = tick;
    }
}
