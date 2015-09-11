package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.item.ItemStack;

public class RecipeSifter {

    protected ItemStack input;
    protected ItemStack output;
    protected ItemStack extra;
    protected float prob;

    public RecipeSifter(ItemStack input, ItemStack output, ItemStack extra, float prob) {
        this.input = input;
        this.output = output;
        this.extra = extra;
        this.prob = prob;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getExtra() {
        return extra;
    }

    public float getProb() {
        return prob;
    }

    public static RecipeSifter getRecipe(ItemStack i) {
        for (RecipeSifter r : MgRecipeRegister.sifter) {
            if (r.matches(i)) return r;
        }
        return null;
    }

    public boolean matches(ItemStack i) {
        if (MgUtils.areEqual(input, i, true)) return true;
        return false;
    }

    @Override
    public String toString() {
        String s = "Sifter Recipe, Input: " + input.getDisplayName() + ", Main Output: " + output.getDisplayName();
        if (extra != null) s += ", Primary Output: " + extra.getDisplayName() + " with " + (int) (prob * 100) + "%%";
        return s;
    }
}
