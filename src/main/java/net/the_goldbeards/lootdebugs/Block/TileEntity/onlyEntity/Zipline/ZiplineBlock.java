package net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Zipline;

import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.the_goldbeards.lootdebugs.Block.TileEntity.withScreen.FuelRefinery.FuelRefineryTile;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineMoveEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.Zipline.ZiplineStringAnchor;
import net.the_goldbeards.lootdebugs.init.BlockEntity.ModTileEntities;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZiplineBlock extends BaseEntityBlock
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ZiplineBlock(Properties p_49224_)
    {
        super(p_49224_);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pState.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            if (!pLevel.isClientSide)
            {
                    if (getLinkedEntity(pLevel, pPos) != null)
                    {
                            ZiplineMoveEntity ziplineMoveEntity = new ZiplineMoveEntity(pLevel, pPos, getLinkedEntity(pLevel, pPos).blockPosition());
                            pLevel.addFreshEntity(ziplineMoveEntity);
                            pPlayer.startRiding(ziplineMoveEntity);
                            return InteractionResult.SUCCESS;
                    }
                    else
                    {
                        pPlayer.displayClientMessage(new TranslatableComponent("block.zipline_block.link_invalid"), true);
                    }
            }
        }
        else if(pState.getValue(HALF) == DoubleBlockHalf.LOWER)
        {
            if(pLevel.getBlockState(pPos.above(1)).getValue(HALF) == DoubleBlockHalf.UPPER)
            {
                pLevel.getBlockState(pPos.above(1)).use(pLevel, pPlayer, pHand, pHit);
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ZiplineTile(pPos, pState);
    }

    public static void placeBlock(Level pLevel, BlockPos pos, @NotNull ZiplineEntity linkedEntity)
    {
        //place blocks, the string anchor entity and links everything together
        if(pos != null)
        {
            BlockPos posUpper = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            pLevel.setBlock(pos, ModBlocks.ZIPLINE_BLOCK.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), 2);
            pLevel.setBlock(posUpper, ModBlocks.ZIPLINE_BLOCK.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 2);

            if (pLevel.getBlockEntity(posUpper) instanceof ZiplineTile ziplineTile)
            {
                ziplineTile.setLinkedEntity(linkedEntity);
                ziplineTile.setThisAnchor(placeAnchor(pLevel, posUpper, linkedEntity));//Spawn anchor and save entity in block
            }
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
    public static void removeZipline(Level pLevel, BlockPos pPos, BlockState pState)
    {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);

        if (doubleblockhalf == DoubleBlockHalf.UPPER)
        {
            if(pLevel.getBlockEntity(pPos) instanceof ZiplineTile ziplineTile)
            {
                if(ziplineTile.getThisAnchor() != null)
                {
                    ziplineTile.getLinkedEntity().discard();
                    ziplineTile.getThisAnchor().discard();
                }
            }
        }
        else if (doubleblockhalf == DoubleBlockHalf.LOWER)
        {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER)
            {
                if(pLevel.getBlockEntity(blockpos) instanceof ZiplineTile ziplineTile)
                {
                    if(ziplineTile.getThisAnchor() != null)
                    {
                        ziplineTile.getLinkedEntity().discard();
                        ziplineTile.getThisAnchor().discard();
                    }
                }
            }
        }

    }


    @Nullable
    public static ZiplineEntity getLinkedEntity(Level pLevel, BlockPos pos)
    {
        if(pLevel.getBlockEntity(pos) instanceof ZiplineTile ziplineTile)
        {
            return ziplineTile.getLinkedEntity();
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            return createTickerHelper(pBlockEntityType, ModTileEntities.ZIPLINE_ENTITY.get(),
                    ZiplineTile::tick);
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

        removeZipline(pLevel, pPos, pState);
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

    public long getSeed(BlockState pState, BlockPos pPos) {
        return Mth.getSeed(pPos.getX(), pPos.below(pState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pPos.getZ());
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
        pBuilder.add(HALF);
    }
}
