package net.the_goldbeards.lootdebugs.client.Render.Entites;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugGoldenEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.Render.Layer.LootbugGoldenEyeLayer;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;


public class LootbugGoldenRender extends MobRenderer<LootbugGoldenEntity, LootbugModel<LootbugGoldenEntity>>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug_golden.png");

    public LootbugGoldenRender(EntityRendererProvider.Context context) {
        super(context, new LootbugModel<>(context.bakeLayer(LootbugModel.LAYER_LOCATION)), 0.7F);
        this.addLayer(new LootbugGoldenEyeLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugGoldenEntity p_114482_) {
        return TEXTURE;
    }
}
