package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.item.ItemStack;

/**
 * @author Cout970
 */
public class RecipeCrusher {

    protected final ItemStack input;
    protected final ItemStack output;
    protected final ItemStack output2;
    protected final ItemStack output3;
    protected float prob2;
    protected float prob3;

    public RecipeCrusher(ItemStack input, ItemStack output, ItemStack output2, float prob1, ItemStack output3, float prob22) {
        this.input = input;
        this.output = output;
        this.output2 = output2;
        this.output3 = output3;
        this.prob2 = prob1;
        this.prob3 = prob22;
    }

    public float getProb2() {
        return prob2;
    }

    public float getProb3() {
        return prob3;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getOutput2() {
        return output2;
    }

    public ItemStack getOutput3() {
        return output3;
    }

    public static RecipeCrusher getRecipe(ItemStack i) {
        for (RecipeCrusher r : MgRecipeRegister.crusher) {
            if (r.matches(i)) {
                return r;
            }
        }
        return null;
    }

    public boolean matches(ItemStack i) {
        return MgUtils.areEqual(input, i, true);
    }

    @Override
    public String toString() {
        String s = "Crusher Recipe, Input: " + input.getDisplayName() + ", Main Output: " + output.getDisplayName();
        if (output2 != null)
            s += ", Primary Output: " + output2.getDisplayName() + " with " + (int) (prob2 * 100) + "%%";
        if (output3 != null)
            s += ", Secondary Output: " + output3.getDisplayName() + " with " + (int) (prob3 * 100) + "%%";
        return s;
    }
}
