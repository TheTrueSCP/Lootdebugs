package net.the_goldbeards.lootdebugs.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.types.templates.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;

import java.util.ArrayList;
import java.util.Map;

public class ModLists
{
    public static class TileEntities
    {
        public static class FuelRefinery
        {
            // 1000 -> 1 Fuel
            public static Map<Item, Integer> fuelAmountBucketItems()
            {
                Map<Item, Integer> fuelAmountItems = Maps.newHashMap();

                fuelAmountItems.put(Items.LAVA_BUCKET, 125);                    //8 Buckets
                fuelAmountItems.put(ModItems.LIQUID_MORKITE_BUCKET.get(), 250); //4Buckets

                return fuelAmountItems;
            }

            public static Map<Item, Integer> fuelAmountItems()
            {
                Map<Item, Integer> fuelAmountItems = Maps.newHashMap();

                fuelAmountItems.put(Items.COAL, 5);                             //22 Blocks for 1 fuel
                fuelAmountItems.put(Items.COAL_BLOCK, 5 * 9);
                fuelAmountItems.put(ModItems.MORKITE.get(), 19);                //6 Blocks
                fuelAmountItems.put(ModBlocks.MORKITE_BLOCK.get().asItem(), 19 * 9);
                fuelAmountItems.put(ModBlocks.OIL_SHALE.get().asItem(), 400);   //2.5 Blocks for 1 fuel

                fuelAmountItems.putAll(fuelAmountBucketItems());

                return fuelAmountItems;
            }
        }
    }
}
