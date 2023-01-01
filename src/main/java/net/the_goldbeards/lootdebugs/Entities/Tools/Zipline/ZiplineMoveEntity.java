package net.the_goldbeards.lootdebugs.Entities.Tools.Zipline;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.the_goldbeards.lootdebugs.Events.ModClientEventBusSubscriber;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import static net.minecraft.world.phys.Vec3.ZERO;

public class ZiplineMoveEntity extends Entity
{

    private BlockPos targetPos;
    private BlockPos startPos;

    private boolean rided = false;

    public ZiplineMoveEntity(EntityType<? extends ZiplineMoveEntity> p_36957_, Level p_36958_) {
        super(p_36957_, p_36958_);
    }

    public ZiplineMoveEntity(Level p_36960_, BlockPos startPos, BlockPos targetPos) {
        this(ModEntities.ZIPLINE_MOVE_ENTITY.get(), p_36960_);
        this.setPos(startPos.getX(), startPos.getY(), startPos.getZ());

        Vec3 moveVec = new Vec3((startPos.getX() - targetPos.getX()) * -1, (startPos.getY() - targetPos.getY()) * -1, (startPos.getZ() - targetPos.getZ()) * -1);


        BlockPos offsetPos = getEntityOffsetRegardingMoveVector(startPos, moveVec);

        this.setPos(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

        this.startPos = startPos;
        this.targetPos = targetPos;
    }

    protected void defineSynchedData()
    {

    }

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        double d3 = vec3.horizontalDistance();

        this.setXRot(lerpRotation(this.xRotO, (float) (Mth.atan2(vec3.y, d3) * (double) (180F / (float) Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI))));

        if (!this.level.isClientSide)
        {
            if(this.getFirstPassenger() != null)
            {
                this.rided = true;
            }

            if(this.getFirstPassenger() == null && rided)
            {
                this.discard();

            }

            if(this.getFirstPassenger() != null)
            {
                if(this.getFirstPassenger() instanceof Player player)
                {
                    String key = ModClientEventBusSubscriber.CHANGE_DIRECTION.getKey().toString().substring(ModClientEventBusSubscriber.CHANGE_DIRECTION.getKey().toString().length() - 1);

                    if(key.equals("y"))
                    {
                        key = "z";
                    }

                    if(key.equals("z"))
                    {
                        key = "y";
                    }
                    player.displayClientMessage(new TextComponent(new TranslatableComponent("entity.zipline_move.change_direction.1").getString() + " " + key + " " + new TranslatableComponent("entity.zipline_move.change_direction.2").getString()), true);
                }
            }

            if(targetPos != null)
            {
                vec3 = new Vec3((this.blockPosition().getX() - targetPos.getX()) * -1, (this.blockPosition().getY() - targetPos.getY()) * -1, (this.blockPosition().getZ() - targetPos.getZ()) * -1);
                this.setDeltaMovement(calaculateVecStrength(vec3.normalize()));

                this.setPos(d0, d1, d2);
                if (!this.level.isClientSide && (this.blockPosition().closerThan(targetPos, 1D))) {
                    this.discard();
                }
            }
        }
        else
        {
            this.setPosRaw(d0, d1, d2);
        }

    }


    //NBT
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.put("TargetPos", NbtUtils.writeBlockPos(this.targetPos));
        pCompound.put("StartPos", NbtUtils.writeBlockPos(this.startPos));
    }

    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        if (pCompound.contains("TargetPos"))
        {
            this.targetPos = NbtUtils.readBlockPos(pCompound.getCompound("TargetPos"));
        }

        if (pCompound.contains("StartPos"))
        {
            this.targetPos = NbtUtils.readBlockPos(pCompound.getCompound("StartPos"));
        }
    }

    //Move Vec Stuff

    private Vec3 calaculateVecStrength(Vec3 input)
    {

        if(input.y() < 1)
        {
            return normalizeTo(input, 0.5f);
        }
        else  if(input.y() > 0)
        {
            return normalizeTo(input, 0.2f);
        }
        else
        {
            return normalizeTo(input, 0.3f);
        }
    }

    private Vec3 normalizeTo(Vec3 input, float modifier)
    {
        double d0 = Math.sqrt(input.x * input.x + input.y * input.y + input.z * input.z);
        return d0 < 1.0E-4D ? ZERO : new Vec3(input.x / d0 * modifier, input.y / d0 * modifier, input.z / d0 * modifier);
    }

    public void changeDirection()
    {
        BlockPos targetTemp = targetPos;
        BlockPos startTemp = startPos;

        //reverse values
        targetPos = startTemp;
        startPos = targetTemp;
    }

    //sets the offset regarding the move vec
    private BlockPos getEntityOffsetRegardingMoveVector(BlockPos pos, Vec3 addVec)
    {
        if(getLongestSide(addVec) == Direction.Axis.X)
        {
            return new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ());
        }
        else if(getLongestSide(addVec) == Direction.Axis.Z)
        {
            return new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 2);
        }

        return pos;
    }

    private Direction.Axis getLongestSide(Vec3 vec3)
    {
        if(vec3.x < vec3.z)
        {
            return Direction.Axis.Z;
        }
        else
        {
            return Direction.Axis.X;
        }
    }

    //Entity Stuff

    @Override
    public double getPassengersRidingOffset() {

        return super.getPassengersRidingOffset() - 1.5;
    }

    public void lerpMotion(double pX, double pY, double pZ) {
        this.setDeltaMovement(pX, pY, pZ);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = Math.sqrt(pX * pX + pZ * pZ);
            this.setYRot((float) (Mth.atan2(pX, pZ) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(pY, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return false;
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while(p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while(p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }
}