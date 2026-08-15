package net.lj1o1.foodplus.block.entity;

import net.lj1o1.foodplus.FoodPlus;
import net.lj1o1.foodplus.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FoodPlus.MODID);

    public static final Supplier<BlockEntityType<CountertopBlockEntity>> COUNTERTOP_BE = BLOCK_ENTITIES.register("growth_chamber_be", () -> BlockEntityType.Builder.of(CountertopBlockEntity::new, ModBlocks.COUNTERTOP.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }


}
