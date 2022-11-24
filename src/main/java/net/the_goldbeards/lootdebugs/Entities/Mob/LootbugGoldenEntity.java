package net.the_goldbeards.lootdebugs.Entities.Mob;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.the_goldbeards.lootdebugs.init.ModItems;

import java.util.Random;

public class LootbugGoldenEntity extends LootbugEntity
{
    public LootbugGoldenEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.00f)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.JUMP_STRENGTH, 1.15);
    }

    @Override
    public ItemStack getPettingDrop()
    {
        int rand = level.random.nextInt(0, 100);

        if(rand <= 5)
        {
            return new ItemStack(Items.FLINT, 1);
        }
        else if(rand <= 10)
        {
            return new ItemStack(ModItems.RAW_BISMOR.get(), 1);
        }
        else if(rand <= 15)
        {
            return new ItemStack(ModItems.RAW_CROPPER.get(), 1);
        }
        else if(rand <= 20)
        {
            return new ItemStack(ModItems.RAW_DYSTRUM.get(), 1);
        }
        else if(rand <= 40)
        {
            return new ItemStack(ModItems.NITRA.get(), 1);
        }
        else if(rand <= 100)
        {
            return new ItemStack(Items.RAW_GOLD, 1);
        }

        return new ItemStack(ModItems.NITRA.get(), 1);
    }

    @Override
    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 4F;
    }

    public static <T extends Mob> boolean checkLootbugGoldenSpawnRules(EntityType<T> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random)
    {
        return blockPos.getY() <= 20 && random.nextFloat(0, 1) > 0.75f;
    }
}
