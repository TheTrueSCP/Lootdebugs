package net.the_goldbeards.lootdebugs.Sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

public class ModSoundTypes
{
   public static final ForgeSoundType MORKITE = new ForgeSoundType(1,1,
           () ->ModSounds.MORKITE_MINING.get(),
           ()-> SoundEvents.STONE_STEP,
           ()-> SoundEvents.STONE_PLACE,
           ()-> SoundEvents.STONE_HIT,
           ()->SoundEvents.STONE_FALL);

   public static final ForgeSoundType PLASCRETE_FOAM = new ForgeSoundType(1,1,
           () ->SoundEvents.SAND_BREAK,
           ()-> SoundEvents.SAND_STEP,
           ()-> SoundEvents.SAND_BREAK,
           ()-> SoundEvents.SAND_HIT,
           ()->SoundEvents.SAND_FALL);

}
