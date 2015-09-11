package com.cout970.magneticraft.api.conveyor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IItemBox {

    public ItemStack getContent();

    public void setContent(ItemStack content);

    public int getPosition();

    public void setPosition(int positions);

    public boolean isOnLeft();

    public void setOnLeft(boolean isLeft);

    public void save(NBTTagCompound t);

    public void load(NBTTagCompound t);

    public long getLastUpdateTick();

    public void setLastUpdateTick(long tick);
}
