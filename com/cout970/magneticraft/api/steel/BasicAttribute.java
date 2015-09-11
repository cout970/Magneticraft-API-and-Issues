package com.cout970.magneticraft.api.steel;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * Basic Implementation of ISteelAttribute
 *
 * @author minecreatr
 */
public class BasicAttribute implements ISteelAttribute {

    private String name;

    public BasicAttribute(String name) {
        this.name = name;
        AttributeRegistry.registerAttribute(this);
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayText(int value) {
        return EnumChatFormatting.BLUE + StatCollector.translateToLocal(getName()) + EnumChatFormatting.GRAY + ": " + EnumChatFormatting.GREEN + value;
    }
}
