package com.cout970.magneticraft.api.computer.prefab;

import com.cout970.magneticraft.api.computer.IPeripheral;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class MonitorPeripheral implements IPeripheral {

    public static final int KEYBOARD_MASK = 0xff010000;
    public static final int TEXT_MASK = 0xff010014;
    private byte[] buffer;
    private int regReady;
    private int regChar;
    private int address = 0x1;
    private TileEntity parent;


    public MonitorPeripheral(TileEntity tile) {
        parent = tile;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public String getName() {
        return "TextMonitor";
    }

    @Override
    public int readByte(int pointer) {
        if (pointer >= getBuffer().length) return 0;
        return getBuffer()[pointer];
    }

    @Override
    public void writeByte(int pointer, int data) {
        if (pointer >= getBuffer().length) return;
        getBuffer()[pointer] = (byte) data;
    }

    @Override
    public TileEntity getParent() {
        return parent;
    }

    public byte[] getBuffer() {
        if (buffer == null || buffer.length != 4020) buffer = new byte[4020];
        return buffer;
    }

    public int getText(int pos) {
        return getBuffer()[pos + 20];
    }

    public void keyPresed(int key) {
        regReady |= 1;
        regChar = key;
    }

    public void load(NBTTagCompound main) {
        NBTTagList list = main.getTagList("TextMonitor", 10);
        NBTTagCompound nbt = list.getCompoundTagAt(0);
        address = nbt.getInteger("Address");
        buffer = nbt.getByteArray("Buffer");
        getBuffer();
    }

    public void save(NBTTagCompound main) {
        NBTTagList list = new NBTTagList();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Address", address);
        nbt.setByteArray("Buffer", getBuffer());
        list.appendTag(nbt);
        main.setTag("TextMonitor", list);
    }

    public void saveInfoToMessage(NBTTagCompound nbt) {
        nbt.setByte("Ready", (byte) regReady);
        nbt.setByte("Char", (byte) regChar);
    }

    public void loadInfoFromMessage(NBTTagCompound nbt) {
        getBuffer()[0] = nbt.getByte("Ready");
        getBuffer()[4] = nbt.getByte("Char");
    }
}
