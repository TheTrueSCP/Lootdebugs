package net.the_goldbeards.lootdebugs.client.Render.Entites;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugOldEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugOldModel;


public class LootbugOldRender extends MobRenderer<LootbugOldEntity, LootbugOldModel<LootbugOldEntity>>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/lootbug_old.png");

    public LootbugOldRender(EntityRendererProvider.Context context) {
        super(context, new LootbugOldModel<>(context.bakeLayer(LootbugOldModel.LAYER_LOCATION)), 0.7F);
        this.shadowRadius = 0.7F;
    }

    @Override
    public ResourceLocation getTextureLocation(LootbugOldEntity pEntity) {
        return TEXTURE;
    }
}
