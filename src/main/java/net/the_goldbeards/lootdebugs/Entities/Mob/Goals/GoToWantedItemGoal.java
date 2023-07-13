package net.the_goldbeards.lootdebugs.Entities.Mob.Goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
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
        return getNearestItem(this.mob, itemDistance, items) != null;
    }

    @Override
    public void tick()
    {
        if(getNearestItem(this.mob, itemDistance, items) != null)
        {
            System.out.println("wannago");
            this.mob.getLookControl().setLookAt(getNearestItem(this.mob, itemDistance, items), (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
            this.mob.getNavigation().moveTo(getNearestItem(this.mob, itemDistance, items), 1.2f);
        }
        }

        @Nullable
        private ItemEntity getNearestItem(Entity mob, int pRange, TagKey<Item> wantedItems)
        {
            List<ItemEntity> wantedNearItems = getNearWantedItems(mob, pRange, wantedItems);


            if(wantedNearItems.size() > 0)
            {
                ItemEntity nearestItem = wantedNearItems.get(0);


                for (ItemEntity itemEntity : wantedNearItems)
                {
                    if(mob.position().distanceTo(itemEntity.position()) < mob.position().distanceTo(nearestItem.position()))
                    {
                        nearestItem = itemEntity;
                    }
                }

                return nearestItem;
            }


            return null;
        }

    private List<ItemEntity> getNearWantedItems(Entity entity, int pRange, TagKey<Item> wantedItems)
    {
        List<ItemEntity> wantedItemEntitiesInRange = NonNullList.create();
        List<ItemEntity> nearItems = getNearItems(entity, pRange);

        System.out.println(nearItems.size());

        if(nearItems.size() > 0)
        {
            for (int i = 0; i < nearItems.size() - 1; i++)
            {
                System.out.println(nearItems.size() + " " + nearItems.get(i).getItem());
                if (nearItems.get(i).getItem().is(wantedItems))
                {

                    System.out.println(nearItems.get(i));
                    wantedItemEntitiesInRange.add(nearItems.get(i));
                }

            }
        }
        return wantedItemEntitiesInRange;
    }

    private List<ItemEntity> getNearItems(Entity pEntity, int pRange)
    {
        Level level = pEntity.getLevel();
        List<Entity> nearEntities = level.getEntities((ItemEntity)null, searchBound(pEntity, pRange), (entity) ->{return entity instanceof ItemEntity;});

        for(Entity entity : nearEntities)
        {
            if(entity instanceof ItemEntity itemEntity)
            {
                List<ItemEntity> itemEntities = NonNullList.create();

                itemEntities.add(itemEntity);

                return itemEntities;
            }
        }

        return NonNullList.create();

    }

    private AABB searchBound(Entity pEntity, int pRange)
    {
        BlockPos pos1 = new BlockPos(pEntity.blockPosition().getX() - pRange, pEntity.blockPosition().getY() - pRange, pEntity.blockPosition().getZ() - pRange);
        BlockPos pos2 = new BlockPos(pEntity.blockPosition().getX() + pRange, pEntity.blockPosition().getY() + pRange, pEntity.blockPosition().getZ() + pRange);

        return new AABB(pos1, pos2);
    }
}
