package net.the_goldbeards.lootdebugs.client.Render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Mob.Layer.LootbugEyeLayer;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.Entities.Tools.ResupplyDropEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;


public class ResupplyDropRender extends EntityRenderer<ResupplyDropEntity>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug.png");

    public ResupplyDropRender(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ResupplyDropEntity pEntity) {
        return TEXTURE;
    }


}
