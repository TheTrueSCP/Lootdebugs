package net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.Pub;


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
import net.the_goldbeards.lootdebugs.Server.PacketHandler;
import net.the_goldbeards.lootdebugs.Server.TileEntity.Pub.BrewBeerPacket;
import net.the_goldbeards.lootdebugs.Sound.ModSounds;
import org.jetbrains.annotations.Nullable;

public class PubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PubBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));

    }


    //VoxelSHape
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_N = Shapes.or(
            Block.box(4.5, 7, 4.25, 11.5, 15.5, 12.5),
            Block.box(5, 4, 4.25, 11, 7, 13),
            Block.box(4.5, 15, 4.25, 6.5, 16, 13),
            Block.box(9.5, 7, 12, 11.5, 15.5, 13),
            Block.box(6.5, 7, 12, 9.5, 11, 13),
            Block.box(4.5, 7, 12, 6.5, 15.5, 13),
            Block.box(7, 12.5, 3, 9, 14.5, 4),
            Block.box(6.5, 12, 3.25, 9.5, 15, 4.25),
            Block.box(7, 9.75, 3.5, 9, 11.75, 4.5),
            Block.box(3, 1, 4.25, 5, 5.5, 6.25),
            Block.box(2.75, 0.75, 5.25, 4, 2.5, 6.5),
            Block.box(3, 3.25, 2.5, 5, 5.25, 3.75),
            Block.box(3.1499999999999995, 1.75, 2.625, 4.85, 3.25, 3.75),
            Block.box(3.3999999999999995, 0.25, 2.75, 4.6, 1.75, 3.75),
            Block.box(3.25, 2.5336148090921116, 2.260603785394825, 4.75, 6.533614809092112, 3.260603785394825),
            Block.box(11, 1, 4.25, 13, 5.5, 6.25),
            Block.box(12, 0.75, 5.25, 13.25, 2.5, 6.5),
            Block.box(11.25, 2.5336148090921116, 2.260603785394825, 12.75, 6.533614809092112, 3.260603785394825),
            Block.box(11, 3.25, 2.5, 13, 5.25, 3.75),
            Block.box(11.149999999999999, 1.75, 2.625, 12.85, 3.25, 3.75),
            Block.box(11.399999999999999, 0.25, 2.75, 12.6, 1.75, 3.75),
            Block.box(9.5, 15, 4.25, 11.5, 16, 13),
            Block.box(6.5, 15, 7.25, 9.5, 16, 11),
            Block.box(7.25, 5.5, 3.75, 8.75, 6.5, 4.75),
            Block.box(8.75, 5, 4, 11.25, 7, 4),
            Block.box(4.75, 5, 4, 7.25, 7, 4),
            Block.box(4.75, 3.75, 11, 7, 6.75, 13.5),
            Block.box(9, 3.75, 11, 11.25, 6.75, 13.5),
            Block.box(9, 3.75, 13.75, 11.25, 6.75, 13.75),
            Block.box(4.75, 3.75, 13.75, 7, 6.75, 13.75)
    );

    private static final VoxelShape SHAPE_E = Shapes.or(
            Block.box(3.5, 7, 4.5, 11.75, 15.5, 11.5),
            Block.box(3, 4, 5, 11.75, 7, 11),
            Block.box(3, 15, 4.5, 11.75, 16, 6.5),
            Block.box(3, 7, 9.5, 4, 15.5, 11.5),
            Block.box(3, 7, 6.5, 4, 11, 9.5),
            Block.box(3, 7, 4.5, 4, 15.5, 6.5),
            Block.box(12, 12.5, 7, 13, 14.5, 9),
            Block.box(11.75, 12, 6.5, 12.75, 15, 9.5),
            Block.box(11.5, 9.75, 7, 12.5, 11.75, 9),
            Block.box(9.75, 1, 3, 11.75, 5.5, 5),
            Block.box(9.5, 0.75, 2.75, 10.75, 2.5, 4),
            Block.box(12.25, 3.25, 3, 13.5, 5.25, 5),
            Block.box(12.25, 1.75, 3.1499999999999986, 13.375, 3.25, 4.85),
            Block.box(12.25, 0.25, 3.3999999999999986, 13.25, 1.75, 4.6),
            Block.box(12.739396214605176, 2.5336148090921116, 3.25, 13.739396214605176, 6.533614809092112, 4.75),
            Block.box(9.75, 1, 11, 11.75, 5.5, 13),
            Block.box(9.5, 0.75, 12, 10.75, 2.5, 13.25),
            Block.box(12.739396214605176, 2.5336148090921116, 11.25, 13.739396214605176, 6.533614809092112, 12.75),
            Block.box(12.25, 3.25, 11, 13.5, 5.25, 13),
            Block.box(12.25, 1.75, 11.149999999999999, 13.375, 3.25, 12.85),
            Block.box(12.25, 0.25, 11.399999999999999, 13.25, 1.75, 12.6),
            Block.box(3, 15, 9.5, 11.75, 16, 11.5),
            Block.box(5, 15, 6.5, 8.75, 16, 9.5),
            Block.box(11.25, 5.5, 7.25, 12.25, 6.5, 8.75),
            Block.box(12, 5, 8.75, 12, 7, 11.25),
            Block.box(12, 5, 4.75, 12, 7, 7.25),
            Block.box(2.5, 3.75, 4.75, 5, 6.75, 7),
            Block.box(2.5, 3.75, 9, 5, 6.75, 11.25),
            Block.box(2.25, 3.75, 9, 2.25, 6.75, 11.25),
            Block.box(2.25, 3.75, 4.75, 2.25, 6.75, 7)
    );

    private static final VoxelShape SHAPE_S = Shapes.or(
            Block.box(4.5, 7, 3.5, 11.5, 15.5, 11.75),
            Block.box(5, 4, 3, 11, 7, 11.75),
            Block.box(9.5, 15, 3, 11.5, 16, 11.75),
            Block.box(4.5, 7, 3, 6.5, 15.5, 4),
            Block.box(6.5, 7, 3, 9.5, 11, 4),
            Block.box(9.5, 7, 3, 11.5, 15.5, 4),
            Block.box(7, 12.5, 12, 9, 14.5, 13),
            Block.box(6.5, 12, 11.75, 9.5, 15, 12.75),
            Block.box(7, 9.75, 11.5, 9, 11.75, 12.5),
            Block.box(11, 1, 9.75, 13, 5.5, 11.75),
            Block.box(12, 0.75, 9.5, 13.25, 2.5, 10.75),
            Block.box(11, 3.25, 12.25, 13, 5.25, 13.5),
            Block.box(11.15, 1.75, 12.25, 12.850000000000001, 3.25, 13.375),
            Block.box(11.4, 0.25, 12.25, 12.600000000000001, 1.75, 13.25),
            Block.box(11.25, 2.5336148090921116, 12.739396214605176, 12.75, 6.533614809092112, 13.739396214605176),
            Block.box(3, 1, 9.75, 5, 5.5, 11.75),
            Block.box(2.75, 0.75, 9.5, 4, 2.5, 10.75),
            Block.box(3.25, 2.5336148090921116, 12.739396214605176, 4.75, 6.533614809092112, 13.739396214605176),
            Block.box(3, 3.25, 12.25, 5, 5.25, 13.5),
            Block.box(3.1500000000000004, 1.75, 12.25, 4.850000000000001, 3.25, 13.375),
            Block.box(3.4000000000000004, 0.25, 12.25, 4.600000000000001, 1.75, 13.25),
            Block.box(4.5, 15, 3, 6.5, 16, 11.75),
            Block.box(6.5, 15, 5, 9.5, 16, 8.75),
            Block.box(7.25, 5.5, 11.25, 8.75, 6.5, 12.25),
            Block.box(4.75, 5, 12, 7.25, 7, 12),
            Block.box(8.75, 5, 12, 11.25, 7, 12),
            Block.box(9, 3.75, 2.5, 11.25, 6.75, 5),
            Block.box(4.75, 3.75, 2.5, 7, 6.75, 5),
            Block.box(4.75, 3.75, 2.25, 7, 6.75, 2.25),
            Block.box(9, 3.75, 2.25, 11.25, 6.75, 2.25)
    );

    private static final VoxelShape SHAPE_W = Shapes.or(
            Block.box(4.25, 7, 4.5, 12.5, 15.5, 11.5),
            Block.box(4.25, 4, 5, 13, 7, 11),
            Block.box(4.25, 15, 9.5, 13, 16, 11.5),
            Block.box(12, 7, 4.5, 13, 15.5, 6.5),
            Block.box(12, 7, 6.5, 13, 11, 9.5),
            Block.box(12, 7, 9.5, 13, 15.5, 11.5),
            Block.box(3, 12.5, 7, 4, 14.5, 9),
            Block.box(3.25, 12, 6.5, 4.25, 15, 9.5),
            Block.box(3.5, 9.75, 7, 4.5, 11.75, 9),
            Block.box(4.25, 1, 11, 6.25, 5.5, 13),
            Block.box(5.25, 0.75, 12, 6.5, 2.5, 13.25),
            Block.box(2.5, 3.25, 11, 3.75, 5.25, 13),
            Block.box(2.625, 1.75, 11.15, 3.75, 3.25, 12.850000000000001),
            Block.box(2.75, 0.25, 11.4, 3.75, 1.75, 12.600000000000001),
            Block.box(2.2606037853948244, 2.5336148090921116, 11.25, 3.2606037853948244, 6.533614809092112, 12.75),
            Block.box(4.25, 1, 3, 6.25, 5.5, 5),
            Block.box(5.25, 0.75, 2.75, 6.5, 2.5, 4),
            Block.box(2.2606037853948244, 2.5336148090921116, 3.25, 3.2606037853948244, 6.533614809092112, 4.75),
            Block.box(2.5, 3.25, 3, 3.75, 5.25, 5),
            Block.box(2.625, 1.75, 3.1500000000000004, 3.75, 3.25, 4.850000000000001),
            Block.box(2.75, 0.25, 3.4000000000000004, 3.75, 1.75, 4.600000000000001),
            Block.box(4.25, 15, 4.5, 13, 16, 6.5),
            Block.box(7.25, 15, 6.5, 11, 16, 9.5),
            Block.box(3.75, 5.5, 7.25, 4.75, 6.5, 8.75),
            Block.box(4, 5, 4.75, 4, 7, 7.25),
            Block.box(4, 5, 8.75, 4, 7, 11.25),
            Block.box(11, 3.75, 9, 13.5, 6.75, 11.25),
            Block.box(11, 3.75, 4.75, 13.5, 6.75, 7),
            Block.box(13.75, 3.75, 4.75, 13.75, 6.75, 7),
            Block.box(13.75, 3.75, 9, 13.75, 6.75, 11.25)
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
            if (blockEntity instanceof PubTile pubTile)
            {
                pubTile.setRemoved();
                ((PubTile) blockEntity).drops();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer,pPos, ModSounds.LLOYD_INTERACTION.get(), SoundSource.BLOCKS,1,1);

        if (!pLevel.isClientSide()) {

            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if(entity instanceof PubTile) {

                NetworkHooks.openGui(((ServerPlayer)pPlayer), (PubTile)entity, pPos);

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PubTile(pPos, pState);
    }

   @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
       {
           if (pLevel.isClientSide()) {
               return null;
           }
           return (lvl, pos, blockState, t) -> {
               if (t instanceof PubTile BE) {
                   BE.tick(pLevel, pos, blockState, BE);
               }
           };
       }
    }

          public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
                    if (!pLevel.isClientSide) {

                        if (pLevel.hasNeighborSignal(pPos)) {
                            PacketHandler.sendToServer(new BrewBeerPacket(pPos));
            }

        }
    }
}
