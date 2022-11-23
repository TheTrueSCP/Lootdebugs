package net.the_goldbeards.lootdebugs.client.model;

import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LootbugModel extends AnimatedGeoModel<LootbugEntity>
{
    @Override
    public ResourceLocation getModelLocation(LootbugEntity object)
    {
        return new ResourceLocation(LootDebugsMain.MOD_ID, "geo/lootbug/lootbugmodel.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugEntity object)
    {
        return new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/lootbug.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LootbugEntity object)
    {

        return new ResourceLocation(LootDebugsMain.MOD_ID, "animations/lootbug/walk.json");
    }
}
