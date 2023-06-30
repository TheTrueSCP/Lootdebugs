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


    private static final VoxelShape SHAPE_N_S = Shapes.or(
            Block.box(3.5, 8, 3.5, 12.5, 12.3, 12.5),
            Block.box(1.5, 0, 4, 14.5, 5, 12),
            Block.box(4, 0, 1.5, 12, 5, 14.5),
            Block.box(2.5, 0, 2.5, 13.5, 8, 13.5)

    );

    private static final VoxelShape EAST = Shapes.or(
            Block.box(3.625, 8, 3.625, 12.625, 12.3, 12.625),
            Block.box(4.125, 0, 1.375, 12.125, 5, 14.375),
            Block.box(1.625, 0, 3.875, 14.625, 5, 11.875),
            Block.box(2.625, 0, 2.375, 13.625, 8, 13.375)
    );

    private static final VoxelShape WEST = Shapes.or(
            Block.box(3.625, 8, 3.625, 12.625, 12.3, 12.625),
            Block.box(4.125, 0, 1.375, 12.125, 5, 14.375),
            Block.box(1.625, 0, 3.875, 14.625, 5, 11.875),
            Block.box(2.625, 0, 2.375, 13.625, 8, 13.375)
    );




    public MaltPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.MALT_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {

        return SHAPE_N_S;
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

            popResource(world, pos, new ItemStack(ModItems.MALT_STAR.get(), 1));
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

