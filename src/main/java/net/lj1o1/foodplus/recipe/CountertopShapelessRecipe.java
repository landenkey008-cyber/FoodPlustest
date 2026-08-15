package net.lj1o1.foodplus.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class CountertopShapelessRecipe implements CountertopRecipe {

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final String group;
    private final CraftingBookCategory category;
    private final boolean showNotification;


    public CountertopShapelessRecipe(
            String group,
            CraftingBookCategory category,
            NonNullList<Ingredient> ingredients,
            ItemStack result,
            boolean showNotification
    ) {
        this.group = group;
        this.category = category;
        this.ingredients = ingredients;
        this.result = result;
        this.showNotification = showNotification;
    }


    public CountertopShapelessRecipe(
            String group,
            CraftingBookCategory category,
            NonNullList<Ingredient> ingredients,
            ItemStack result
    ) {
        this(
                group,
                category,
                ingredients,
                result,
                true
        );
    }


    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> inputItems = new ArrayList<>();

        for (ItemStack stack : input.items()) {
            if (!stack.isEmpty()) {
                inputItems.add(stack);
            }
        }

        if (inputItems.size() != this.ingredients.size()) {
            return false;
        }

        return RecipeMatcher.findMatches(
                inputItems,
                this.ingredients
        ) != null;
    }


    @Override
    public ItemStack assemble(
            CraftingInput input,
            HolderLookup.Provider registries
    ) {
        return this.result.copy();
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COUNTERTOP_SHAPELESS_SERIALIZER.get();
    }


    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COUNTERTOP_TYPE.get();
    }


    @Override
    public String getGroup() {
        return this.group;
    }


    public CraftingBookCategory category() {
        return this.category;
    }


    @Override
    public boolean showNotification() {
        return this.showNotification;
    }


    @Override
    public boolean isIncomplete() {
        return this.ingredients.isEmpty()
                || this.ingredients.stream()
                .anyMatch(Ingredient::hasNoItems);
    }


    /*
     * ============================================================
     * SERIALIZER
     * ============================================================
     */

    public static class Serializer
            implements RecipeSerializer<CountertopShapelessRecipe> {

        public static final MapCodec<CountertopShapelessRecipe> CODEC =
                RecordCodecBuilder.mapCodec(instance ->
                        instance.group(

                                Codec.STRING
                                        .optionalFieldOf("group", "")
                                        .forGetter(recipe -> recipe.group),

                                CraftingBookCategory.CODEC
                                        .fieldOf("category")
                                        .orElse(CraftingBookCategory.MISC)
                                        .forGetter(recipe -> recipe.category),

                                Ingredient.CODEC.listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                list -> {
                                                    if (list.size() > 9) {
                                                        return com.mojang.serialization.DataResult.error(
                                                                () -> "Too many ingredients for countertop recipe"
                                                        );
                                                    }

                                                    NonNullList<Ingredient> ingredients =
                                                            NonNullList.withSize(
                                                                    list.size(),
                                                                    Ingredient.EMPTY
                                                            );

                                                    for (int i = 0; i < list.size(); i++) {
                                                        ingredients.set(i, list.get(i));
                                                    }

                                                    return com.mojang.serialization.DataResult.success(
                                                            ingredients
                                                    );
                                                },
                                                ingredients ->
                                                        com.mojang.serialization.DataResult.success(
                                                                java.util.List.copyOf(ingredients)
                                                        )
                                        )
                                        .forGetter(recipe -> recipe.ingredients),

                                ItemStack.STRICT_CODEC
                                        .fieldOf("result")
                                        .forGetter(recipe -> recipe.result),

                                Codec.BOOL
                                        .optionalFieldOf(
                                                "show_notification",
                                                true
                                        )
                                        .forGetter(recipe -> recipe.showNotification)

                        ).apply(instance, CountertopShapelessRecipe::new)
                );


        public static final StreamCodec<
                RegistryFriendlyByteBuf,
                CountertopShapelessRecipe
                > STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        private static CountertopShapelessRecipe fromNetwork(
                RegistryFriendlyByteBuf buffer
        ) {
            String group = buffer.readUtf();

            CraftingBookCategory category =
                    buffer.readEnum(CraftingBookCategory.class);

            int ingredientCount = buffer.readVarInt();

            NonNullList<Ingredient> ingredients =
                    NonNullList.withSize(
                            ingredientCount,
                            Ingredient.EMPTY
                    );

            for (int i = 0; i < ingredientCount; i++) {
                ingredients.set(
                        i,
                        Ingredient.CONTENTS_STREAM_CODEC.decode(buffer)
                );
            }

            ItemStack result =
                    ItemStack.STREAM_CODEC.decode(buffer);

            boolean showNotification =
                    buffer.readBoolean();

            return new CountertopShapelessRecipe(
                    group,
                    category,
                    ingredients,
                    result,
                    showNotification
            );
        }

        private static void toNetwork(
                RegistryFriendlyByteBuf buffer,
                CountertopShapelessRecipe recipe
        ) {
            buffer.writeUtf(recipe.group);

            buffer.writeEnum(recipe.category);

            buffer.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(
                        buffer,
                        ingredient
                );
            }

            ItemStack.STREAM_CODEC.encode(
                    buffer,
                    recipe.result
            );

            buffer.writeBoolean(recipe.showNotification);
        }

        @Override
        public MapCodec<CountertopShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CountertopShapelessRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}