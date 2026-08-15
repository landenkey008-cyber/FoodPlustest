package net.lj1o1.foodplus.block;

import net.lj1o1.foodplus.FoodPlus;
import net.lj1o1.foodplus.block.custom.CountertopBlock;
import net.lj1o1.foodplus.block.custom.CountertopPanel;
import net.lj1o1.foodplus.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(FoodPlus.MODID);

    //Registers the Countertop Block
    public static final DeferredBlock<net.minecraft.world.level.block.Block> COUNTERTOP = registerBlock("countertop", () -> new CountertopBlock(BlockBehaviour.Properties.of()
            .strength(3.5f).sound(SoundType.BASALT).destroyTime(1f).requiresCorrectToolForDrops()));
    //Registers the Countertop Block Panel
    public static final DeferredBlock<net.minecraft.world.level.block.Block> COUNTERTOP_PANEL = registerBlock("countertop_panel", () -> new CountertopPanel(BlockBehaviour.Properties.of()
            .strength(3.5f).sound(SoundType.BASALT).destroyTime(1f).requiresCorrectToolForDrops()));

    //Other Blocks
    public static final DeferredBlock<Block> SALT_BLOCK = registerBlock("salt_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(1.5f)));


    private static <T extends net.minecraft.world.level.block.Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }



    private static <T extends net.minecraft.world.level.block.Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }
}

