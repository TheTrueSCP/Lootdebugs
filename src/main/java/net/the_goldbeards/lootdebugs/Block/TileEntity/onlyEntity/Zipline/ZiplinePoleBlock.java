package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineMoveEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZiplinePoleBlock extends BaseEntityBlock
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape NORTH_SOUTH_UPPER = Shapes.or(
            Block.box(6.25, 9, 6.25, 9.75, 12.75, 9.75),
            Block.box(7, 12.75, 7, 9, 15, 9),
            Block.box(6.9, 15, 6.9, 9.1, 16, 9.1),
            Block.box(6, 11.25, 7.5, 10, 13.25, 8.5),
            Block.box(7.5, 11.25, 6, 8.5, 13.25, 10),
            Block.box(7.5, -0.5, 6.5, 8.5, 9.5, 6.5),
            Block.box(6.75, 8, 6.75, 9.25, 9, 9.25),
            Block.box(7.125, -0.5, 7.125, 8.875, 8, 8.875),
            Block.box(6.875, 1, 6.875, 9.125, 2, 9.125),
            Block.box(6.875, 1.5, 7.5, 9.125, 2.5, 8.5),
            Block.box(8, -3.5, 6.75, 8, -2, 9.25),
            Block.box(8, -4.5, 7.5, 8, -3.5, 8.5),
            Block.box(7.5, -4.5, 8, 8.5, -3.5, 8)
    );

    public static final VoxelShape EAST_WEST_UPPER = Shapes.or(
            Block.box(6.25, 9, 6.25, 9.75, 12.75, 9.75),
            Block.box(7, 12.75, 7, 9, 15, 9),
            Block.box(6.9, 15, 6.9, 9.1, 16, 9.1),
            Block.box(7.5, 11.25, 6, 8.5, 13.25, 10),
            Block.box(6, 11.25, 7.5, 10, 13.25, 8.5),
            Block.box(9.5, -0.5, 7.5, 9.5, 9.5, 8.5),
            Block.box(6.75, 8, 6.75, 9.25, 9, 9.25),
            Block.box(7.125, -0.5, 7.125, 8.875, 8, 8.875),
            Block.box(6.875, 1, 6.875, 9.125, 2, 9.125),
            Block.box(7.5, 1.5, 6.875, 8.5, 2.5, 9.125),
            Block.box(6.75, -3.5, 8, 9.25, -2, 8),
            Block.box(7.5, -4.5, 8, 8.5, -3.5, 8),
            Block.box(8, -4.5, 7.5, 8, -3.5, 8.5)
    );

    public static final VoxelShape NORTH_LOWER = Shapes.or(Block.box(6.249999999999993, 4, 6.25, 9.749999999999993, 7.5, 9.75),
            Block.box(6.499999999999993, 3, 6.5, 9.499999999999993, 4, 9.5),
            Block.box(6.999999999999993, -0.5, 7, 8.999999999999993, 3, 9),
            Block.box(7.499999999999993, -1.5, 7.5, 8.499999999999993, -0.5, 8.5),
            Block.box(6.749999999999993, 7.5, 6.75, 9.249999999999993, 13.5, 9.25),
            Block.box(7.249999999999993, 8.5, 8.75, 8.749999999999993, 12.5, 9.75),
            Block.box(7.499999999999993, 8.5, 9.5, 8.499999999999993, 15.5, 9.5),
            Block.box(10.249999999999993, 7.5, 2.25, 11.249999999999993, 11.5, 6.25),
            Block.box(9.249999999999993, 8, 2.5500000000000007, 10.249999999999993, 11.1, 5.850000000000001),
            Block.box(6.749999999999993, 8, 2.5500000000000007, 7.749999999999993, 11, 5.550000000000001),
            Block.box(7.499999999999993, 8, -0.9699999999999989, 8.499999999999993, 9, 2.8000000000000007),
            Block.box(7.249999999999993, 7.9, 2.6499999999999986, 8.249999999999993, 11.1, 5.850000000000001),
            Block.box(8.249999999999993, 7.75, 2.5, 9.249999999999993, 11.25, 6),
            Block.box(5.749999999999995, 7.8, 2.5, 6.749999999999993, 11.3, 6),
            Block.box(4.749999999999998, 7.5, 2.25, 5.749999999999995, 11.5, 6.25),
            Block.box(3.750000000000001, 9, 3.75, 4.749999999999998, 10, 4.75),
            Block.box(11.249999999999993, 9, 3.75, 12.249999999999993, 10, 4.75),
            Block.box(4.25, 8.25, 3.75, 4.25, 10.75, 7.75),
            Block.box(11.749999999999993, 8.25, 3.75, 11.749999999999993, 10.75, 7.75),
            Block.box(4.749999999999998, 5.46447, 7.285530000000001, 6.249999999999993, 6.46447, 8.285530000000001),
            Block.box(9.749999999999993, 5.46447, 7.285530000000001, 11.249999999999993, 6.46447, 8.285530000000001),
            Block.box(5.749999999999995, 9, 3.75, 10.249999999999993, 10, 4.75),
            Block.box(6.749999999999993, 8.5, -1, 9.249999999999993, 9.75, 0.25),
            Block.box(6.499999999999993, 8.75, -0.75, 7.499999999999993, 9.75, 2.25),
            Block.box(7.499999999999993, 8.75, 1.25, 8.499999999999993, 9.75, 6.25),
            Block.box(7.499999999999993, 4.3, 4, 8.499999999999993, 5.3, 5),
            Block.box(7.249999999999993, 4.05, 5, 8.749999999999993, 5.55, 6.25),
            Block.box(8.499999999999993, 8.75, -0.75, 9.499999999999993, 9.75, 2.25),
            Block.box(7.124999999999993, 13.5, 7.125, 8.874999999999993, 15.5, 8.875),
            Block.box(7.999999999999993, -2, 5.75, 7.999999999999993, 0.5, 10.25),
            Block.box(7.999999999999993, -3.5, 6.75, 7.999999999999993, -2, 9.25),
            Block.box(7.999999999999993, -4.5, 7.5, 7.999999999999993, -3.5, 8.5),
            Block.box(5.749999999999995, -2, 8, 10.249999999999993, 0.5, 8),
            Block.box(6.749999999999993, -3.5, 8, 9.249999999999993, -2, 8),
            Block.box(7.499999999999993, -4.5, 8, 8.499999999999993, -3.5, 8),
            Block.box(7.999999999999993, 0.5, 6.5, 7.999999999999993, 3, 9.5),
            Block.box(6.499999999999993, 0.5, 8, 9.499999999999993, 3, 8)
    );

    public static final VoxelShape EAST_LOWER = Shapes.or(Block.box(6.25, 4, 6.25, 9.75, 7.5, 9.75),
            Block.box(6.5, 3, 6.5, 9.5, 4, 9.5),
            Block.box(7, -0.5, 7, 9, 3, 9),
            Block.box(7.5, -1.5, 7.5, 8.5, -0.5, 8.5),
            Block.box(6.75, 7.5, 6.75, 9.25, 13.5, 9.25),
            Block.box(6.25, 8.5, 7.25, 7.25, 12.5, 8.75),
            Block.box(6.5, 8.5, 7.5, 6.5, 15.5, 8.5),
            Block.box(9.75, 7.5, 10.25, 13.75, 11.5, 11.25),
            Block.box(10.15, 8, 9.25, 13.45, 11.1, 10.25),
            Block.box(10.45, 8, 6.75, 13.45, 11, 7.75),
            Block.box(13.2, 8, 7.5, 16.97, 9, 8.5),
            Block.box(10.15, 7.9, 7.25, 13.35, 11.1, 8.25),
            Block.box(10, 7.75, 8.25, 13.5, 11.25, 9.25),
            Block.box(10, 7.8, 5.75, 13.5, 11.3, 6.75),
            Block.box(9.75, 7.5, 4.75, 13.75, 11.5, 5.75),
            Block.box(11.25, 9, 3.75, 12.25, 10, 4.75),
            Block.box(11.25, 9, 11.25, 12.25, 10, 12.25),
            Block.box(8.25, 8.25, 4.25, 12.25, 10.75, 4.25),
            Block.box(8.25, 8.25, 11.75, 12.25, 10.75, 11.75),
            Block.box(7.71447, 5.46447, 4.75, 8.71447, 6.46447, 6.25),
            Block.box(7.71447, 5.46447, 9.75, 8.71447, 6.46447, 11.25),
            Block.box(11.25, 9, 5.75, 12.25, 10, 10.25),
            Block.box(15.75, 8.5, 6.75, 17, 9.75, 9.25),
            Block.box(13.75, 8.75, 6.5, 16.75, 9.75, 7.5),
            Block.box(9.75, 8.75, 7.5, 14.75, 9.75, 8.5),
            Block.box(11, 4.3, 7.5, 12, 5.3, 8.5),
            Block.box(9.75, 4.05, 7.25, 11, 5.55, 8.75),
            Block.box(13.75, 8.75, 8.5, 16.75, 9.75, 9.5),
            Block.box(7.125, 13.5, 7.125, 8.875, 15.5, 8.875),
            Block.box(5.75, -2, 8, 10.25, 0.5, 8),
            Block.box(6.75, -3.5, 8, 9.25, -2, 8),
            Block.box(7.5, -4.5, 8, 8.5, -3.5, 8),
            Block.box(8, -2, 5.75, 8, 0.5, 10.25),
            Block.box(8, -3.5, 6.75, 8, -2, 9.25),
            Block.box(8, -4.5, 7.5, 8, -3.5, 8.5),
            Block.box(6.5, 0.5, 8, 9.5, 3, 8),
            Block.box(8, 0.5, 6.5, 8, 3, 9.5)
    );

    public static final VoxelShape SOUTH_LOWER = Shapes.or(
            Block.box(6.25, 4, 6.25, 9.75, 7.5, 9.75),
            Block.box(6.5, 3, 6.5, 9.5, 4, 9.5),
            Block.box(7, -0.5, 7, 9, 3, 9),
            Block.box(7.5, -1.5, 7.5, 8.5, -0.5, 8.5),
            Block.box(6.75, 7.5, 6.75, 9.25, 13.5, 9.25),
            Block.box(7.25, 8.5, 6.25, 8.75, 12.5, 7.25),
            Block.box(7.5, 8.5, 6.5, 8.5, 15.5, 6.5),
            Block.box(4.75, 7.5, 9.75, 5.75, 11.5, 13.75),
            Block.box(5.75, 8, 10.15, 6.75, 11.1, 13.45),
            Block.box(8.25, 8, 10.45, 9.25, 11, 13.45),
            Block.box(7.5, 8, 13.2, 8.5, 9, 16.97),
            Block.box(7.75, 7.9, 10.15, 8.75, 11.1, 13.35),
            Block.box(6.75, 7.75, 10, 7.75, 11.25, 13.5),
            Block.box(9.25, 7.8, 10, 10.25, 11.3, 13.5),
            Block.box(10.25, 7.5, 9.75, 11.25, 11.5, 13.75),
            Block.box(11.25, 9, 11.25, 12.25, 10, 12.25),
            Block.box(3.75, 9, 11.25, 4.75, 10, 12.25),
            Block.box(11.75, 8.25, 8.25, 11.75, 10.75, 12.25),
            Block.box(4.25, 8.25, 8.25, 4.25, 10.75, 12.25),
            Block.box(9.75, 5.46447, 7.71447, 11.25, 6.46447, 8.71447),
            Block.box(4.75, 5.46447, 7.71447, 6.25, 6.46447, 8.71447),
            Block.box(5.75, 9, 11.25, 10.25, 10, 12.25),
            Block.box(6.75, 8.5, 15.75, 9.25, 9.75, 17),
            Block.box(8.5, 8.75, 13.75, 9.5, 9.75, 16.75),
            Block.box(7.5, 8.75, 9.75, 8.5, 9.75, 14.75),
            Block.box(7.5, 4.3, 11, 8.5, 5.3, 12),
            Block.box(7.25, 4.05, 9.75, 8.75, 5.55, 11),
            Block.box(6.5, 8.75, 13.75, 7.5, 9.75, 16.75),
            Block.box(7.125, 13.5, 7.125, 8.875, 15.5, 8.875),
            Block.box(8, -2, 5.75, 8, 0.5, 10.25),
            Block.box(8, -3.5, 6.75, 8, -2, 9.25),
            Block.box(8, -4.5, 7.5, 8, -3.5, 8.5),
            Block.box(5.75, -2, 8, 10.25, 0.5, 8),
            Block.box(6.75, -3.5, 8, 9.25, -2, 8),
            Block.box(7.5, -4.5, 8, 8.5, -3.5, 8),
            Block.box(8, 0.5, 6.5, 8, 3, 9.5),
            Block.box(6.5, 0.5, 8, 9.5, 3, 8)
    );


    public static final VoxelShape WEST_LOWER = Shapes.or(Block.box(6.249999999999993, 4, 6.25, 9.749999999999993, 7.5, 9.75),
            Block.box(6.499999999999993, 3, 6.5, 9.499999999999993, 4, 9.5),
            Block.box(6.999999999999993, -0.5, 7, 8.999999999999993, 3, 9),
            Block.box(7.499999999999993, -1.5, 7.5, 8.499999999999993, -0.5, 8.5),
            Block.box(6.749999999999993, 7.5, 6.75, 9.249999999999993, 13.5, 9.25),
            Block.box(8.749999999999993, 8.5, 7.25, 9.749999999999993, 12.5, 8.75),
            Block.box(9.499999999999993, 8.5, 7.5, 9.499999999999993, 15.5, 8.5),
            Block.box(2.249999999999993, 7.5, 4.75, 6.249999999999993, 11.5, 5.75),
            Block.box(2.5499999999999936, 8, 5.75, 5.849999999999994, 11.1, 6.75),
            Block.box(2.5499999999999936, 8, 8.25, 5.549999999999994, 11, 9.25),
            Block.box(-0.970000000000006, 8, 7.5, 2.7999999999999936, 9, 8.5),
            Block.box(2.6499999999999915, 7.9, 7.75, 5.849999999999994, 11.1, 8.75),
            Block.box(2.499999999999993, 7.75, 6.75, 5.999999999999993, 11.25, 7.75),
            Block.box(2.499999999999993, 7.8, 9.25, 5.999999999999993, 11.3, 10.25),
            Block.box(2.249999999999993, 7.5, 10.25, 6.249999999999993, 11.5, 11.249999999999993),
            Block.box(3.749999999999993, 9, 11.249999999999993, 4.749999999999993, 10, 12.249999999999993),
            Block.box(3.749999999999993, 9, 3.75, 4.749999999999993, 10, 4.75),
            Block.box(3.749999999999993, 8.25, 11.749999999999993, 7.749999999999993, 10.75, 11.749999999999993),
            Block.box(3.749999999999993, 8.25, 4.25, 7.749999999999993, 10.75, 4.25),
            Block.box(7.285529999999994, 5.46447, 9.75, 8.285529999999994, 6.46447, 11.249999999999993),
            Block.box(7.285529999999994, 5.46447, 4.75, 8.285529999999994, 6.46447, 6.25),
            Block.box(3.749999999999993, 9, 5.75, 4.749999999999993, 10, 10.25),
            Block.box(-1.000000000000007, 8.5, 6.75, 0.2499999999999929, 9.75, 9.25),
            Block.box(-0.7500000000000071, 8.75, 8.5, 2.249999999999993, 9.75, 9.5),
            Block.box(1.249999999999993, 8.75, 7.5, 6.249999999999993, 9.75, 8.5),
            Block.box(3.999999999999993, 4.3, 7.5, 4.999999999999993, 5.3, 8.5),
            Block.box(4.999999999999993, 4.05, 7.25, 6.249999999999993, 5.55, 8.75),
            Block.box(-0.7500000000000071, 8.75, 6.5, 2.249999999999993, 9.75, 7.5),
            Block.box(7.124999999999993, 13.5, 7.125, 8.874999999999993, 15.5, 8.875),
            Block.box(5.749999999999993, -2, 8, 10.249999999999993, 0.5, 8),
            Block.box(6.749999999999993, -3.5, 8, 9.249999999999993, -2, 8),
            Block.box(7.499999999999993, -4.5, 8, 8.499999999999993, -3.5, 8),
            Block.box(7.999999999999993, -2, 5.75, 7.999999999999993, 0.5, 10.25),
            Block.box(7.999999999999993, -3.5, 6.75, 7.999999999999993, -2, 9.25),
            Block.box(7.999999999999993, -4.5, 7.5, 7.999999999999993, -3.5, 8.5),
            Block.box(6.499999999999993, 0.5, 8, 9.499999999999993, 3, 8),
            Block.box(7.999999999999993, 0.5, 6.5, 7.999999999999993, 3, 9.5)
    );

    public ZiplinePoleBlock(Properties p_49224_)
    {
        super(p_49224_);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pLevel instanceof ServerLevel serverLevel && pLevel.getBlockState(pPos).getBlock() == ModBlocks.ZIPLINE_POLE_BLOCK.get())
        {
            if (pState.getValue(HALF) == DoubleBlockHalf.UPPER)
            {
                    if (getLinkedEntity(serverLevel, pPos) != null)
                    {
                        ZiplineEntity linkedEntity = getLinkedEntity(serverLevel, pPos);

                        ZiplineMoveEntity ziplineMoveEntity = new ZiplineMoveEntity(pLevel, pPos, linkedEntity.blockPosition());
                        pLevel.addFreshEntity(ziplineMoveEntity);
                        pPlayer.startRiding(ziplineMoveEntity);

                        return InteractionResult.SUCCESS;
                    }
                    else
                    {
                        pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.zipline_block.link_invalid"), true);
                    }
            }
            else
            {
                if (getLinkedEntity(serverLevel, pPos.above()) != null)
                {
                    ZiplineEntity linkedEntity = getLinkedEntity(serverLevel, pPos.above());

                    ZiplineMoveEntity ziplineMoveEntity = new ZiplineMoveEntity(pLevel, pPos, linkedEntity.blockPosition());
                    pLevel.addFreshEntity(ziplineMoveEntity);
                    pPlayer.startRiding(ziplineMoveEntity);

                    return InteractionResult.SUCCESS;
                }
                else
                {
                    pPlayer.displayClientMessage(new TranslatableComponent("message.lootdebugs.zipline_block.link_invalid"), true);
                }
            }
        }
        return InteractionResult.PASS;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ZiplinePoleTile(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        if(pState.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            switch (pState.getValue(FACING))
            {
                case NORTH, SOUTH: return NORTH_SOUTH_UPPER;
                case EAST, WEST: return EAST_WEST_UPPER;
            }
        }

        switch (pState.getValue(FACING))
        {
            case NORTH: return NORTH_LOWER;
            case EAST: return EAST_LOWER;
            case SOUTH: return SOUTH_LOWER;
            case WEST: return WEST_LOWER;
        }

        return Shapes.block();
    }

    /**
     * place upper and lower blocks, the string-anchor entity and links everything together
     *
     * @param pLevel serverlevel
     * @param pos place - base pos
     * @param linkedEntity  zipline entity which should be linked in the block
     * @param direction the direction  which the block should be placed
     *
     */
    public static void placeBlock(Level pLevel, BlockPos pos, @NotNull ZiplineEntity linkedEntity, Direction direction)
    {
        if(pLevel instanceof ServerLevel)
        {
            if (pos != null) {
                BlockPos posUpper = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());

                pLevel.setBlock(pos, ModBlocks.ZIPLINE_POLE_BLOCK.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, direction), 2);//place both blocks
                pLevel.setBlock(posUpper, ModBlocks.ZIPLINE_POLE_BLOCK.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, direction), 2);

                if (pLevel.getBlockEntity(posUpper) instanceof ZiplinePoleTile ziplinePoleTile)
                {
                    ZiplineStringAnchor ziplineStringAnchor = placeAnchor(pLevel, posUpper, linkedEntity);//spawn zipline entity

                    ziplinePoleTile.setLinkedEntityUUID(linkedEntity.getUUID());//save zipline-entity in block
                    ziplinePoleTile.setThisAnchor(ziplineStringAnchor.getUUID());// save anchor-entity in block
                }
            }
        }
    }

    public static void removeBlock(Level level, BlockPos ziplineMountBase)
    {
        if(level.getBlockState(ziplineMountBase).is(ModBlocks.ZIPLINE_POLE_BLOCK.get()))
        {
            ZiplinePoleBlock.removeZipline(level, ziplineMountBase);

            level.removeBlock(ziplineMountBase.above(1), false);
            level.removeBlockEntity(ziplineMountBase.above(1));

            level.removeBlock(ziplineMountBase, false);
            level.removeBlockEntity(ziplineMountBase);
        }
    }

    public static ZiplineStringAnchor placeAnchor(Level pLevel, BlockPos pos, Entity linkedEntity)
    {
        ZiplineStringAnchor anchor = new ZiplineStringAnchor(pLevel, pos, linkedEntity);
        pLevel.addFreshEntity(anchor);
        return anchor;
    }

    /**
     * /
     * remove the "hook", string and the string anchor, but NOT the block itself
     */
    public static void removeZipline(Level pLevel, BlockPos pPos)
    {
        BlockState pState = pLevel.getBlockState(pPos);

        if(pLevel instanceof ServerLevel serverLevel)
        {
            DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);

            if (doubleblockhalf == DoubleBlockHalf.UPPER)
            {
                if (pLevel.getBlockEntity(pPos) instanceof ZiplinePoleTile ziplinePoleTile)
                {
                    if (ziplinePoleTile.getThisAnchor(serverLevel) != null)
                    {
                        ziplinePoleTile.getThisAnchor(serverLevel).discard();
                    }

                    if (ziplinePoleTile.getLinkedEntity(serverLevel) != null)
                    {
                        ziplinePoleTile.getLinkedEntity(serverLevel).discard();//remove string aswell
                    }
                }
            }
            else if (doubleblockhalf == DoubleBlockHalf.LOWER)
            {
                BlockPos blockpos = pPos.above();
                BlockState blockstate = pLevel.getBlockState(blockpos);

                if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER)
                {
                    if (pLevel.getBlockEntity(blockpos) instanceof ZiplinePoleTile ziplinePoleTile)
                    {
                        if (ziplinePoleTile.getThisAnchor(serverLevel) != null)
                        {
                            ziplinePoleTile.getThisAnchor(serverLevel).discard();
                        }

                        if (ziplinePoleTile.getLinkedEntity(serverLevel) != null)
                        {
                            ziplinePoleTile.getLinkedEntity(serverLevel).discard();
                        }
                    }
                }
            }
        }

    }


    @Nullable
    public static ZiplineEntity getLinkedEntity(ServerLevel pLevel, BlockPos pos)
    {
        if(pLevel.getBlockEntity(pos) instanceof ZiplinePoleTile ziplinePoleTile)
        {
            return ziplinePoleTile.getLinkedEntity(pLevel);
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            return createTickerHelper(pBlockEntityType, ModTileEntities.ZIPLINE_POLE_ENTITY.get(),
                    ZiplinePoleTile::tick);
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
    public boolean onDestroyedByPlayer(BlockState pState, Level pLevel, BlockPos pPos, Player player, boolean willHarvest, FluidState fluid) {

        removeZipline(pLevel, pPos);
        return super.onDestroyedByPlayer(pState, pLevel, pPos, player, willHarvest, fluid);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative())
        {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public static void preventCreativeDropFromBottomPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);

        if (doubleblockhalf == DoubleBlockHalf.UPPER)
        {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && blockstate.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                pLevel.setBlock(blockpos, blockstate1, 35);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }
        else if (doubleblockhalf == DoubleBlockHalf.LOWER)
        {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER)
            {
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
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER || blockstate.is(this);
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

    public long getSeed(BlockState pState, BlockPos pPos) {
        return Mth.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pPos.getZ());
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, HALF);

    }
}
