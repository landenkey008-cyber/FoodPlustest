package net.lj1o1.foodplus.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SaltBlock extends FallingBlock {

    public static final MapCodec<SaltBlock> CODEC = simpleCodec(SaltBlock::new);

    public SaltBlock(BlockBehaviour.Properties properties) {
        super(properties);

    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos){
        return 40248255;
    }
}
