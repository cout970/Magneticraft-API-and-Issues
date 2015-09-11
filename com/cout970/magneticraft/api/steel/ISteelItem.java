package com.cout970.magneticraft.api.steel;

import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Represents an item made out of steel
 */
public interface ISteelItem {


    /**
     * Gets the value of the specified attribute on the specified ItemStack
     *
     * @param stack     The ItemStack
     * @param attribute The Attribute
     * @return
     */
    public int getValue(ItemStack stack, ISteelAttribute attribute);

    /**
     * Sets the value of the specified attribute on the specified itemstack to the specified value
     *
     * @param stack     The ItemStack
     * @param attribute The Attribute
     * @param value     The Value
     */
    public void setValue(ItemStack stack, ISteelAttribute attribute, int value);

    /**
     * Get all the attributes that have a value for this item
     *
     * @param stack The ItemStack
     * @return A List of its attributes
     */
    public List<ISteelAttribute> getAllApplicableAttributes(ItemStack stack);

    /**
     * Gets the attribute map
     *
     * @param stack The ItemStack
     * @return The Attribute map
     */
    public Map<ISteelAttribute, Integer> getAttributeMap(ItemStack stack);

    /**
     * Whether to disable the tooltip of information about the steel item for the specified ItemStack
     *
     * @param stack The ItemStack
     * @return Whether to disable the tooltip
     */
    public boolean disableTooltip(ItemStack stack);
}
