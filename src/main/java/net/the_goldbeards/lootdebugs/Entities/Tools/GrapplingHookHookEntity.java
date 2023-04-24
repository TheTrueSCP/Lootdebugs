package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModEntities;

import javax.annotation.Nullable;

public class GrapplingHookHookEntity extends AbstractShootablePhysicsArrowLikeEntity {


    public GrapplingHookHookEntity(EntityType<? extends GrapplingHookHookEntity> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    public GrapplingHookHookEntity(Level p_36861_, double p_36862_, double p_36863_, double p_36864_) {
        super(ModEntities.GRAPPLING_HOOK_HOOK.get(), p_36862_, p_36863_, p_36864_, p_36861_);
    }

    public GrapplingHookHookEntity(Level p_36866_, LivingEntity p_36867_) {
        super(ModEntities.GRAPPLING_HOOK_HOOK.get(), p_36867_, p_36866_);
    }

    @Nullable
    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player ? (Player)entity : null;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isInLava())
        {
            this.kill();
        }
    }
}