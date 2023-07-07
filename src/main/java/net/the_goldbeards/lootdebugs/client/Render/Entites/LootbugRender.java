package net.the_goldbeards.lootdebugs.client.Render.Entites;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.Render.Layer.LootbugEyeLayer;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;


public class LootbugRender extends MobRenderer<LootbugEntity, LootbugModel<LootbugEntity>>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug.png");

    public LootbugRender(EntityRendererProvider.Context context)
    {
        super(context, new LootbugModel<>(context.bakeLayer(LootbugModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new LootbugEyeLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugEntity p_114482_) {
        return TEXTURE;
    }

}
