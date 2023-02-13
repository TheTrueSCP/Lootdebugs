package net.the_goldbeards.lootdebugs.client.Render;



import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.animal.Pig;
import net.the_goldbeards.lootdebugs.Entities.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.LootbugModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class Lootbug_GoldenRender extends GeoEntityRenderer<LootbugEntity>
{
    protected final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug_golden.png");

    public Lootbug_GoldenRender(EntityRendererProvider.Context entityRender) {
        super(entityRender, new LootbugModel());
        this.shadowRadius = 0.7F;
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugEntity p_114482_) {
        return TEXTURE;
    }
}
