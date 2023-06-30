package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;

import javax.annotation.Nullable;
import java.util.Random;

public class YeastPlantBlock extends BushBlock implements BonemealableBlock
{

    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    private static final VoxelShape NS_PICKED = Shapes.or(//North_South_Grown
            Block.box(11, 3, 1.25, 14, 9, 7.25),
            Block.box(11, 7.5, 2.75, 14, 23.25, 5.75),
            Block.box(14, 3, 2.75, 15.5, 9, 5.75),
            Block.box(9.5, 3, 2.75, 11, 9, 5.75),
            Block.box(0.5, 3, 10.25, 2, 9, 13.25),
            Block.box(2, 3, 8.75, 5, 9, 14.75),
            Block.box(2, 7.5, 10.25, 5, 23.25, 13.25),
            Block.box(2, 20.25, 2.2426399999999997, 5, 23.25, 10.24264),
            Block.box(11, 20.25, 5.75, 14, 23.25, 14),
            Block.box(2, 14.75, -0.75, 5, 23.25, 2.25),
            Block.box(11, 15.875, 13.45, 14, 23.374999999999996, 16.45),
            Block.box(5, 3, 10.25, 6.5, 9, 13.25),
            Block.box(1.25, 0, 8, 5.75, 4.5, 15.5),
            Block.box(-0.25, 0, 9.5, 2.75, 4.5, 14),
            Block.box(4.25, 0, 9.5, 7.25, 4.5, 14),
            Block.box(8.75, 0, 2, 11.75, 4.5, 6.5),
            Block.box(10.25, 0, 0.5, 14.75, 4.5, 8),
            Block.box(13.25, 0, 2, 16.25, 4.5, 6.5)
    );

    private static final VoxelShape NS_GROWN = Shapes.or(//North_South_Grown
            Block.box(11, 3, 1.25, 14, 9, 7.25),
            Block.box(11, 7.5, 2.75, 14, 23.25, 5.75),
            Block.box(14, 3, 2.75, 15.5, 9, 5.75),
            Block.box(9.5, 3, 2.75, 11, 9, 5.75),
            Block.box(0.5, 3, 10.25, 2, 9, 13.25),
            Block.box(2, 3, 8.75, 5, 9, 14.75),
            Block.box(2, 7.5, 10.25, 5, 23.25, 13.25),
            Block.box(2, 20.25, 2.2426399999999997, 5, 23.25, 10.24264),
            Block.box(11, 20.25, 5.75, 14, 23.25, 14),
            Block.box(2, 14.75, -0.75, 5, 23.25, 2.25),
            Block.box(11, 15.875, 13.45, 14, 23.374999999999996, 16.45),
            Block.box(5, 3, 10.25, 6.5, 9, 13.25),
            Block.box(1.25, 0, 8, 5.75, 4.5, 15.5),
            Block.box(-0.25, 0, 9.5, 2.75, 4.5, 14),
            Block.box(4.25, 0, 9.5, 7.25, 4.5, 14),
            Block.box(8.75, 0, 2, 11.75, 4.5, 6.5),
            Block.box(10.25, 0, 0.5, 14.75, 4.5, 8),
            Block.box(13.25, 0, 2, 16.25, 4.5, 6.5),
            Block.box(1.25, 7, -1.75, 5.75, 16.5, 2.75),
            Block.box(10.25, 7.25, 12.875, 14.75, 16.5, 17.375)
    );

    public static final VoxelShape EW_PICKED = Shapes.or(
            Block.box(8.75, 3, 11, 14.75, 9, 14),
            Block.box(10.25, 7.5, 11, 13.25, 23.25, 14),
            Block.box(10.25, 3, 14, 13.25, 9, 15.5),
            Block.box(10.25, 3, 9.5, 13.25, 9, 11),
            Block.box(2.75, 3, 0.5, 5.75, 9, 2),
            Block.box(1.25, 3, 2, 7.25, 9, 5),
            Block.box(2.75, 7.5, 2, 5.75, 23.25, 5),
            Block.box(5.75736, 20.25, 2, 13.75736, 23.25, 5),
            Block.box(2, 20.25, 11, 10.25, 23.25, 14),
            Block.box(13.75, 14.75, 2, 16.75, 23.25, 5),
            Block.box(-0.4499999999999993, 15.875, 11, 2.5500000000000007, 23.374999999999996, 14),
            Block.box(2.75, 3, 5, 5.75, 9, 6.5),
            Block.box(0.5, 0, 1.25, 8, 4.5, 5.75),
            Block.box(2, 0, -0.25, 6.5, 4.5, 2.75),
            Block.box(2, 0, 4.25, 6.5, 4.5, 7.25),
            Block.box(9.5, 0, 8.75, 14, 4.5, 11.75),
            Block.box(8, 0, 10.25, 15.5, 4.5, 14.75),
            Block.box(9.5, 0, 13.25, 14, 4.5, 16.25)
    );

    public static final VoxelShape EW_GROWN = Shapes.or(
            Block.box(8.75, 3, 11, 14.75, 9, 14),
            Block.box(10.25, 7.5, 11, 13.25, 23.25, 14),
            Block.box(10.25, 3, 14, 13.25, 9, 15.5),
            Block.box(10.25, 3, 9.5, 13.25, 9, 11),
            Block.box(2.75, 3, 0.5, 5.75, 9, 2),
            Block.box(1.25, 3, 2, 7.25, 9, 5),
            Block.box(2.75, 7.5, 2, 5.75, 23.25, 5),
            Block.box(5.75736, 20.25, 2, 13.75736, 23.25, 5),
            Block.box(2, 20.25, 11, 10.25, 23.25, 14),
            Block.box(13.75, 14.75, 2, 16.75, 23.25, 5),
            Block.box(-0.4499999999999993, 15.875, 11, 2.5500000000000007, 23.374999999999996, 14),
            Block.box(2.75, 3, 5, 5.75, 9, 6.5),
            Block.box(0.5, 0, 1.25, 8, 4.5, 5.75),
            Block.box(2, 0, -0.25, 6.5, 4.5, 2.75),
            Block.box(2, 0, 4.25, 6.5, 4.5, 7.25),
            Block.box(9.5, 0, 8.75, 14, 4.5, 11.75),
            Block.box(8, 0, 10.25, 15.5, 4.5, 14.75),
            Block.box(9.5, 0, 13.25, 14, 4.5, 16.25),
            Block.box(13.25, 7, 1.25, 17.75, 16.5, 5.75),
            Block.box(-1.375, 7.25, 10.25, 3.125, 16.5, 14.75)
        );

    public YeastPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.YEAST_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACING) == Direction.NORTH  && state.getValue(AGE) == 0 || state.getValue(FACING) == Direction.SOUTH  && state.getValue(AGE) == 0) {//If the Rotation is North or South and is not Grown
            return NS_PICKED;
        }
        else if(state.getValue(FACING) == Direction.NORTH  && state.getValue(AGE) == 1 || state.getValue(FACING) == Direction.SOUTH  && state.getValue(AGE) == 1)//If the Rotation is North or South and is Grown
        {
            return NS_GROWN;
        }
        else if(state.getValue(FACING) == Direction.EAST  && state.getValue(AGE) == 0 || state.getValue(FACING) == Direction.WEST  && state.getValue(AGE) == 0)//If the Rotation is East or West and is not Grown
        {
            return EW_PICKED;
        }
        else if(state.getValue(FACING) == Direction.EAST  && state.getValue(AGE) == 1 || state.getValue(FACING) == Direction.WEST  && state.getValue(AGE) == 1)//If the Rotation is East or West and is Grown
        {
            return EW_GROWN;
        }
        else//That Should not Happen
        {
            throw new IllegalStateException("!Warning! The Voxelshape can't be Provided because the States from the Block are faulty");
        }

    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 1;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        int i = pState.getValue(AGE);
        if (i < 1 && pRandom.nextInt(0, 10) == 5) {
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

            popResource(world, pos, new ItemStack(ModItems.YEAST_CONE.get(), 2));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
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
        if (pState.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return pLevel.getBlockState(blockpos).canSustainPlant(pLevel, blockpos, Direction.UP, this);
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
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

    //Builder
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE, FACING);
    }
}

