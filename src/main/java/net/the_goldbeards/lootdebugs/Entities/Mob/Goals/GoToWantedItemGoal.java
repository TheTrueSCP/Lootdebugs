package net.the_goldbeards.lootdebugs.Entities.Mob.Goals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GoToWantedItemGoal extends Goal
{
    private final int itemDistance;
    private final TagKey<Item> items;
    private final PathfinderMob mob;

    public GoToWantedItemGoal(PathfinderMob pMob, TagKey<Item> pItems, int pItemDistance)
    {
        this.itemDistance = pItemDistance;
        this.items = pItems;
        this.mob = pMob;
    }



    @Override
    public boolean canUse()
    {
        return !getNearWantedItems(mob, itemDistance, items).isEmpty();
    }

    @Override
    public void tick()
    {
        if(!getNearWantedItems(mob, itemDistance, items).isEmpty())
        {
            this.mob.getLookControl().setLookAt(getNearWantedItems(mob, itemDistance, items).get(0), (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
            this.mob.getNavigation().moveTo(getNearWantedItems(mob, itemDistance, items).get(0), 1.2f);
        }
        }

    private List<ItemEntity> getNearWantedItems(Entity entity, int pRange, TagKey<Item> wantedItems)
    {
        List<ItemEntity> wantedItemEntitiesInRange = new ArrayList<>();
        List<ItemEntity> nearItems = getNearItems(entity, pRange);

        if(nearItems.size() >= 1)
        {
            for (int i = 0; i < nearItems.size() - 1; i++)
            {

                if (nearItems.get(i).getItem().is(wantedItems)) {
                    wantedItemEntitiesInRange.add(nearItems.get(i));
                }

            }
        }
        return wantedItemEntitiesInRange;
    }

    @Nullable
    private List<ItemEntity> getNearItems(Entity pEntity, int pRange)
    {
        List<ItemEntity> itemEntitiesInRange = new ArrayList<>();
        Level level = pEntity.getLevel();
        List<Entity> nearEntities = level.getEntities(pEntity, searchBound(pEntity, pRange));

        for(int i = 0; i < nearEntities.size() - 1; i++)
        {


            if(nearEntities.get(i) instanceof ItemEntity itemEntity)
            {
                itemEntitiesInRange.add(itemEntity);
            }
        }
        return itemEntitiesInRange;

    }

    private AABB searchBound(Entity pEntity, int pRange)
    {
        BlockPos pos1 = new BlockPos(pEntity.blockPosition().getX() - pRange, pEntity.blockPosition().getY() - pRange, pEntity.blockPosition().getZ() - pRange);
        BlockPos pos2 = new BlockPos(pEntity.blockPosition().getX() + pRange, pEntity.blockPosition().getY() + pRange, pEntity.blockPosition().getZ() + pRange);

        return new AABB(pos1, pos2);
    }
}
