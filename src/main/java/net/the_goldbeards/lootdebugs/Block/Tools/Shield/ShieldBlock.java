package net.the_goldbeards.lootdebugs.Block.Tools.Shield;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.Shield.ShieldBlockTile;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class ShieldBlock extends BaseEntityBlock {

    public ShieldBlock(Properties p_49224_) {
        super(p_49224_);
    }

    public static final BooleanProperty HAVEEMITTER = BooleanProperty.create("haveemitter");

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {

        return pAdjacentBlockState.is(this) || pAdjacentBlockState.is(ModBlocks.SHIELD_EMITTER_BLOCK.get());
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 15.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }


    //BLock Stuff

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {

        Vec3 lookAngleEntity = pEntity.getLookAngle();
        Vec3 moveOutsideTheShield = new Vec3(lookAngleEntity.x() * -1,lookAngleEntity.y(),lookAngleEntity.z() * -1);

        if((pEntity instanceof Arrow))
        {
            pEntity.kill();
        }

        if(pEntity instanceof Monster)
        {
            pEntity.setDeltaMovement(moveOutsideTheShield);
        }

        if(pEntity instanceof Player player)
        {
            ((Player) pEntity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,1));
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ShieldBlockTile(pPos,pState);
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        {
            if (pLevel.isClientSide()) {
                return null;
            }
            return (lvl, pos, blockState, t) -> {
                if (t instanceof ShieldBlockTile BE) {
                    BE.tick();
                }
            };
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HAVEEMITTER);
    }
}
