package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipePolymerizer {

    protected FluidStack fluid;
    protected ItemStack input;
    protected ItemStack output;
    protected double minTemperature;

    public RecipePolymerizer(FluidStack fluid, ItemStack input, ItemStack output, double minTemperature) {
        this.fluid = fluid;
        this.input = input;
        this.output = output;
        this.minTemperature = minTemperature;
    }

    public FluidStack getFluid() {
        return fluid;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public double getTemperature() {
        return minTemperature;
    }

    public static RecipePolymerizer getRecipe(ItemStack i) {
        for (RecipePolymerizer r : MgRecipeRegister.polymerizer) {
            if (r.matches(i)) {
                return r;
            }
        }
        return null;
    }

    public boolean matches(ItemStack item) {
        return MgUtils.areEqual(item, input, true);
    }

    public boolean matches(FluidStack fluid) {
        return MgUtils.areEqual(fluid, getFluid());
    }

    @Override
    public String toString() {
        return "Polymerizer Recipe, Fluid: " + fluid.getLocalizedName() + ", Item Input: " + input + ", Item Output: " + output + ", Temperature: " + minTemperature;
    }


}
