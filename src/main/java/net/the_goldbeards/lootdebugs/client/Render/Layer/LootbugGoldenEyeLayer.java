package net.the_goldbeards.lootdebugs.client.Render.Layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.the_goldbeards.lootdebugs.Entities.Mob.LootbugEntity;
import net.the_goldbeards.lootdebugs.LootDebugsMain;
import net.the_goldbeards.lootdebugs.client.model.Entities.LootbugModel;

public class LootbugGoldenEyeLayer<T extends LootbugEntity, M extends LootbugModel<T>> extends EyesLayer<T, M>
{
    private static final RenderType LOOTBUG_EYES = RenderType.eyes(new ResourceLocation(LootDebugsMain.MOD_ID,"textures/entity/layer/lootbug_golden_eyes.png"));
    public LootbugGoldenEyeLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public RenderType renderType() {
        return LOOTBUG_EYES;
    }
}
