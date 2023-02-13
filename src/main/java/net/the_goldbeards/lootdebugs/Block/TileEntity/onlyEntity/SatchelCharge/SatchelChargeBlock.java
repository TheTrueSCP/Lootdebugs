package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class SatchelChargeBlock extends BaseEntityBlock {

    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");




    public static final VoxelShape FLOOR_X = Shapes.or(
            Block.box(6.15, 3.5, 6.625, 9.85, 4.5, 9.375),
            Block.box(6.15, -0.5, 6.625, 9.85, 0.5, 9.375),
            Block.box(9.75, -0.25, 3.75, 11.5, 4.25, 12.25),
            Block.box(4.5, -0.25, 3.75, 6.25, 4.25, 12.25),
            Block.box(3.5, 0, 8.05, 12.5, 4, 12.05),
            Block.box(3.5, 0, 3.95, 12.5, 4, 7.95),
            Block.box(7, 3.25, 9.3, 9, 4.25, 11.3),
            Block.box(7, 3.25, 4.7, 9, 4.25, 6.7),
            Block.box(7, -0.25, 9.3, 9, 0.75, 11.3),
            Block.box(7, -0.25, 4.7, 9, 0.75, 6.7),
            Block.box(7.5, 3.75, 5.25, 8.5, 4.75, 6.25),
            Block.box(7.5, 3.75, 9.75, 8.5, 4.75, 10.75),
            Block.box(7.5, -0.75, 5.25, 8.5, 0.25, 6.25),
            Block.box(7.5, -0.75, 9.75, 8.5, 0.25, 10.75),
            Block.box(0.5, 2, 4.5, 4.5, 2, 11.5),
            Block.box(11.5, 2, 4.5, 15.5, 2, 11.5)
    );

    public static final VoxelShape FLOOR_Z = Shapes.or(
            Block.box(6.625, 3.5, 6.15, 9.375, 4.5, 9.85),
            Block.box(6.625, -0.5, 6.15, 9.375, 0.5, 9.85),
            Block.box(3.75, -0.25, 4.5, 12.25, 4.25, 6.25),
            Block.box(3.75, -0.25, 9.75, 12.25, 4.25, 11.5),
            Block.box(8.05, 0, 3.5, 12.05, 4, 12.5),
            Block.box(3.95, 0, 3.5, 7.95, 4, 12.5),
            Block.box(9.3, 3.25, 7, 11.3, 4.25, 9),
            Block.box(4.7, 3.25, 7, 6.7, 4.25, 9),
            Block.box(9.3, -0.25, 7, 11.3, 0.75, 9),
            Block.box(4.7, -0.25, 7, 6.7, 0.75, 9),
            Block.box(5.25, 3.75, 7.5, 6.25, 4.75, 8.5),
            Block.box(9.75, 3.75, 7.5, 10.75, 4.75, 8.5),
            Block.box(5.25, -0.75, 7.5, 6.25, 0.25, 8.5),
            Block.box(9.75, -0.75, 7.5, 10.75, 0.25, 8.5),
            Block.box(4.5, 2, 11.5, 11.5, 2, 15.5),
            Block.box(4.5, 2, 0.5, 11.5, 2, 4.5)
    );

    public static final VoxelShape WALL_SOUTH = Shapes.or(
            Block.box(6.625, 6.15, -0.5, 9.375, 9.85, 0.5),
            Block.box(6.625, 6.15, 3.5, 9.375, 9.85, 4.5),
            Block.box(3.75, 4.5, -0.25, 12.25, 6.25, 4.25),
            Block.box(3.75, 9.75, -0.25, 12.25, 11.5, 4.25),
            Block.box(8.05, 3.5, 0, 12.05, 12.5, 4),
            Block.box(3.95, 3.5, 0, 7.95, 12.5, 4),
            Block.box(9.3, 7, -0.25, 11.3, 9, 0.75),
            Block.box(4.7, 7, -0.25, 6.7, 9, 0.75),
            Block.box(9.3, 7, 3.25, 11.3, 9, 4.25),
            Block.box(4.7, 7, 3.25, 6.7, 9, 4.25),
            Block.box(5.25, 7.5, -0.75, 6.25, 8.5, 0.25),
            Block.box(9.75, 7.5, -0.75, 10.75, 8.5, 0.25),
            Block.box(5.25, 7.5, 3.75, 6.25, 8.5, 4.75),
            Block.box(9.75, 7.5, 3.75, 10.75, 8.5, 4.75),
            Block.box(4.5, 11.5, 2, 11.5, 15.5, 2),
            Block.box(4.5, 0.5, 2, 11.5, 4.5, 2)
    );

    public static final VoxelShape WALL_NORTH =  Shapes.or(
            Block.box(6.625, 6.15, 11.5, 9.375, 9.85, 12.5),
            Block.box(6.625, 6.15, 15.5, 9.375, 9.85, 16.5),
            Block.box(3.75, 4.5, 11.75, 12.25, 6.25, 16.25),
            Block.box(3.75, 9.75, 11.75, 12.25, 11.5, 16.25),
            Block.box(8.05, 3.5, 12, 12.05, 12.5, 16),
            Block.box(3.95, 3.5, 12, 7.95, 12.5, 16),
            Block.box(9.3, 7, 11.75, 11.3, 9, 12.75),
            Block.box(4.7, 7, 11.75, 6.7, 9, 12.75),
            Block.box(9.3, 7, 15.25, 11.3, 9, 16.25),
            Block.box(4.7, 7, 15.25, 6.7, 9, 16.25),
            Block.box(5.25, 7.5, 11.25, 6.25, 8.5, 12.25),
            Block.box(9.75, 7.5, 11.25, 10.75, 8.5, 12.25),
            Block.box(5.25, 7.5, 15.75, 6.25, 8.5, 16.75),
            Block.box(9.75, 7.5, 15.75, 10.75, 8.5, 16.75),
            Block.box(4.5, 11.5, 14, 11.5, 15.5, 14),
            Block.box(4.5, 0.5, 14, 11.5, 4.5, 14)
    );


    public static final VoxelShape WALL_EAST = Shapes.or(
            Block.box(-0.5, 6.15, 6.625, 0.5, 9.85, 9.375),
            Block.box(3.5, 6.15, 6.625, 4.5, 9.85, 9.375),
            Block.box(-0.25, 4.5, 3.75, 4.25, 6.25, 12.25),
            Block.box(-0.25, 9.75, 3.75, 4.25, 11.5, 12.25),
            Block.box(0, 3.5, 3.9499999999999993, 4, 12.5, 7.949999999999999),
            Block.box(0, 3.5, 8.05, 4, 12.5, 12.05),
            Block.box(-0.25, 7, 4.699999999999999, 0.75, 9, 6.699999999999999),
            Block.box(-0.25, 7, 9.3, 0.75, 9, 11.3),
            Block.box(3.25, 7, 4.699999999999999, 4.25, 9, 6.699999999999999),
            Block.box(3.25, 7, 9.3, 4.25, 9, 11.3),
            Block.box(-0.75, 7.5, 9.75, 0.25, 8.5, 10.75),
            Block.box(-0.75, 7.5, 5.25, 0.25, 8.5, 6.25),
            Block.box(3.75, 7.5, 9.75, 4.75, 8.5, 10.75),
            Block.box(3.75, 7.5, 5.25, 4.75, 8.5, 6.25),
            Block.box(2, 11.5, 4.5, 2, 15.5, 11.5),
            Block.box(2, 0.5, 4.5, 2, 4.5, 11.5)
    );

    public static final VoxelShape WALL_WEST = Shapes.or(
            Block.box(11.5, 6.15, 6.625, 12.5, 9.85, 9.375),
            Block.box(15.5, 6.15, 6.625, 16.5, 9.85, 9.375),
            Block.box(11.75, 4.5, 3.75, 16.25, 6.25, 12.25),
            Block.box(11.75, 9.75, 3.75, 16.25, 11.5, 12.25),
            Block.box(12, 3.5, 3.9499999999999993, 16, 12.5, 7.949999999999999),
            Block.box(12, 3.5, 8.05, 16, 12.5, 12.05),
            Block.box(11.75, 7, 4.699999999999999, 12.75, 9, 6.699999999999999),
            Block.box(11.75, 7, 9.3, 12.75, 9, 11.3),
            Block.box(15.25, 7, 4.699999999999999, 16.25, 9, 6.699999999999999),
            Block.box(15.25, 7, 9.3, 16.25, 9, 11.3),
            Block.box(11.25, 7.5, 9.75, 12.25, 8.5, 10.75),
            Block.box(11.25, 7.5, 5.25, 12.25, 8.5, 6.25),
            Block.box(15.75, 7.5, 9.75, 16.75, 8.5, 10.75),
            Block.box(15.75, 7.5, 5.25, 16.75, 8.5, 6.25),
            Block.box(14, 11.5, 4.5, 14, 15.5, 11.5),
            Block.box(14, 0.5, 4.5, 14, 4.5, 11.5)
    );


    public static final VoxelShape CEILING_X = Shapes.or(
            Block.box(6.15, 15.5, 6.625, 9.85, 16.5, 9.375),
            Block.box(6.15, 11.5, 6.625, 9.85, 12.5, 9.375),
            Block.box(9.75, 11.75, 3.75, 11.5, 16.25, 12.25),
            Block.box(4.5, 11.75, 3.75, 6.25, 16.25, 12.25),
            Block.box(3.5, 12, 8.05, 12.5, 16, 12.05),
            Block.box(3.5, 12, 3.95, 12.5, 16, 7.95),
            Block.box(7, 15.25, 9.3, 9, 16.25, 11.3),
            Block.box(7, 15.25, 4.7, 9, 16.25, 6.7),
            Block.box(7, 11.75, 9.3, 9, 12.75, 11.3),
            Block.box(7, 11.75, 4.7, 9, 12.75, 6.7),
            Block.box(7.5, 15.75, 5.25, 8.5, 16.75, 6.25),
            Block.box(7.5, 15.75, 9.75, 8.5, 16.75, 10.75),
            Block.box(7.5, 11.25, 5.25, 8.5, 12.25, 6.25),
            Block.box(7.5, 11.25, 9.75, 8.5, 12.25, 10.75),
            Block.box(0.5, 14, 4.5, 4.5, 14, 11.5),
            Block.box(11.5, 14, 4.5, 15.5, 14, 11.5)
    );

    public static final VoxelShape CEILING_Z = Shapes.or(
            Block.box(6.625, 15.5, 6.15, 9.375, 16.5, 9.85),
            Block.box(6.625, 11.5, 6.15, 9.375, 12.5, 9.85),
            Block.box(3.75, 11.75, 4.5, 12.25, 16.25, 6.25),
            Block.box(3.75, 11.75, 9.75, 12.25, 16.25, 11.5),
            Block.box(8.05, 12, 3.5, 12.05, 16, 12.5),
            Block.box(3.95, 12, 3.5, 7.95, 16, 12.5),
            Block.box(9.3, 15.25, 7, 11.3, 16.25, 9),
            Block.box(4.7, 15.25, 7, 6.7, 16.25, 9),
            Block.box(9.3, 11.75, 7, 11.3, 12.75, 9),
            Block.box(4.7, 11.75, 7, 6.7, 12.75, 9),
            Block.box(5.25, 15.75, 7.5, 6.25, 16.75, 8.5),
            Block.box(9.75, 15.75, 7.5, 10.75, 16.75, 8.5),
            Block.box(5.25, 11.25, 7.5, 6.25, 12.25, 8.5),
            Block.box(9.75, 11.25, 7.5, 10.75, 12.25, 8.5),
            Block.box(4.5, 14, 11.5, 11.5, 14, 15.5),
            Block.box(4.5, 14, 0.5, 11.5, 14, 4.5)
    );

    public SatchelChargeBlock(Properties p_49795_) {
        super(p_49795_);
        this.defaultBlockState().setValue(ACTIVATED,false);
    }


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        switch((AttachFace)pState.getValue(FACE)) {
            case FLOOR:
                if (direction.getAxis() == Direction.Axis.X) {
                    return FLOOR_X;
                }

                return  FLOOR_Z;

            case WALL:
                switch(direction) {
                    case EAST:
                        return  WALL_EAST;
                    case WEST:
                        return WALL_WEST;
                    case SOUTH:
                        return  WALL_SOUTH;
                    case NORTH:
                    default:
                        return  WALL_NORTH;
                }
            case CEILING:
            default:
                if (direction.getAxis() == Direction.Axis.X) {
                    return  CEILING_X;
                } else {
                    return  CEILING_Z;
                }
        }
    }
    public static void explode(BlockPos pPos, Level pLevel) {
        explode(pLevel, pPos, (LivingEntity)null);
    }

    public static void explode(Level pLevel, BlockPos pPos, @Nullable LivingEntity pEntity) {
        if (!pLevel.isClientSide)
        {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 2);
            float f = 4.0F;
            pLevel.explode(null, pPos.getX(), pPos.getY(), pPos.getZ(), 5.0F, Explosion.BlockInteraction.BREAK);
            pLevel.gameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    @Override
    public boolean dropFromExplosion(Explosion pExplosion) {
        return false;
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        Level level = pLevel;
        BlockPos pos = pPos;
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(),3);
        explode(pos, level);


    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return false;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SatchelChargeTile(pPos, pState);
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {

        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof SatchelChargeTile BE) {
                    BE.tick();
                }
            };
        }
    }



    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canAttach(pLevel,pPos, getConnectedDirection(pState).getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, @org.jetbrains.annotations.Nullable Direction direction) {
        return true;
    }


    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {
        if (pState.getValue(ACTIVATED) && !pLevel.hasNeighborSignal(pPos)) {
            pLevel.setBlock(pPos, pState.cycle(ACTIVATED), 2);
        }

    }

    //Rotate

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return getConnectedDirection(pState).getOpposite() == pFacing && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        for(Direction direction : pContext.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, pContext.getHorizontalDirection()).setValue(ACTIVATED, false);
            } else {
                blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite()).setValue(ACTIVATED, false);
            }

            if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
                return blockstate;
            }
        }

        return null;
    }

    public static boolean canAttach(LevelReader pReader, BlockPos pPos, Direction pDirection)
    {
        BlockPos blockpos = pPos.relative(pDirection);
        System.out.println(blockpos);
        return pReader.getBlockState(blockpos).isFaceSturdy(pReader, blockpos, pDirection.getOpposite());
    }


    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }


    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected static Direction getConnectedDirection(BlockState pState) {
        switch((AttachFace)pState.getValue(FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return pState.getValue(FACING);
        }
    }


    //Builder
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, FACE, FACING);
    }


}