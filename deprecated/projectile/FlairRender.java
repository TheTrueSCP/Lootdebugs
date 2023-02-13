package net.the_goldbeards.lootdebugs.client.Render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.the_goldbeards.lootdebugs.Entities.Projectile.FlairEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

@OnlyIn(Dist.CLIENT)
public class FlairRender extends ArrowRenderer<FlairEntity> {
    public FlairRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FlairEntity pEntity) {
        Item referenceItem = pEntity.getPickupItem().getItem();
        return new ResourceLocation(LootDebugsMain.MOD_ID, "textures/entity/projectiles/flair.png");
    }
}
