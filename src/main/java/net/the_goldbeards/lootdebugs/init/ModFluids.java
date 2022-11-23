package net.the_goldbeards.lootdebugs.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.the_goldbeards.lootdebugs.LootDebugsMain;

public class ModFluids
{


    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, LootDebugsMain.MOD_ID);

    public static final RegistryObject<FlowingFluid> FLOWING_LQUID_MORKITE = FLUIDS.register("flowing_liquid_morkite",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.LIQUID_MORKITE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> LIQUID_MORKITE = FLUIDS.register("liquid_morkite",
            () -> new ForgeFlowingFluid.Source(ModFluids.LIQUID_MORKITE_PROPERTIES));


    //Fluids

        //Morkite
    public static final ForgeFlowingFluid.Properties LIQUID_MORKITE_PROPERTIES = new ForgeFlowingFluid.Properties(
        () -> LIQUID_MORKITE.get(), () -> FLOWING_LQUID_MORKITE.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
        .density(15).luminosity(2).viscosity(5).sound(SoundEvents.BUCKET_FILL).overlay(WATER_OVERLAY_RL)
        .color(0xbf009c95)).slopeFindDistance(2).levelDecreasePerBlock(2)
        .block(() -> ModFluids.LIQUID_MORKITE_BLOCK.get()).bucket(() -> ModItems.LIQUID_MORKITE_BUCKET.get());



    public static final RegistryObject<LiquidBlock> LIQUID_MORKITE_BLOCK = ModBlocks.BLOCKS.register("liquid_morkite_block",
            () -> new LiquidBlock(() -> ModFluids.LIQUID_MORKITE.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));



    public static void register(IEventBus eventBus)
    {
        FLUIDS.register(eventBus);
    }
}
