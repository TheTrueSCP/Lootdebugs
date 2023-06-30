package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Lloyd;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.the_goldbeards.lootdebugs.Network.PacketHandler;
import net.the_goldbeards.lootdebugs.Network.TileEntity.Lloyd.LloydBrewBeerPacket;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import org.jetbrains.annotations.Nullable;

public class LloydBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LloydBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));

    }


    //VoxelSHape
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_S = Shapes.or(
            Block.box(4.75, 3.75, 2.25, 7, 6.75, 2.25),
            Block.box(4.5, 7, 3.5, 11.5, 15.5, 11.75),
            Block.box(5, 4, 3, 11, 7, 11.75),
            Block.box(4.5, 15, 3, 6.5, 16, 11.75),
            Block.box(9.5, 7, 3, 11.5, 15.5, 4),
            Block.box(6.5, 7, 3, 9.5, 11, 4),
            Block.box(4.5, 7, 3, 6.5, 15.5, 4),
            Block.box(7, 12.5, 12, 9, 14.5, 13),
            Block.box(6.5, 12, 11.75, 9.5, 15, 12.75),
            Block.box(7, 9.75, 11.5, 9, 11.75, 12.5),
            Block.box(3, 1, 9.75, 5, 5.5, 11.75),
            Block.box(2.75, 0.75, 9.5, 4, 2.5, 10.75),
            Block.box(3, 3.25, 12.25, 5, 5.25, 13.5),
            Block.box(3.15, 1.75, 12.25, 4.85, 3.25, 13.375),
            Block.box(3.4, 0.25, 12.25, 4.6, 1.75, 13.25),
            Block.box(11, 1, 9.75, 13, 5.5, 11.75),
            Block.box(12, 0.75, 9.5, 13.25, 2.5, 10.75),
            Block.box(11, 3.25, 12.25, 13, 5.25, 13.5),
            Block.box(11.15, 1.75, 12.25, 12.85, 3.25, 13.375),
            Block.box(11.4, 0.25, 12.25, 12.6, 1.75, 13.25),
            Block.box(9.5, 15, 3, 11.5, 16, 11.75),
            Block.box(6.5, 15, 5, 9.5, 16, 8.75),
            Block.box(7.25, 5.5, 11.25, 8.75, 6.5, 12.25),
            Block.box(8.75, 5, 12, 11.25, 7, 12),
            Block.box(4.75, 5, 12, 7.25, 7, 12),
            Block.box(4.75, 3.75, 2.5, 7, 6.75, 5),
            Block.box(9, 3.75, 2.5, 11.25, 6.75, 5),
            Block.box(9, 3.75, 2.25, 11.25, 6.75, 2.25)
    );

    private static final VoxelShape SHAPE_E = Shapes.or(

            Block.box(2.1875, 3.75, 8.9375, 2.1875, 6.75, 11.1875),
            Block.box(3.4375, 7, 4.4375, 11.6875, 15.5, 11.4375),
            Block.box(2.9375, 4, 4.9375, 11.6875, 7, 10.9375),
            Block.box(2.9375, 15, 9.4375, 11.6875, 16, 11.4375),
            Block.box(2.9375, 7, 4.4375, 3.9375, 15.5, 6.4375),
            Block.box(2.9375, 7, 6.4375, 3.9375, 11, 9.4375),
            Block.box(2.9375, 7, 9.4375, 3.9375, 15.5, 11.4375),
            Block.box(11.9375, 12.5, 6.9375, 12.9375, 14.5, 8.9375),
            Block.box(11.6875, 12, 6.4375, 12.6875, 15, 9.4375),
            Block.box(11.4375, 9.75, 6.9375, 12.4375, 11.75, 8.9375),
            Block.box(9.6875, 1, 10.9375, 11.6875, 5.5, 12.9375),
            Block.box(9.4375, 0.75, 11.9375, 10.6875, 2.5, 13.1875),
            Block.box(12.1875, 3.25, 10.9375, 13.4375, 5.25, 12.9375),
            Block.box(12.1875, 1.75, 11.0875, 13.3125, 3.25, 12.7875),
            Block.box(12.1875, 0.25, 11.3375, 13.1875, 1.75, 12.5375),
            Block.box(9.6875, 1, 2.9375, 11.6875, 5.5, 4.9375),
            Block.box(9.4375, 0.75, 2.6875, 10.6875, 2.5, 3.9375),
            Block.box(12.1875, 3.25, 2.9375, 13.4375, 5.25, 4.9375),
            Block.box(12.1875, 1.75, 3.0875000000000004, 13.3125, 3.25, 4.7875),
            Block.box(12.1875, 0.25, 3.3375000000000004, 13.1875, 1.75, 4.5375),
            Block.box(2.9375, 15, 4.4375, 11.6875, 16, 6.4375),
            Block.box(4.9375, 15, 6.4375, 8.6875, 16, 9.4375),
            Block.box(11.1875, 5.5, 7.1875, 12.1875, 6.5, 8.6875),
            Block.box(11.9375, 5, 4.6875, 11.9375, 7, 7.1875),
            Block.box(11.9375, 5, 8.6875, 11.9375, 7, 11.1875),
            Block.box(2.4375, 3.75, 8.9375, 4.9375, 6.75, 11.1875),
            Block.box(2.4375, 3.75, 4.6875, 4.9375, 6.75, 6.9375),
            Block.box(2.1875, 3.75, 4.6875, 2.1875, 6.75, 6.9375)

    );

    private static final VoxelShape SHAPE_N = Shapes.or(
            Shapes.or(
                    Block.box(4.5, 7, 4.25, 11.5, 15.5, 12.5),
                    Block.box(5, 4, 4.25, 11, 7, 13),
                    Block.box(4.5, 15, 4.25, 6.5, 16, 13),
                    Block.box(9.5, 7, 12, 11.5, 15.5, 13),
                    Block.box(6.5, 7, 12, 9.5, 11, 13),
                    Block.box(4.5, 7, 12, 6.5, 15.5, 13),
                    Block.box(7, 12.5, 3, 9, 14.5, 4),
                    Block.box(7, 9.75, 3.5, 9, 11.75, 4.5),
                    Block.box(6.5, 12, 3.25, 9.5, 15, 4.25),
                    Block.box(3, 1, 4.25, 5, 5.5, 6.25),
                    Block.box(2.75, 0.75, 5.25, 4, 2.5, 6.5),
                    Block.box(3, 3.25, 2.5, 5, 5.25, 3.75),
                    Block.box(3.15, 1.75, 2.625, 4.85, 3.25, 3.75),
                    Block.box(3.4, 0.25, 2.75, 4.6, 1.75, 3.75),
                    Block.box(11, 1, 4.25, 13, 5.5, 6.25),
                    Block.box(12, 0.75, 5.25, 13.25, 2.5, 6.5),
                    Block.box(11, 3.25, 2.5, 13, 5.25, 3.75),
                    Block.box(11.15, 1.75, 2.625, 12.85, 3.25, 3.75),
                    Block.box(11.4, 0.25, 2.75, 12.6, 1.75, 3.75),
                    Block.box(9.5, 15, 4.25, 11.5, 16, 13),
                    Block.box(6.5, 15, 7.25, 9.5, 16, 11),
                    Block.box(7.25, 5.5, 3.75, 8.75, 6.5, 4.75),
                    Block.box(8.75, 5, 4, 11.25, 7, 4),
                    Block.box(4.75, 5, 4, 7.25, 7, 4),
                    Block.box(4.75, 3.75, 11, 7, 6.75, 13.5),
                    Block.box(9, 3.75, 11, 11.25, 6.75, 13.5),
                    Block.box(9, 3.75, 13.75, 11.25, 6.75, 13.75),
                    Block.box(4.75, 3.75, 13.75, 7, 6.75, 13.75)
            )
    );

    private static final VoxelShape SHAPE_W = Shapes.or(Block.box(13.8125, 3.75, 8.9375, 13.8125, 6.75, 11.1875),
            Block.box(4.3125, 7, 4.4375, 12.5625, 15.5, 11.4375),
            Block.box(4.3125, 4, 4.9375, 13.0625, 7, 10.9375),
            Block.box(4.3125, 15, 9.4375, 13.0625, 16, 11.4375),
            Block.box(12.0625, 7, 4.4375, 13.0625, 15.5, 6.4375),
            Block.box(12.0625, 7, 6.4375, 13.0625, 11, 9.4375),
            Block.box(12.0625, 7, 9.4375, 13.0625, 15.5, 11.4375),
            Block.box(3.0625, 12.5, 6.9375, 4.0625, 14.5, 8.9375),
            Block.box(3.3125, 12, 6.4375, 4.3125, 15, 9.4375),
            Block.box(3.5625, 9.75, 6.9375, 4.5625, 11.75, 8.9375),
            Block.box(4.3125, 1, 10.9375, 6.3125, 5.5, 12.9375),
            Block.box(5.3125, 0.75, 11.9375, 6.5625, 2.5, 13.1875),
            Block.box(2.5625, 3.25, 10.9375, 3.8125, 5.25, 12.9375),
            Block.box(2.6875, 1.75, 11.0875, 3.8125, 3.25, 12.7875),
            Block.box(2.8125, 0.25, 11.3375, 3.8125, 1.75, 12.5375),
            Block.box(4.3125, 1, 2.9375, 6.3125, 5.5, 4.9375),
            Block.box(5.3125, 0.75, 2.6875, 6.5625, 2.5, 3.9375),
            Block.box(2.5625, 3.25, 2.9375, 3.8125, 5.25, 4.9375),
            Block.box(2.6875, 1.75, 3.0875000000000004, 3.8125, 3.25, 4.7875),
            Block.box(2.8125, 0.25, 3.3375000000000004, 3.8125, 1.75, 4.5375),
            Block.box(4.3125, 15, 4.4375, 13.0625, 16, 6.4375),
            Block.box(7.3125, 15, 6.4375, 11.0625, 16, 9.4375),
            Block.box(3.8125, 5.5, 7.1875, 4.8125, 6.5, 8.6875),
            Block.box(4.0625, 5, 4.6875, 4.0625, 7, 7.1875),
            Block.box(4.0625, 5, 8.6875, 4.0625, 7, 11.1875),
            Block.box(11.0625, 3.75, 8.9375, 13.5625, 6.75, 11.1875),
            Block.box(11.0625, 3.75, 4.6875, 13.5625, 6.75, 6.9375),
            Block.box(13.8125, 3.75, 4.6875, 13.8125, 6.75, 6.9375)
        );


    //Rotation Shape
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return SHAPE_N;

            case EAST:
                return SHAPE_E;

            case WEST:
                return SHAPE_W;

            case SOUTH:
                return SHAPE_S;

            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }


    //Waterlog
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


//Last Stuff
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return super.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED,  Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {

        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof LloydTile lloydTile)
            {
                ((LloydTile) blockEntity).drops();
                pLevel.removeBlockEntity(pPos);
                pLevel.removeBlock(pPos, false);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide())
        {
            pLevel.playSound(null, pPos, ModSounds.LLOYD_INTERACTION.get(), SoundSource.BLOCKS,1,1);

            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if(entity instanceof LloydTile) {

                NetworkHooks.openGui(((ServerPlayer)pPlayer), (LloydTile)entity, pPos);

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LloydTile(pPos, pState);
    }

   @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
       {
           return createTickerHelper(pBlockEntityType, ModTileEntities.LLOYD_BLOCK_ENTITY.get(),
                   LloydTile::tick);
       }
    }

          public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
                    if (!pLevel.isClientSide) {

                        if (pLevel.hasNeighborSignal(pPos)) {
                            PacketHandler.sendToServer(new LloydBrewBeerPacket(pPos));
            }

        }
    }
}
