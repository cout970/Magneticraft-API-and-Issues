package com.cout970.magneticraft.api.access;

import net.minecraft.item.ItemStack;

/**
 * @author Cout970
 */
public class RecipeBiomassBurner {

    protected final ItemStack fuel;
    protected final int burnTime;
    protected final boolean useNBT;

    public RecipeBiomassBurner(ItemStack a, int b, boolean c) {
        fuel = a;
        burnTime = b;
        useNBT = c;
    }

    public ItemStack getFuel() {
        return fuel;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public boolean useNBT() {
        return useNBT;
    }

    public static RecipeBiomassBurner getRecipe(ItemStack i) {
        for (RecipeBiomassBurner r : MgRecipeRegister.biomassBurner) {
            if (r.matches(i)) {
                return r;
            }
        }
        return null;
    }

    public boolean matches(ItemStack item) {
        return item.isItemEqual(getFuel()) && (!useNBT || ItemStack.areItemStackTagsEqual(item, getFuel()));
    }

    public String toString() {
        return "Biomass Burner Recipe, Fuel: " + fuel.getDisplayName() + ", Fuel value: " + burnTime;
    }
}
