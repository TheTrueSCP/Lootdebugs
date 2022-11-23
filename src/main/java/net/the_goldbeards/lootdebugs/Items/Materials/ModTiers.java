package net.the_goldbeards.lootdebugs.Items.Materials;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.util.ModTags;

public class ModTiers
{
    //Company Standard Tier for Company Standard pickaxe
    public static final ForgeTier COMPANY_STANDARD = new ForgeTier(4, 2031, 9.0F, 5.0F, 0, ModTags.Blocks.NEED_COMPANY_STANDARD_TOOL,
            ()-> Ingredient.of(ModItems.DYSTRUM_INGOT.get()));


}
