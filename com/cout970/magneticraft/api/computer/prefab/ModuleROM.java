package com.cout970.magneticraft.api.computer.prefab;

import com.cout970.magneticraft.api.computer.IModuleMemoryController;
import com.cout970.magneticraft.api.computer.IModuleROM;

import java.io.IOException;
import java.io.InputStream;

public class ModuleROM implements IModuleROM {

    @Override
    public void loadToRAM(IModuleMemoryController ram) {
        InputStream archive;
        try {
            archive = ComputerUtils.getInputStream("forth.bin");
            byte[] buffer = new byte[0x3000];
            archive.read(buffer, 0, buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                ram.writeByte(i, buffer[i]);
            }
            archive.close();

            archive = ComputerUtils.getInputStream("forth_data.bin");
            buffer = new byte[0x3000];
            archive.read(buffer, 0, buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                ram.writeByte(0x3000 + i, buffer[i]);
            }
            archive.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
