package net.the_goldbeards.lootdebugs.Entities.Tools;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModEntities;

public class ZiplineEntity extends ThrowableProjectile
{
    int[] coordinates = {0, 0, 0};

    public ZiplineEntity(LivingEntity shooter, Level level)
    {
        super(ModEntities.ZIPLINE_ENTITY.get(), shooter, level);
    }

    public ZiplineEntity(EntityType<ZiplineEntity> ziplineEntityEntityType, Level level)
    {
        super(ziplineEntityEntityType, level);
    }

    @Override
    protected void defineSynchedData() {

    }
}
