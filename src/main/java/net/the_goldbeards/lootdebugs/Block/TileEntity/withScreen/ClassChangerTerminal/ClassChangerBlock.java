package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.ClassChangerTerminal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ClassChangerBlock extends BaseEntityBlock  {


    public static final IntegerProperty LAST_INTERACTED_CLASS = IntegerProperty.create("last_interacted_class", 0, 4);

    public static VoxelShape NORTH = Shapes.or(
            Shapes.or(
                    Block.box(2.25, 0, 0, 13.75, 1.25, 6.75),
                    Block.box(1.75, 0, 8, 14.25, 5, 15.5),
                    Block.box(0.25, 5.75, 10.25, 15.5, 15, 13.25),
                    Block.box(1.75, 4.75, 10.25, 14.25, 6, 12.25)
            )
    );

    public static VoxelShape EAST = Shapes.or(
            Block.box(9.249999999999998, 0, 2.1750000000000003, 15.999999999999998, 1.25, 13.674999999999999),
            Block.box(0.5, 0, 1.6750000000000003, 7.999999999999998, 5, 14.174999999999999),
            Block.box(2.7500000000000004, 5.75, 0.17500000000000002, 5.749999999999998, 15, 15.424999999999999),
            Block.box(3.7500000000000004, 4.75, 1.6750000000000003, 5.749999999999998, 6, 14.174999999999999)
    );

    public static VoxelShape SOUTH = Shapes.or(
            Block.box(2.25, 0, 9.25, 13.75, 1.25, 16),
            Block.box(1.75, 0, 0.5, 14.25, 5, 8),
            Block.box(0.25, 5.75, 2.75, 15.5, 15, 5.75),
            Block.box(1.75, 4.75, 3.75, 14.25, 6, 5.75)
    );

    public static VoxelShape WEST = Shapes.or(
            Block.box(1.7763568394002505e-15, 0, 2.1750000000000003, 6.750000000000002, 1.25, 13.674999999999999),
            Block.box(8.000000000000002, 0, 1.6750000000000003, 15.5, 5, 14.174999999999999),
            Block.box(10.250000000000002, 5.75, 0.17500000000000002, 13.25, 15, 15.424999999999999),
            Block.box(10.250000000000002, 4.75, 1.6750000000000003, 12.25, 6, 14.174999999999999)
    );

    public ClassChangerBlock(Properties properties)
    {
        super(properties);
    }
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return NORTH;

            case EAST:
                return EAST;

            case WEST:
                return WEST;

            case SOUTH:
                return SOUTH;

            default:
                return NORTH;
        }
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide()) {

            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if(entity instanceof ClassChangerTile)
            {
                    NetworkHooks.openGui(((ServerPlayer) pPlayer), (ClassChangerTile) entity, pPos);

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

        @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new ClassChangerTile(pPos,pState);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    @javax.annotation.Nullable
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

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {

        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ClassChangerTile) {
                pLevel.removeBlockEntity(pPos);
                pLevel.removeBlock(pPos, false);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below(1)).isFaceSturdy(pLevel, pPos, Direction.UP);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LAST_INTERACTED_CLASS);
    }
}
