package net.the_goldbeards.lootdebugs.Block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;

public class TestBlock extends Block {

    public static final BooleanProperty ISPLAYING = BooleanProperty.create("isplaying");



    public TestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ISPLAYING, false));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            if(pHand == InteractionHand.MAIN_HAND) {
                boolean currentState = pState.getValue(ISPLAYING);
                if (!currentState) {

                    pLevel.playSound(pPlayer, pPos, ModSounds.RICKROLL.get(), SoundSource.BLOCKS, 10f, 1f);
                }
                pLevel.setBlock(pPos, pState.setValue(ISPLAYING, !currentState), 3);
            }
        return InteractionResult.SUCCESS;




    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ISPLAYING);
    }


}
