package net.lj1o1.foodplus.block.custom;

import com.mojang.serialization.MapCodec;
import net.lj1o1.foodplus.screen.custom.CounterTopMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CountertopBlock extends Block {
    public static final MapCodec<CountertopBlock> CODEC = simpleCodec(CountertopBlock::new);
    private static final Component CONTAINER_COUNTERTOP = Component.translatable("Countertop");


    public CountertopBlock(BlockBehaviour.Properties properties) {
        super(properties);

    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (p_52229_, p_52230_, p_52231_) -> new CounterTopMenu(p_52229_, p_52230_, ContainerLevelAccess.create(level, pos)), CONTAINER_COUNTERTOP
        );
    }


}