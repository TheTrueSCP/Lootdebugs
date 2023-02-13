package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;

import net.minecraft.world.phys.shapes.VoxelShape;

import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;

import java.util.Random;

public class BarleyPlantBlock extends BushBlock implements BonemealableBlock
{

    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    private static final VoxelShape GROWN = Shapes.or(
            Block.box(6.5, 0, 6.5, 9.5, 5, 9.5),
            Block.box(7, 4, 6, 9, 12, 8),
            Block.box(7, 11, 5.5, 9, 16, 7.5),
            Block.box(6.5, 14.5, 5.5, 9.5, 18.5, 8.5),
            Block.box(6.5, 17.5, 5.5, 9.5, 21.5, 8.5),
            Block.box(7.5, 18.5, 6.5, 8.5, 22.5, 7.5),
            Block.box(6, 16.5, 5, 10, 20.5, 9),
            Block.box(6.5, 14, 7, 9.5, 14, 10),
            Block.box(6.5, 14, 4, 9.5, 14, 7),
            Block.box(5, 14, 5.5, 8, 14, 8.5),
            Block.box(8, 14, 5.5, 11, 14, 8.5)
    );

    private static final VoxelShape PICKED =  Shapes.or(
            Block.box(6.5, 0, 6.5, 9.5, 5, 9.5),
            Block.box(7, 4, 6, 9, 12, 8),
            Block.box(7, 11, 5.5, 9, 15, 7.5),
            Block.box(6.5, 14.5, 5.5, 9.5, 15.5, 8.5),
            Block.box(6.5, 14, 7, 9.5, 14, 10),
            Block.box(6.5, 14, 4, 9.5, 14, 7),
            Block.box(5, 14, 5.5, 8, 14, 8.5),
            Block.box(8, 14, 5.5, 11, 14, 8.5)
    );



    public BarleyPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModItems.BARLEY_BULB.get());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) == 0) {
            return PICKED;
        } else {
            return GROWN;
        }
    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 1;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        int i = pState.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,pRandom.nextInt(5) == 0)) {
            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
        }

    }



    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hint) {
        int i = state.getValue(AGE);
        boolean flag = i == 1;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i == 1) {


            popResource(world, pos, new ItemStack(ModItems.BARLEY_BULB.get(), 1));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) ||
                state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.PODZOL) ||
                state.is(Blocks.STONE) ||
                state.is(Blocks.CLAY) ||
                state.is(Blocks.MOSS_BLOCK) ||
                state.is(Blocks.COBBLED_DEEPSLATE) ||
                state.is(Blocks.COBBLESTONE) ||
                state.is(Blocks.DEEPSLATE) ||
                state.is(Blocks.GRAVEL) ||
                state.is(Blocks.ANDESITE) ||
                state.is(Blocks.GRANITE);
    }
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }


    //Bonemeal
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 1;
    }

    public boolean isBonemealSuccess(Level pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pPos, BlockState pState) {

            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(1)), 2);
    }
}

