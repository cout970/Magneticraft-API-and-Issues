package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.item.ItemStack;

public class RecipeCrushingTable {
    protected final ItemStack input;
    protected final ItemStack output;

    public RecipeCrushingTable(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public boolean matches(ItemStack i) {
        return MgUtils.areEqual(input, i, true);
    }

    public static RecipeCrushingTable getRecipe(ItemStack i) {
        for (RecipeCrushingTable r : MgRecipeRegister.crushing_table) {
            if (r.matches(i)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Crushing Table Recipe, Input: " + input.getDisplayName() + ", Output: " + output.getDisplayName();
    }
}