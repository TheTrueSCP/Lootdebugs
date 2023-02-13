package net.the_goldbeards.lootdebugs.init.Sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

public class ModSoundTypes
{
   public static final ForgeSoundType GENERIC_MINERAL = new ForgeSoundType(1,1,
           ()-> SoundEvents.STONE_PLACE,
           ()-> SoundEvents.STONE_STEP,
           ()-> ModSounds.MINERAL_MINING.get(),
           ()-> ModSounds.MINERAL_MINING.get(),
           ()->SoundEvents.STONE_FALL);


   public static final ForgeSoundType PLASCRETE_FOAM = new ForgeSoundType(1,1,
           () ->SoundEvents.SLIME_BLOCK_PLACE,
           ()-> SoundEvents.STONE_STEP,
           ()-> SoundEvents.SLIME_BLOCK_BREAK,
           ()-> SoundEvents.SLIME_BLOCK_HIT,
           ()->SoundEvents.SLIME_BLOCK_FALL);

   public static final ForgeSoundType VEGETATION = new ForgeSoundType(1,1,
           () ->SoundEvents.STONE_PLACE,
           ()-> SoundEvents.STONE_STEP,
           ()-> ModSounds.VEGETATION_PICKED.get(),
           ()-> SoundEvents.STONE_HIT,
           ()->SoundEvents.STONE_FALL);

   public static final ForgeSoundType OMMORAN_HEARTHSTONE = new ForgeSoundType(1,1,
           () -> SoundEvents.AMETHYST_BLOCK_PLACE,
           ()-> SoundEvents.STONE_STEP,
           ()-> SoundEvents.AMETHYST_BLOCK_PLACE,
           ()-> SoundEvents.STONE_HIT,
           ()->SoundEvents.STONE_FALL);

}
