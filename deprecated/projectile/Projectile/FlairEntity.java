package net.the_goldbeards.lootdebugs.Entities.Projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.the_goldbeards.lootdebugs.init.ModEntities;
import net.the_goldbeards.lootdebugs.init.ModItems;

public class FlairEntity extends AbstractArrow {

    private final Item referenceItem;

    public FlairEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.referenceItem = ModItems.FLAIR.get();
    }

    public FlairEntity(LivingEntity shooter, Level level, Item referenceItem) {
        super(ModEntities.FLAIR.get(), shooter, level);
        this.referenceItem = referenceItem;
    }


    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(ModItems.FLAIR.get());
    }


}
