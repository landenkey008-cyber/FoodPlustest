package net.lj1o1.foodplus.block.entity;

import net.lj1o1.foodplus.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CountertopBlockEntity extends BlockEntity implements MenuProvider {

   public final ItemStackHandler itemHandler = new ItemStackHandler(10) {
       @Override
       protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
               level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
           }
        }
    };

    private static final int INPUT_1 = 0;
   private static final int INPUT_2 = 1;
   private static final int INPUT_3 = 2;
   private static final int INPUT_4 = 3;
   private static final int INPUT_5 = 4;
   private static final int INPUT_6 = 5;
   private static final int INPUT_7 = 6;
    private static final int INPUT_8 = 7;
   private static final int OUTPUT = 9;


  public CountertopBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COUNTERTOP_BE.get(), pos, blockState);
   }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.foodplus.countertop_2");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
       for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

   public void tick(Level level1, BlockPos blockPos, BlockState blockState) {

        if(hasRecipe()) {
            craftItem();
           }

       }

   private void craftItem() {
        ItemStack output = new ItemStack(ModItems.TOAST.get(), 2);

       itemHandler.extractItem(INPUT_1, 1, false);
       itemHandler.setStackInSlot(OUTPUT, new ItemStack(output.getItem(),
              itemHandler.getStackInSlot(OUTPUT).getCount() + output.getCount()));

    }

    private boolean hasRecipe() {
       ItemStack output = new ItemStack(ModItems.TOAST.get(), 2);

       return itemHandler.getStackInSlot(INPUT_1).is(ModItems.BREAD_SLICE) &&
                canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
   }

   private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT).isEmpty() ||
               itemHandler.getStackInSlot(OUTPUT).getItem() == output.getItem();
   }

   private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT).getMaxStackSize();
       int currentCount = itemHandler.getStackInSlot(OUTPUT).getCount();

       return maxCount >= currentCount + count;
    }


    @Override
   public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
   public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
   }

}