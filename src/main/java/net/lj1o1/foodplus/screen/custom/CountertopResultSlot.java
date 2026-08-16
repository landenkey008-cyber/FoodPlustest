package net.lj1o1.foodplus.screen.custom;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;

public class CountertopResultSlot extends ResultSlot {

    private final CraftingContainer craftSlots;

    public CountertopResultSlot(
            Player player,
            CraftingContainer craftSlots,
            Container resultContainer,
            int slot,
            int x,
            int y
    ) {
        super(
                player,
                craftSlots,
                resultContainer,
                slot,
                x,
                y
        );

        this.craftSlots = craftSlots;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);

        // Consume one item from every occupied crafting slot
        for (int i = 0; i < craftSlots.getContainerSize(); i++) {

            ItemStack ingredient = craftSlots.getItem(i);

            if (!ingredient.isEmpty()) {
                craftSlots.removeItem(i, 1);
            }
        }
    }
}