package net.lj1o1.foodplus.recipe;

import net.lj1o1.foodplus.FoodPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, FoodPlus.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, FoodPlus.MODID);



    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CountertopShapedRecipe>> COUNTERTOP_SHAPED_SERIALIZER =
            SERIALIZERS.register("countertop_shaped", CountertopShapedRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CountertopShapelessRecipe>> COUNTERTOP_SHAPELESS_SERIALIZER =
            SERIALIZERS.register("countertop_shapeless", CountertopShapelessRecipe.Serializer::new);



    public static final DeferredHolder<RecipeType<?>, RecipeType<CountertopRecipe>> COUNTERTOP_TYPE =
            TYPES.register("countertop", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "countertop";
                }
            });




    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
