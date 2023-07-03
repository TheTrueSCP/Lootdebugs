package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.EquipmentTable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import org.jetbrains.annotations.Nullable;

public class EquipmentTableBlock extends BaseEntityBlock  {


    public EquipmentTableBlock(Properties properties) {
        super(properties);
    }
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private VoxelShape SHAPE_LOWER_N_S = Shapes.or(Block.box(12.5, 0, 2, 15.5, 14, 14),
            Block.box(0.5, 0, 2, 3.5, 14, 14),
            Block.box(-0.5, 13, -0.5, 16.5, 15.5, 16.5)
            );

    private VoxelShape SHAPE_LOWER_E_W = Shapes.or(Block.box(2, 0, 12.5, 14, 14, 15.5),
            Block.box(2, 0, 0.5, 14, 14, 3.5),
            Block.box(-0.5, 13, -0.5, 16.5, 15.5, 16.5)
    );

    private VoxelShape SHAPE_UPPER = Shapes.or(Block.box(-1, 13.95, -1, 17, 15.95, 17), Block.box(-0.20000000000000018, -0.5, -0.20000000000000018, 16.2, 14, 16.2));


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide()) {

            BlockEntity entity = pLevel.getBlockEntity(pPos);
            BlockEntity entityBelow = pLevel.getBlockEntity(pPos.below());

            if(entity instanceof EquipmentTableTile) {

                if(pState.getValue(HALF) == DoubleBlockHalf.UPPER)
                {
                    NetworkHooks.openGui(((ServerPlayer) pPlayer), (EquipmentTableTile) entityBelow, pPos.below());
                }
                else
                {
                    NetworkHooks.openGui(((ServerPlayer) pPlayer), (EquipmentTableTile) entity, pPos);
                }

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        switch (state.getValue(HALF))
        {
            case UPPER: return SHAPE_UPPER;

            case LOWER: return state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH ? SHAPE_LOWER_N_S : SHAPE_LOWER_E_W;
            default:
                return Shapes.block();
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);

        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && blockstate.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                pLevel.setBlock(blockpos, blockstate1, 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }


    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }

        @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }



    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    /**
     * Return a random long to be passed to {@link net.minecraft.client.resources.model.BakedModel#getQuads}, used for
     * random model rotations
     */
    public long getSeed(BlockState pState, BlockPos pPos) {
        return Mth.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pPos.getZ());
    }

    //If the block is destroyed, al items inside drops
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof EquipmentTableTile) {
                ((EquipmentTableTile) blockEntity).drops();
                pLevel.removeBlockEntity(pPos);
                pLevel.removeBlock(pPos, false);
            }
        }
        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EquipmentTableTile(pPos,pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof EquipmentTableTile BE) {
                    BE.tick(pLevel, pos, blockState, BE);
                }
            };
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING);
    }
}
