package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
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
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MaltPlantBlock extends BushBlock implements BonemealableBlock
{

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;


    private static final VoxelShape PICKED = Shapes.or(
            Block.box(3.75, 8, 3.5, 12.75, 12.3, 12.5),
            Block.box(1.5, 0, 4, 14.5, 5, 12),
            Block.box(4, 0, 1.5, 12, 5, 14.5),
            Block.box(2.5, 0, 2.5, 13.5, 8, 13.5)
    );

    private static final VoxelShape GROWN = Shapes.or(
            Block.box(3.75, 8, 3.5, 12.75, 12.3, 12.5),
            Block.box(1.5, 0, 4, 14.5, 5, 12),
            Block.box(4, 0, 1.5, 12, 5, 14.5),
            Block.box(2.5, 0, 2.5, 13.5, 8, 13.5),
            Block.box(1.75, 13.375, -1.75, 4, 15.625, -0.75),
            Block.box(1.75, 13.375, -1, 6.25, 15.625, 0),
            Block.box(4, 13.375, -0.25, 7, 15.625, 0.75),
            Block.box(5.5, 13.375, 14.75, 6.5, 15.625, 15.75),
            Block.box(5.5, 13.375, 0.5, 6.5, 15.625, 1.5),
            Block.box(6.25, 13.375, 0.5, 10, 15.625, 2),
            Block.box(11.5, 13.375, -1, 14.5, 15.625, 0.5),
            Block.box(10, 13.375, -0.25, 11.5, 15.625, 1.25),
            Block.box(14.5, 13.375, -0.25, 16, 15.625, 2.75),
            Block.box(13.75, 13.375, 2.75, 15.25, 15.625, 4.25),
            Block.box(13, 13.375, 11.75, 14, 15.625, 12.75),
            Block.box(13, 13.375, 3.25, 14, 15.625, 4.25),
            Block.box(12.25, 13.375, 4, 14.5, 15.625, 5),
            Block.box(11.5, 13.375, 4.75, 13.75, 15.625, 5.75),
            Block.box(10.75, 13.375, 5.5, 13, 15.625, 6.5),
            Block.box(10, 13.375, 6.25, 12.25, 15.625, 7.25),
            Block.box(-6.5, 13.375, 7.25, -4.25, 15.625, 8.75),
            Block.box(-5.75, 13.375, 6.5, -3.5, 15.625, 7.5),
            Block.box(-5, 13.375, 5.75, -2.75, 15.625, 6.75),
            Block.box(-4.25, 13.375, 5, -0.5, 15.625, 6),
            Block.box(1, 13.375, -2.5, 2.5, 15.625, -1.5),
            Block.box(0.25, 13.375, -1.75, 1.75, 15.625, 3.5),
            Block.box(-2.75, 13.375, 4.25, 1, 15.625, 5.25),
            Block.box(-0.5, 13.375, 3.5, 1.75, 15.625, 4.5),
            Block.box(-0.5, 13.375, 11.5, 1.75, 15.625, 12.5),
            Block.box(-2.75, 13.375, 10.75, 1, 15.625, 11.75),
            Block.box(0.25, 13.375, 12.5, 1.75, 15.625, 17.75),
            Block.box(1, 13.375, 17.5, 2.5, 15.625, 18.5),
            Block.box(-4.25, 13.375, 10, -0.5, 15.625, 11),
            Block.box(-5, 13.375, 9.25, -2.75, 15.625, 10.25),
            Block.box(10, 13.375, 7.25, 11.5, 15.625, 8.75),
            Block.box(10, 13.375, 8.75, 12.25, 15.625, 9.75),
            Block.box(10.75, 13.375, 9.5, 13, 15.625, 10.5),
            Block.box(11.5, 13.375, 10.25, 13.75, 15.625, 11.25),
            Block.box(12.25, 13.375, 11, 14.5, 15.625, 12),
            Block.box(13.75, 13.375, 11.75, 15.25, 15.625, 13.25),
            Block.box(14.5, 13.375, 13.25, 16, 15.625, 16.25),
            Block.box(10, 13.375, 14.75, 11.5, 15.625, 16.25),
            Block.box(11.5, 13.375, 15.5, 14.5, 15.625, 17),
            Block.box(6.25, 13.375, 14, 10, 15.625, 15.5),
            Block.box(4, 13.375, 15.5, 7, 15.625, 16.5),
            Block.box(1.75, 13.375, 16, 6.25, 15.625, 17),
            Block.box(1.75, 13.375, 16.75, 4, 15.625, 17.75),
            Block.box(-5.75, 13.375, 8.5, -3.5, 15.625, 9.5),
            Block.box(-0.5, 10.875, 5.5, 1, 11.875, 6.5),
            Block.box(2.5, 10.875, 0.5, 4.75, 11.875, 2),
            Block.box(1.75, 10.375, 2, 10.75, 11.875, 5.75),
            Block.box(-0.5, 10.875, 9.5, 1, 11.875, 10.5),
            Block.box(2.5, 10.875, 14, 4.75, 11.875, 15.5),
            Block.box(10.75, 10.875, 2, 13, 11.875, 3.5),
            Block.box(10.75, 10.875, 12.5, 13, 11.875, 14),
            Block.box(1, 10.375, 5.75, 10, 11.875, 10.25),
            Block.box(1.75, 10.375, 10.25, 10.75, 11.875, 14),
            Block.box(-3.5, 10.625, 6.5, 1, 11.875, 9.5),
            Block.box(1, 11.875, 1.25, 13, 14.875, 5.75),
            Block.box(11.5, 11.875, 0.5, 14.5, 14.875, 3.5),
            Block.box(1.75, 11.875, -0.25, 5.5, 14.875, 1.25),
            Block.box(-0.5, 11.875, 4.75, 1, 14.875, 5.75),
            Block.box(-0.5, 11.875, 10.25, 1, 14.875, 11.25),
            Block.box(1.75, 11.875, 14.75, 5.5, 14.875, 16.25),
            Block.box(11.5, 11.875, 12.5, 14.5, 14.875, 15.5),
            Block.box(1, 11.875, 10.25, 13, 14.875, 14.75),
            Block.box(-4.25, 11.875, 5.75, 10.75, 14.875, 10.25)
    );



    public MaltPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.MALT_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
       return PICKED;
    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 1;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        int i = state.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,rand.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hint) {
        int i = state.getValue(AGE);
        boolean flag = i == 1;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i == 1) {

            popResource(world, pos, new ItemStack(ModItems.MALT_STAR.get(), 1 + world.random.nextInt(1)));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
    }

    //ValidGround
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

//Rotation
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

