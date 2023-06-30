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

public class StarchPlantBlock extends BushBlock implements BonemealableBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }


    private static final VoxelShape N_PICKED =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(6.5, 22, 17.75, 9.5, 25.25, 19.75),
            Block.box(6.5, 18, 8.25, 9.5, 24, 9.75),
            Block.box(6.5, 23, 9.75, 9.5, 25, 13.75),
            Block.box(6.5, 24, 13.75, 9.5, 26, 17.75)
            );
    private static final VoxelShape E_PICKED =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(-3.75, 22, 6.5, -1.75, 25.25, 9.5),
            Block.box(6.25, 18, 6.5, 7.75, 24, 9.5),
            Block.box(2.25, 23, 6.5, 6.25, 25, 9.5),
            Block.box(-1.75, 24, 6.5, 2.25, 26, 9.5)

    );
    private static final VoxelShape S_PICKED =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(6.5, 22, -3.75, 9.5, 25.25, -1.75),
            Block.box(6.5, 18, 6.25, 9.5, 24, 7.75),
            Block.box(6.5, 23, 2.25, 9.5, 25, 6.25),
            Block.box(6.5, 24, -1.75, 9.5, 26, 2.25)
    );

    private static final VoxelShape W_PICKED =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(17.75, 22, 6.5, 19.75, 25.25, 9.5),
            Block.box(8.25, 18, 6.5, 9.75, 24, 9.5),
            Block.box(9.75, 23, 6.5, 13.75, 25, 9.5),
            Block.box(13.75, 24, 6.5, 17.75, 26, 9.5)
    );



    //Grown
    private static final VoxelShape N_GROWN =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(10.375, 12.25, 17.15, 11.875, 19.75, 21.65),
            Block.box(4.375, 12.25, 17.15, 5.875, 19.75, 21.65),
            Block.box(5.875, 12.25, 21.65, 10.375, 19.75, 23.15),
            Block.box(5.875, 12.25, 15.65, 10.375, 19.75, 17.15),
            Block.box(5.875, 10.75, 17.15, 10.375, 13.75, 21.65),
            Block.box(6.625, 9.25, 17.9, 9.625, 12.25, 20.9),
            Block.box(4.9590700000000005, 19.16969, 16.4, 10.95907, 22.16969, 22.4),
            Block.box(3.4590700000000005, 16.16969, 14.9, 12.45907, 20.66969, 23.9),
            Block.box(6.35, 19.74133, 18, 9.85, 21.24133, 21.5),
            Block.box(6.5, 22, 17.75, 9.5, 25.25, 19.75),
            Block.box(6.5, 18, 8.25, 9.5, 24, 9.75),
            Block.box(6.5, 23, 9.75, 9.5, 25, 13.75),
            Block.box(6.5, 24, 13.75, 9.5, 26, 17.75)
    );

    private static final VoxelShape E_GROWN =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(-5.649999999999999, 12.25, 10.375, -1.1499999999999986, 19.75, 11.875),
            Block.box(-5.649999999999999, 12.25, 4.375, -1.1499999999999986, 19.75, 5.875),
            Block.box(-7.149999999999999, 12.25, 5.875, -5.649999999999999, 19.75, 10.375),
            Block.box(-1.1499999999999986, 12.25, 5.875, 0.34999999999999964, 19.75, 10.375),
            Block.box(-5.649999999999999, 10.75, 5.875, -1.1499999999999986, 13.75, 10.375),
            Block.box(-4.899999999999999, 9.25, 6.625, -1.8999999999999986, 12.25, 9.625),
            Block.box(-6.399999999999999, 19.16969, 4.9590700000000005, -0.3999999999999986, 22.16969, 10.95907),
            Block.box(-7.899999999999999, 16.16969, 3.4590700000000005, 1.0999999999999996, 20.66969, 12.45907),
            Block.box(-5.5, 19.74133, 6.35, -2, 21.24133, 9.85),
            Block.box(-3.75, 22, 6.5, -1.75, 25.25, 9.5),
            Block.box(6.25, 18, 6.5, 7.75, 24, 9.5),
            Block.box(2.25, 23, 6.5, 6.25, 25, 9.5),
            Block.box(-1.75, 24, 6.5, 2.25, 26, 9.5)
    );
    private static final VoxelShape S_GROWN =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(10.375, 12.25, -5.65, 11.875, 19.75, -1.1500000000000004),
            Block.box(4.375, 12.25, -5.65, 5.875, 19.75, -1.1500000000000004),
            Block.box(5.875, 12.25, -7.15, 10.375, 19.75, -5.65),
            Block.box(5.875, 12.25, -1.1500000000000004, 10.375, 19.75, 0.34999999999999964),
            Block.box(5.875, 10.75, -5.65, 10.375, 13.75, -1.1500000000000004),
            Block.box(6.625, 9.25, -4.9, 9.625, 12.25, -1.9000000000000004),
            Block.box(4.9590700000000005, 19.16969, -6.4, 10.95907, 22.16969, -0.40000000000000036),
            Block.box(3.4590700000000005, 16.16969, -7.9, 12.45907, 20.66969, 1.0999999999999996),
            Block.box(6.35, 19.74133, -5.5, 9.85, 21.24133, -2),
            Block.box(6.5, 22, -3.75, 9.5, 25.25, -1.75),
            Block.box(6.5, 18, 6.25, 9.5, 24, 7.75),
            Block.box(6.5, 23, 2.25, 9.5, 25, 6.25),
            Block.box(6.5, 24, -1.75, 9.5, 26, 2.25)
    );
    private static final VoxelShape W_GROWN =Shapes.or(
            Block.box(4.25, 13.75, 4.25, 11.75, 18.25, 11.75),
            Block.box(5, 4.75, 5, 11, 15.25, 11),
            Block.box(4.25, 3.25, 4.25, 11.75, 6.25, 11.75),
            Block.box(2.75, 0.25, 2.75, 13.25, 4.75, 13.25),
            Block.box(17.15, 12.25, 10.375, 21.65, 19.75, 11.875),
            Block.box(17.15, 12.25, 4.375, 21.65, 19.75, 5.875),
            Block.box(21.65, 12.25, 5.875, 23.15, 19.75, 10.375),
            Block.box(15.65, 12.25, 5.875, 17.15, 19.75, 10.375),
            Block.box(17.15, 10.75, 5.875, 21.65, 13.75, 10.375),
            Block.box(17.9, 9.25, 6.625, 20.9, 12.25, 9.625),
            Block.box(16.4, 19.16969, 4.9590700000000005, 22.4, 22.16969, 10.95907),
            Block.box(14.9, 16.16969, 3.4590700000000005, 23.9, 20.66969, 12.45907),
            Block.box(18, 19.74133, 6.35, 21.5, 21.24133, 9.85),
            Block.box(17.75, 22, 6.5, 19.75, 25.25, 9.5),
            Block.box(8.25, 18, 6.5, 9.75, 24, 9.5),
            Block.box(9.75, 23, 6.5, 13.75, 25, 9.5),
            Block.box(13.75, 24, 6.5, 17.75, 26, 9.5)
    );



    public StarchPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.STARCH_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {

        if (state.getValue(FACING) == Direction.NORTH && state.getValue(AGE) == 0) {//NORTH
            return N_PICKED;
        }
        else if(state.getValue(FACING) == Direction.NORTH && state.getValue(AGE) == 1){
            return N_GROWN;
        }
        else if(state.getValue(FACING) == Direction.EAST && state.getValue(AGE) == 0){//EAST
            return E_PICKED;
        }
        else if(state.getValue(FACING) == Direction.EAST && state.getValue(AGE) == 1){
            return E_GROWN;
        }
        else if(state.getValue(FACING) == Direction.SOUTH && state.getValue(AGE) == 0){//SOUTH
            return S_PICKED;
        }
        else if(state.getValue(FACING) == Direction.SOUTH && state.getValue(AGE) == 1){
            return S_GROWN;
        }
        else if(state.getValue(FACING) == Direction.WEST && state.getValue(AGE) == 0){//WEST
            return W_PICKED;
        }
        else if(state.getValue(FACING) == Direction.WEST && state.getValue(AGE) == 1){
            return W_GROWN;
        }
        else
        {
            return Shapes.empty();
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

            popResource(world, pos, new ItemStack(ModItems.STARCH_NUT.get(), 1));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE, FACING);
    }


}
