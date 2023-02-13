package net.the_goldbeards.lootdebugs.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public interface ModConfiguredStructureTags
{
        TagKey<ConfiguredStructureFeature<?, ?>> OMMORAN_HEARTHSTONE = create("ommoran_hearthstone");

        private static TagKey<ConfiguredStructureFeature<?, ?>> create(String p_207644_) {
            return TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(LootDebugsMain.MOD_ID, p_207644_));
        }
}
