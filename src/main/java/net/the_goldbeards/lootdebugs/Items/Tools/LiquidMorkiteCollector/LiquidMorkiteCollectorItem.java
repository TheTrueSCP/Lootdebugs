package net.the_goldbeards.lootdebugs.Items.Tools.LiquidMorkiteCollector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.the_goldbeards.lootdebugs.init.ModFluids;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class LiquidMorkiteCollectorItem extends Item implements IFuelCountRefinery {

    private static final int maxFluidAmount = 10;


    public LiquidMorkiteCollectorItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        Level pLevel = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        ItemStack pUsedStack = pContext.getItemInHand();
        Direction direction = pContext.getClickedFace();

        BlockPos posFluidPlace = clickedPos.relative(direction);
        FluidState pFluidStatePlace = pLevel.getFluidState(posFluidPlace);


         if(pFluidStatePlace.is(ModFluids.LIQUID_MORKITE.get()))
         {
             if(storeFluid(pUsedStack, 1))
             {
                 pLevel.setBlockAndUpdate(posFluidPlace, Blocks.AIR.defaultBlockState());

                 if(pLevel instanceof ServerLevel)
                 {
                     pLevel.playSound(null, posFluidPlace, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1, 1);
                 }

                 return InteractionResult.SUCCESS;
             }
         }
         else if(pFluidStatePlace.isEmpty() && decreaseFluid(pUsedStack, 1))
         {
             pLevel.setBlockAndUpdate(posFluidPlace, ModFluids.LIQUID_MORKITE_BLOCK.get().defaultBlockState());

             if(pLevel instanceof ServerLevel)
             {
                 pLevel.playSound(null, posFluidPlace, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1, 1);
             }

             return InteractionResult.SUCCESS;
         }

        return InteractionResult.FAIL;
    }

    private static boolean decreaseFluid(ItemStack pStack ,int amount)
    {
        if(haveEnoughFluidToDecrease(pStack, amount))
        {
            int oldFluidCount = getFluidAmount(pStack);
           setFluidAmount(pStack, oldFluidCount - amount);
            return true;
        }

        return false;
    }


    private static boolean storeFluid(ItemStack pStack, int amount)
    {
        if(havePlaceToStore(pStack, amount))
        {
            int oldFluidCount = getFluidAmount(pStack);
            setFluidAmount(pStack, oldFluidCount + amount);
            return true;
        }

        return false;
    }

    private static boolean haveEnoughFluidToDecrease(ItemStack pStack, int amount)
    {
        return getFluidAmount(pStack) - amount >= 0;
    }

    private static boolean havePlaceToStore(ItemStack pStack, int amount)
    {
        return getFluidAmount(pStack) + amount <= maxFluidAmount;
    }

    private static int getFluidAmount(ItemStack pStack)
    {
        return ModUtils.ItemNBTHelper.getInt(pStack, "fluidCount");
    }

    private static void setFluidAmount(ItemStack pStack, int amount)
    {
        ModUtils.ItemNBTHelper.putInt(pStack, "fluidCount", amount);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack pStack)
    {
        return Math.round(13.0f / maxFluidAmount * getFluidAmount(pStack));
    }

    @Override
    public int getBarColor(ItemStack pStack) {

        return Integer.MAX_VALUE;
    }

    //Fuel Refinery
    @Override
    public int getFuelCountRefinery(ItemStack pStack) {
        return (int) (getFluidAmount(pStack) * maxFluidAmount);
    }
}
