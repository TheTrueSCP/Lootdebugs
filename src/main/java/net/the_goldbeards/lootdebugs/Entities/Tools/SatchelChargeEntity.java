package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Block.TileEntity.onlyEntity.SatchelCharge.SatchelChargeBlock;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModUtils;
import org.jetbrains.annotations.Nullable;

public class SatchelChargeEntity extends ThrowableProjectile
{

    public SatchelChargeEntity(EntityType<? extends ThrowableProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public SatchelChargeEntity(LivingEntity pShooter, Level p_37464_) {
        super(ModEntities.SATCHEL_CHARGE.get(), pShooter, p_37464_);
    }


    @Override
    public void tick() {
        super.tick();

        if (this.isInLava())
        {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        Vec3 vec3 = hitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        Level level = this.getLevel();


        BlockPos pos = setSatchelCharge(level, hitResult.getBlockPos(), hitResult.getDirection());

        if(this.getOwner() != null)
        {
            if (this.getOwner() instanceof Player player && pos != null) {
                ItemStack detonator = new ItemStack(ModItems.SATCHEL_CHARGE_DETONATOR.get(), 1);
                ModUtils.ItemNBTHelper.put(detonator, "satchel_charge", NbtUtils.writeBlockPos(pos));
                player.getInventory().add(detonator);
            }
        }

        this.kill();



    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {

        if(!level.isClientSide)
        {
            BlockPos hitPos = pResult.getEntity().blockPosition();
            level.addFreshEntity(new ItemEntity(level, hitPos.getX(), hitPos.getY(), hitPos.getZ(), new ItemStack(ModItems.SATCHEL_CHARGE.get(), 1)));
        }
        this.discard();
    }

    @Nullable
    public BlockPos setSatchelCharge(Level pLevel, BlockPos pPos, Direction direction)
    {

        if(!pLevel.getBlockState(pPos).isCollisionShapeFullBlock(pLevel, pPos))
        {
            ItemEntity satchelCharge = new ItemEntity(pLevel, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.SATCHEL_CHARGE.get(), 1));
            pLevel.addFreshEntity(satchelCharge);
            this.discard();
            return null;
        }

        switch (direction) {
            case UP -> {
                pLevel.setBlock(new BlockPos(pPos.getX(), pPos.getY() + 1, pPos.getZ()), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACE, AttachFace.FLOOR).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX(), pPos.getY() + 1, pPos.getZ());
            }
            case DOWN -> {
                pLevel.setBlock(new BlockPos(pPos.getX(), pPos.getY() - 1, pPos.getZ()), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACE, AttachFace.CEILING).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX(), pPos.getY() - 1, pPos.getZ());
            }


            case NORTH -> {
                pLevel.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ() - 1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACING, Direction.NORTH).setValue(SatchelChargeBlock.FACE, AttachFace.WALL).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ() - 1);
            }

            case EAST -> {
                pLevel.setBlock(new BlockPos(pPos.getX() + 1, pPos.getY(), pPos.getZ()), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACING, Direction.EAST).setValue(SatchelChargeBlock.FACE, AttachFace.WALL).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX() + 1, pPos.getY(), pPos.getZ());
            }

            case SOUTH -> {
                pLevel.setBlock(new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ() + 1), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACING, Direction.SOUTH).setValue(SatchelChargeBlock.FACE, AttachFace.WALL).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX(), pPos.getY(), pPos.getZ() + 1);
            }

            case WEST -> {
                pLevel.setBlock(new BlockPos(pPos.getX() - 1, pPos.getY(), pPos.getZ()), ModBlocks.SATCHEL_CHARGE.get().defaultBlockState().setValue(SatchelChargeBlock.FACING, Direction.WEST).setValue(SatchelChargeBlock.FACE, AttachFace.WALL).setValue(SatchelChargeBlock.ACTIVATED, true), 2);
                return new BlockPos(pPos.getX() - 1, pPos.getY(), pPos.getZ());
            }
        }
        return null;
    }

    @Override
    protected void defineSynchedData() {

    }
}
