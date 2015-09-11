package com.cout970.magneticraft.api.electricity;

import net.minecraft.item.ItemStack;

/**
 * @author Cout970
 */
public interface IBatteryItem {

    /**
     * the amount of energy in the battery in RF
     *
     * @param it
     * @return
     */
    public int getCharge(ItemStack it);

    /**
     * the amount of energy to drain from the battery, must always positive
     *
     * @param it
     * @param energy
     */
    public void discharge(ItemStack it, int energy);

    /**
     * the amount of energy to add to the battery
     *
     * @param stack
     * @param energy
     * @return
     */
    public int charge(ItemStack stack, int energy);

    /**
     * the max amount of charge that the battery can store
     *
     * @return
     */
    public int getMaxCharge(ItemStack stack);

    public boolean canAcceptCharge(ItemStack stack);

    public boolean canExtractCharge(ItemStack stack);

    public boolean canProvideEnergy(ItemStack stack);
}