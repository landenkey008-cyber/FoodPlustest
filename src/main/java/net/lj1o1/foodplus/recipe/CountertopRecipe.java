package net.lj1o1.foodplus.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;

public interface CountertopRecipe extends Recipe<CraftingInput> {
    @Override
    default NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining =
                NonNullList.withSize(input.size(), ItemStack.EMPTY);

        return remaining;
    }
}
