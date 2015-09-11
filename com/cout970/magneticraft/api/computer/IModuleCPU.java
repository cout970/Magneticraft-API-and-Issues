package com.cout970.magneticraft.api.computer;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleCPU extends IHardwareComponent {

    public void iterate();

    public boolean isRunning();

    public void start();

    public void stop();

    public void haltTick();

    public int getRegister(int reg);

    public void setRegister(int reg, int value);

    public int getRegPC();

    public void setRegPC(int value);

    public void connectMemory(IModuleMemoryController ram);

    public void loadRegisters(NBTTagCompound nbt);

    public void saveRegisters(NBTTagCompound nbt);

}
