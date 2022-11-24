package net.the_goldbeards.lootdebugs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModTags
{
    public static class Blocks
    {
        public static final TagKey<Block> NOT_MINEABLE_WITH_DRILLS = createTag("not_mineable_with_drills");

        public static final TagKey<Block> LOOTBUG_CAN_SPAWN_ON = createTag("lootbug_can_spawn_on");

        public static final TagKey<Block> NEED_COMPANY_STANDARD_TOOL = createTag("need_company_standard_tool");

        public static final TagKey<Block> MC_ORES = createTag("mc_ores");

        public static final TagKey<Block> BEER_CAN_PLACE_ON = createTag("beer_can_place_on");

        public static final TagKey<Block> PLATFORMGUN_REPLACEABLE_BLOCKS = createTag("platformgun_replaceable_blocks");

        public static final TagKey<Block> SHIELD_BLOCK_REPLACEABLE_BLOCKS = createTag("shield_block_replaceable_blocks");

        public static final TagKey<Block> GOLD_BLOCK_REPLACEABLE_BLOCKS = createTag("gold_block_replaceable_blocks");



        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(new ResourceLocation(LootDebugsMain.MOD_ID, name));
        }

        private static TagKey<Block> createForgeTag(String name)
        {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }

    public static class Items
    {
        public static final TagKey<Item> LOOTBUG_BREEDING_ITEMS = createTag("lootbug/lootbug_breeding_items");

        public static final TagKey<Item> LOOTBUG_TEMPTATION_ITEMS = createTag("lootbug/lootbug_temptation_items");

        public static final TagKey<Item> LOOTBUG_CONSUMABLE_ITEMS = createTag("lootbug/consumable_item_items");

        public static final TagKey<Item> FUELPRESS_WOOD = createTag("fuelpress/wood");

        public static final TagKey<Item> LIQUID_FUEL = createTag("fuelrefinery/ld_liquid_fuel");

        public static final TagKey<Item> SOLID_FUEL = createTag("fuelrefinery/ld_solid_fuel");


        private static TagKey<Item> createTag(String name)
        {
            return ItemTags.create(new ResourceLocation(LootDebugsMain.MOD_ID, name));
        }

        private static TagKey<Item> createForgeTag(String name)
        {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}
