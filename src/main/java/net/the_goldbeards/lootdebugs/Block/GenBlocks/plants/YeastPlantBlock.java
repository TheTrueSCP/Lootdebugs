package net.the_goldbeards.lootdebugs.Block.GenBlocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.init.ModBlocks;
import net.the_goldbeards.lootdebugs.init.ModItems;
import net.the_goldbeards.lootdebugs.init.Sound.ModSounds;

import javax.annotation.Nullable;
import java.util.Random;

public class YeastPlantBlock extends BushBlock implements BonemealableBlock
{

    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    private static final VoxelShape NS_PICKED = Shapes.or(//North_South_Grown
            Block.box(11, 3, 1.25, 14, 9, 7.25),
            Block.box(11, 7.5, 2.75, 14, 15, 5.75),
            Block.box(14, 3, 2.75, 15.5, 9, 5.75),
            Block.box(9.5, 3, 2.75, 11, 9, 5.75),
            Block.box(0.5, 3, 10.25, 2, 9, 13.25),
            Block.box(2, 3, 8.75, 5, 9, 14.75),
            Block.box(2, 7.5, 10.25, 5, 15, 13.25),
            Block.box(2, 13.5, 10.25, 5, 19.5, 13.25),
            Block.box(2, 18, 12.5, 5, 24, 15.5),
            Block.box(2, 20.25, 1.74264, 5, 23.25, 10.74264),
            Block.box(11, 20.25, 5, 14, 23.25, 14),
            Block.box(2, 23.625, 6.125, 5, 23.625, 9.125),
            Block.box(2, 23.25, 9.875, 5, 23.25, 12.875),
            Block.box(11, 22.875, 3.875, 14, 22.875, 6.875),
            Block.box(11, 23.25, 8, 14, 23.25, 11),
            Block.box(2, 14.85, 1.25, 5, 22.35, 4.25),
            Block.box(11, 15.075, 11.45, 14, 22.575, 14.45),
            Block.box(11, 18, 0.5, 14, 24, 3.5),
            Block.box(11, 13.5, 2.75, 14, 19.5, 5.75),
            Block.box(5, 3, 10.25, 6.5, 9, 13.25),
            Block.box(1.25, 0, 8, 5.75, 4.5, 15.5),
            Block.box(-0.25, 0, 9.5, 2.75, 4.5, 14),
            Block.box(4.25, 0, 9.5, 7.25, 4.5, 14),
            Block.box(8.75, 0, 2, 11.75, 4.5, 6.5),
            Block.box(10.25, 0, 0.5, 14.75, 4.5, 8),
            Block.box(13.25, 0, 2, 16.25, 4.5, 6.5),
            Block.box(1.25, 15, -1.75, 5.75, 16.5, 2.75),
            Block.box(10.25, 15, 12.875, 14.75, 16.5, 17.375)
    );

    private static final VoxelShape NS_GROWN = Shapes.or(//North_South_Grown
            Block.box(11, 3, 1.25, 14, 9, 7.25),
            Block.box(11, 7.5, 2.75, 14, 15, 5.75),
            Block.box(14, 3, 2.75, 15.5, 9, 5.75),
            Block.box(9.5, 3, 2.75, 11, 9, 5.75),
            Block.box(0.5, 3, 10.25, 2, 9, 13.25),
            Block.box(2, 3, 8.75, 5, 9, 14.75),
            Block.box(2, 7.5, 10.25, 5, 15, 13.25),
            Block.box(2, 13.5, 10.25, 5, 19.5, 13.25),
            Block.box(2, 18, 12.5, 5, 24, 15.5),
            Block.box(2, 20.25, 1.7426406871192768, 5, 23.25, 10.742640687119277),
            Block.box(11, 20.25, 5, 14, 23.25, 14),
            Block.box(2, 23.625, 6.125, 5, 23.625, 9.125),
            Block.box(2, 23.25, 9.875, 5, 23.25, 12.875),
            Block.box(11, 22.875, 3.875, 14, 22.875, 6.875),
            Block.box(11, 23.25, 8, 14, 23.25, 11),
            Block.box(2, 14.850000000000001, 1.25, 5, 22.35, 4.25),
            Block.box(11, 15.075000000000001, 11.450000000000001, 14, 22.575000000000003, 14.450000000000003),
            Block.box(11, 18, 0.5, 14, 24, 3.5),
            Block.box(11, 13.5, 2.75, 14, 19.5, 5.75),
            Block.box(5, 3, 10.25, 6.5, 9, 13.25),
            Block.box(1.25, 0, 8, 5.75, 4.5, 15.5),
            Block.box(-0.25, 0, 9.5, 2.75, 4.5, 14),
            Block.box(4.25, 0, 9.5, 7.25, 4.5, 14),
            Block.box(8.75, 0, 2, 11.75, 4.5, 6.5),
            Block.box(10.25, 0, 0.5, 14.75, 4.5, 8),
            Block.box(13.25, 0, 2, 16.25, 4.5, 6.5),
            Block.box(1.25, 15, -1.75, 5.75, 16.5, 2.75),
            Block.box(0.875, 13.5, -1.75, 2.375, 15, 2.75),
            Block.box(4.625, 13.5, -1.75, 6.125, 15, 2.75),
            Block.box(1.25, 13.5, -2.125, 5.75, 15, -0.625),
            Block.box(1.25, 13.5, 1.625, 5.75, 15, 3.125),
            Block.box(1.25, 12.375, 2, 5.75, 13.875, 3.5),
            Block.box(0.5, 12.375, -1.75, 2, 13.875, 2.75),
            Block.box(1.25, 12.375, -2.5, 5.75, 13.875, -1),
            Block.box(5, 12.375, -1.75, 6.5, 13.875, 2.75),
            Block.box(1.25, 11.25, 2, 5.75, 12.75, 3.5),
            Block.box(0.5, 11.25, -1.75, 2, 12.75, 2.75),
            Block.box(1.25, 11.25, -2.5, 5.75, 12.75, -1),
            Block.box(5, 11.25, -1.75, 6.5, 12.75, 2.75),
            Block.box(1.25, 10.125, 2, 5.75, 11.625, 3.875),
            Block.box(0.125, 10.125, -1.75, 2, 11.625, 2.75),
            Block.box(1.25, 10.125, -2.875, 5.75, 11.625, -1),
            Block.box(5, 10.125, -1.75, 6.875, 11.625, 2.75),
            Block.box(1.25, 9, -2.5, 5.75, 10.5, -1),
            Block.box(5, 9, -1.75, 6.5, 10.5, 2.75),
            Block.box(1.25, 9, 2, 5.75, 10.5, 3.5),
            Block.box(0.5, 9, -1.75, 2, 10.5, 2.75),
            Block.box(10.25, 15, 12.875, 14.75, 16.5, 17.375),
            Block.box(10.25, 13.5, 12.5, 14.75, 15, 14),
            Block.box(10.25, 12.375, 12.125, 14.75, 13.875, 13.625),
            Block.box(10.25, 11.25, 12.125, 14.75, 12.75, 13.625),
            Block.box(10.25, 10.125, 11.75, 14.75, 11.625, 13.625),
            Block.box(10.25, 9, 12.125, 14.75, 10.5, 13.625),
            Block.box(14, 9, 12.875, 15.5, 10.5, 17.375),
            Block.box(14, 10.125, 12.875, 15.875, 11.625, 17.375),
            Block.box(14, 11.25, 12.875, 15.5, 12.75, 17.375),
            Block.box(14, 12.375, 12.875, 15.5, 13.875, 17.375),
            Block.box(13.625, 13.5, 12.875, 15.125, 15, 17.375),
            Block.box(9.875, 13.5, 12.875, 11.375, 15, 17.375),
            Block.box(9.5, 12.375, 12.875, 11, 13.875, 17.375),
            Block.box(9.5, 11.25, 12.875, 11, 12.75, 17.375),
            Block.box(9.125, 10.125, 12.875, 11, 11.625, 17.375),
            Block.box(9.5, 9, 12.875, 11, 10.5, 17.375),
            Block.box(10.25, 13.5, 16.25, 14.75, 15, 17.75),
            Block.box(10.25, 11.25, 16.625, 14.75, 12.75, 18.125),
            Block.box(10.25, 10.125, 16.625, 14.75, 11.625, 18.5),
            Block.box(10.25, 9, 16.625, 14.75, 10.5, 18.125),
            Block.box(10.25, 12.375, 16.625, 14.75, 13.875, 18.125),
            Block.box(13.25, 8.25, 13.625, 14.75, 9.75, 17.375),
            Block.box(11.75, 7.5, 15.125, 14, 9, 16.625),
            Block.box(11.75, 7.125, 14.375, 13.25, 8.625, 15.875),
            Block.box(10.25, 8.25, 15.875, 14, 9.75, 17.375),
            Block.box(11, 8.25, 12.875, 14.75, 9.75, 14.375),
            Block.box(10.25, 8.25, 12.875, 11.75, 9.75, 16.625),
            Block.box(11, 7.5, 13.625, 13.25, 9, 15.125),
            Block.box(11, 7.5, 14.375, 12.5, 9, 16.625),
            Block.box(12.5, 7.5, 13.625, 14, 9, 15.875),
            Block.box(2, 7.5, -0.25, 3.5, 9, 2),
            Block.box(2.75, 7.125, -0.25, 4.25, 8.625, 1.25),
            Block.box(2.75, 7.5, 0.5, 5, 9, 2),
            Block.box(3.5, 7.5, -1, 5, 9, 1.25),
            Block.box(2, 7.5, -1, 4.25, 9, 0.5),
            Block.box(1.25, 8.25, -1.75, 2.75, 9.75, 2),
            Block.box(1.25, 8.25, 1.25, 5, 9.75, 2.75),
            Block.box(4.25, 8.25, -1, 5.75, 9.75, 2.75),
            Block.box(2, 8.25, -1.75, 5.75, 9.75, -0.25)
    );

    public static final VoxelShape EW_PICKED = Shapes.or(
            Block.box(8.774214118557694, 3, 11.024214118557694, 14.774214118557694, 9, 14.024214118557694),
            Block.box(10.274214118557694, 7.5, 11.024214118557694, 13.274214118557694, 15, 14.024214118557694),
            Block.box(10.274214118557694, 3, 14.024214118557694, 13.274214118557694, 9, 15.524214118557694),
            Block.box(10.274214118557694, 3, 9.524214118557694, 13.274214118557694, 9, 11.024214118557694),
            Block.box(2.774214118557694, 3, 0.5242141185576941, 5.774214118557694, 9, 2.024214118557694),
            Block.box(1.274214118557694, 3, 2.024214118557694, 7.274214118557694, 9, 5.024214118557694),
            Block.box(2.774214118557694, 7.5, 2.024214118557694, 5.774214118557694, 15, 5.024214118557694),
            Block.box(3.4106376756724455, 14.85561726365291, 2.024214118557694, 6.4106376756724455, 20.85561726365291, 5.024214118557694),
            Block.box(-1.5594163385699638, 12.186428889821947, 2.024214118557694, 1.4405836614300362, 18.186428889821947, 5.024214118557694),
            Block.box(5.281574118557694, 20.25, 2.024214118557694, 14.281574118557694, 23.25, 5.024214118557694),
            Block.box(2.024214118557694, 20.25, 11.024214118557694, 11.024214118557694, 23.25, 14.024214118557694),
            Block.box(2.563879881746935, 22.576297001534705, 2.024214118557694, 5.563879881746935, 22.576297001534705, 5.024214118557694),
            Block.box(-1.071939417019994, 21.627271852987068, 2.024214118557694, 1.928060582980006, 21.627271852987068, 5.024214118557694),
            Block.box(13.480861975319575, 21.807764317531998, 11.024214118557694, 16.480861975319577, 21.807764317531998, 14.024214118557694),
            Block.box(9.470042676552644, 22.756789466079635, 11.024214118557694, 12.470042676552644, 22.756789466079635, 14.024214118557694),
            Block.box(14.939782135902554, 12.42048508985904, 2.024214118557694, 17.939782135902554, 19.920485089859042, 5.024214118557694),
            Block.box(-1.5950402788360414, 12.664017773861744, 11.024214118557694, 1.4049597211639586, 20.164017773861744, 14.024214118557694),
            Block.box(14.593660273435164, 12.152184954956745, 11.024214118557694, 17.593660273435162, 18.152184954956745, 14.024214118557694),
            Block.box(9.634104181394065, 14.874149947655617, 11.024214118557694, 12.634104181394065, 20.874149947655617, 14.024214118557694),
            Block.box(2.774214118557694, 3, 5.024214118557694, 5.774214118557694, 9, 6.524214118557694),
            Block.box(0.5242141185576941, 0, 1.274214118557694, 8.024214118557694, 4.5, 5.774214118557694),
            Block.box(2.024214118557694, 0, -0.22578588144230594, 6.524214118557694, 4.5, 2.774214118557694),
            Block.box(2.024214118557694, 0, 4.274214118557694, 6.524214118557694, 4.5, 7.274214118557694),
            Block.box(9.524214118557694, 0, 8.774214118557694, 14.024214118557694, 4.5, 11.774214118557694),
            Block.box(8.024214118557694, 0, 10.274214118557694, 15.524214118557694, 4.5, 14.774214118557694),
            Block.box(9.524214118557694, 0, 13.274214118557694, 14.024214118557694, 4.5, 16.274214118557694),
            Block.box(13.274214118557694, 15, 1.274214118557694, 17.774214118557694, 16.5, 5.774214118557694),
            Block.box(-1.350785881442306, 15, 10.274214118557694, 3.149214118557694, 16.5, 14.774214118557694)
    );

    public static final VoxelShape EW_GROWN = Shapes.or(
            box(8.631430178135064, 3, 10.881430178135064, 14.631430178135064, 9, 13.881430178135064),
            Block.box(10.131430178135064, 7.5, 10.881430178135064, 13.131430178135064, 15, 13.881430178135064),
        Block.box(10.131430178135064, 3, 13.881430178135064, 13.131430178135064, 9, 15.381430178135064),
        Block.box(10.131430178135064, 3, 9.381430178135064, 13.131430178135064, 9, 10.881430178135064),
        Block.box(2.631430178135064, 3, 0.38143017813506397, 5.631430178135064, 9, 1.881430178135064),
        Block.box(1.131430178135064, 3, 1.881430178135064, 7.131430178135064, 9, 4.881430178135064),
        Block.box(2.631430178135064, 7.5, 1.881430178135064, 5.631430178135064, 15, 4.881430178135064),
        Block.box(3.7074655683274784, 14.824976016433844, 1.881430178135064, 6.707465568327478, 20.824976016433844, 4.881430178135064),
        Block.box(-2.4525936934322328, 11.757319671221342, 1.881430178135064, 0.5474063065677672, 17.757319671221342, 4.881430178135064),
        Block.box(5.138790178135064, 20.25, 1.881430178135064, 14.138790178135064, 23.25, 4.881430178135064),
        Block.box(1.881430178135064, 20.25, 10.881430178135064, 10.881430178135064, 23.25, 13.881430178135064),
        Block.box(2.0032216688363444, 22.43637365750055, 1.881430178135064, 5.0032216688363444, 22.43637365750055, 4.881430178135064),
        Block.box(-1.6325976299305847, 21.487348508952916, 1.881430178135064, 1.3674023700694153, 21.487348508952916, 4.881430178135064),
        Block.box(13.777689867974608, 21.777123070312932, 10.881430178135064, 16.77768986797461, 21.777123070312932, 13.881430178135064),
        Block.box(9.766870569207676, 22.72614821886057, 10.881430178135064, 12.766870569207676, 22.72614821886057, 13.881430178135064),
        Block.box(15.236610028557584, 12.389843842639971, 1.881430178135064, 18.236610028557585, 19.889843842639973, 4.881430178135064),
        Block.box(-2.155698491746632, 12.524094429827592, 10.881430178135064, 0.844301508253368, 20.024094429827592, 13.881430178135064),
        Block.box(15.284910643262677, 11.925002721390896, 10.881430178135064, 18.284910643262677, 17.925002721390896, 13.881430178135064),
        Block.box(9.073445968483474, 14.734226603621465, 10.881430178135064, 12.073445968483474, 20.734226603621465, 13.881430178135064),
        Block.box(2.631430178135064, 3, 4.881430178135064, 5.631430178135064, 9, 6.381430178135064),
        Block.box(0.38143017813506397, 0, 1.131430178135064, 7.881430178135064, 4.5, 5.631430178135064),
        Block.box(1.881430178135064, 0, -0.36856982186493603, 6.381430178135064, 4.5, 2.631430178135064),
        Block.box(1.881430178135064, 0, 4.131430178135064, 6.381430178135064, 4.5, 7.131430178135064),
        Block.box(9.381430178135064, 0, 8.631430178135064, 13.881430178135064, 4.5, 11.631430178135064),
        Block.box(7.881430178135064, 0, 10.131430178135064, 15.381430178135064, 4.5, 14.631430178135064),
        Block.box(9.381430178135064, 0, 13.131430178135064, 13.881430178135064, 4.5, 16.131430178135062),
        Block.box(13.131430178135064, 15, 1.131430178135064, 17.631430178135062, 16.5, 5.631430178135064),
        Block.box(13.131430178135064, 10.776088063146132, 0.1874001249165982, 17.631430178135062, 12.276088063146132, 1.6874001249165982),
        Block.box(13.131430178135064, 14.220238954431938, 5.760544438751948, 17.631430178135062, 15.720238954431938, 7.260544438751948),
        Block.box(16.8561244191578, 9.673412472457054, 1.131430178135064, 18.3561244191578, 11.173412472457054, 5.631430178135064),
        Block.box(11.282980105322453, 15.322914545121016, 1.131430178135064, 12.782980105322453, 16.822914545121016, 5.631430178135064),
        Block.box(11.352771554387312, 14.211796927477366, 1.131430178135064, 12.852771554387312, 15.711796927477366, 5.631430178135064),
        Block.box(13.131430178135064, 9.66497044550248, 0.25719157398145853, 17.631430178135062, 11.16497044550248, 1.7571915739814585),
        Block.box(16.786332970092943, 8.562294854813402, 1.131430178135064, 18.286332970092943, 10.062294854813402, 5.631430178135064),
        Block.box(13.131430178135064, 13.109121336788286, 5.690752989687088, 17.631430178135062, 14.609121336788286, 7.190752989687088),
        Block.box(11.783290415798039, 13.172432453402168, 1.131430178135064, 13.283290415798039, 14.672432453402168, 5.631430178135064),
        Block.box(13.131430178135064, 8.625605971427282, 0.6877104353921837, 17.631430178135062, 10.125605971427282, 2.1877104353921837),
        Block.box(16.355814108682218, 7.522930380738202, 1.131430178135064, 17.855814108682218, 9.022930380738202, 5.631430178135064),
        Block.box(13.131430178135064, 12.069756862713088, 5.260234128276362, 17.631430178135062, 13.569756862713088, 6.760234128276362),
        Block.box(11.853081864862899, 12.061314835758516, 1.131430178135064, 13.728081864862899, 13.561314835758516, 5.631430178135064),
        Block.box(13.131430178135064, 7.51448835378363, 0.757501884457044, 17.631430178135062, 9.01448835378363, 2.632501884457044),
        Block.box(15.911022659617359, 6.4118127630945505, 1.131430178135064, 17.78602265961736, 7.9118127630945505, 5.631430178135064),
        Block.box(13.131430178135064, 10.958639245069438, 4.815442679211503, 17.631430178135062, 12.458639245069438, 6.690442679211503),
        Block.box(15.494776385860764, 5.444201432587809, 1.131430178135064, 16.994776385860764, 6.944201432587809, 5.631430178135064),
        Block.box(13.131430178135064, 9.991027914562693, 4.39919640545491, 17.631430178135062, 11.491027914562693, 5.89919640545491),
        Block.box(12.64432813861949, 11.093703505251773, 1.131430178135064, 14.14432813861949, 12.593703505251773, 5.631430178135064),
        Block.box(13.131430178135064, 6.5468770232768865, 1.5487481582136358, 17.631430178135062, 8.046877023276886, 3.0487481582136358),
        Block.box(-1.493569821864936, 15, 10.131430178135064, 3.006430178135064, 16.5, 14.631430178135064),
        Block.box(3.344386256180231, 15.27015767079649, 10.131430178135064, 4.844386256180231, 16.77015767079649, 14.631430178135064),
        Block.box(3.2745948071153723, 14.159040053152838, 10.131430178135064, 4.774594807115372, 15.659040053152838, 14.631430178135064),
        Block.box(2.844075945704647, 13.11967557907764, 10.131430178135064, 4.344075945704647, 14.61967557907764, 14.631430178135064),
        Block.box(2.399284496639787, 12.008557961433988, 10.131430178135064, 4.274284496639787, 13.508557961433988, 14.631430178135064),
        Block.box(1.983038222883195, 11.040946630927245, 10.131430178135064, 3.483038222883195, 12.540946630927245, 14.631430178135064),
        Block.box(-1.493569821864936, 6.5468770232768865, 12.714112198056492, 3.006430178135064, 8.046877023276886, 14.214112198056492),
        Block.box(-1.493569821864936, 7.51448835378363, 13.130358471813084, 3.006430178135064, 9.01448835378363, 15.005358471813084),
        Block.box(-1.493569821864936, 8.625605971427282, 13.575149920877944, 3.006430178135064, 10.125605971427282, 15.075149920877944),
        Block.box(-1.493569821864936, 9.66497044550248, 14.00566878228867, 3.006430178135064, 11.16497044550248, 15.50566878228867),
        Block.box(-1.493569821864936, 10.776088063146132, 14.07546023135353, 3.006430178135064, 12.276088063146132, 15.57546023135353),
        Block.box(-1.493569821864936, 14.220238954431938, 8.50231591751818, 3.006430178135064, 15.720238954431938, 10.00231591751818),
        Block.box(-1.493569821864936, 13.109121336788286, 8.57210736658304, 3.006430178135064, 14.609121336788286, 10.07210736658304),
        Block.box(-1.493569821864936, 12.069756862713088, 9.002626227993765, 3.006430178135064, 13.569756862713088, 10.502626227993765),
        Block.box(-1.493569821864936, 10.958639245069438, 9.072417677058626, 3.006430178135064, 12.458639245069438, 10.947417677058626),
        Block.box(-1.493569821864936, 9.991027914562693, 9.863663950815218, 3.006430178135064, 11.491027914562693, 11.363663950815218),
        Block.box(-2.228758057655119, 9.726169346781578, 10.131430178135064, -0.7287580576551189, 11.226169346781578, 14.631430178135064),
        Block.box(-1.7284477471795316, 7.57568725506273, 10.131430178135064, -0.22844774717953165, 9.07568725506273, 14.631430178135064),
        Block.box(-1.658656298114673, 6.464569637419078, 10.131430178135064, 0.21634370188532692, 7.964569637419078, 14.631430178135064),
        Block.box(-0.8674100243580778, 5.496958306912334, 10.131430178135064, 0.6325899756419222, 6.996958306912334, 14.631430178135064),
        Block.box(-2.158966608590257, 8.615051729137928, 10.131430178135064, -0.6589666085902568, 10.115051729137928, 14.631430178135064),
        Block.box(-1.493569821864936, 8.25, 13.131430178135064, 2.256430178135064, 9.75, 14.631430178135064),
        Block.box(-0.743569821864936, 7.5, 11.631430178135064, 0.756430178135064, 9, 13.881430178135064),
        Block.box(0.006430178135063969, 7.125, 11.631430178135064, 1.506430178135064, 8.625, 13.131430178135064),
        Block.box(-1.493569821864936, 8.25, 10.131430178135064, 0.006430178135063969, 9.75, 13.881430178135064),
        Block.box(1.506430178135064, 8.25, 10.881430178135064, 3.006430178135064, 9.75, 14.631430178135064),
        Block.box(-0.743569821864936, 8.25, 10.131430178135064, 3.006430178135064, 9.75, 11.631430178135064),
        Block.box(0.756430178135064, 7.5, 10.881430178135064, 2.256430178135064, 9, 13.131430178135064),
        Block.box(-0.743569821864936, 7.5, 10.881430178135064, 1.506430178135064, 9, 12.381430178135064),
        Block.box(0.006430178135063969, 7.5, 12.381430178135064, 2.256430178135064, 9, 13.881430178135064),
        Block.box(13.881430178135064, 7.5, 1.881430178135064, 16.131430178135062, 9, 3.381430178135064),
        Block.box(14.631430178135064, 7.125, 2.631430178135064, 16.131430178135062, 8.625, 4.131430178135064),
        Block.box(13.881430178135064, 7.5, 2.631430178135064, 15.381430178135064, 9, 4.881430178135064),
        Block.box(14.631430178135064, 7.5, 3.381430178135064, 16.881430178135062, 9, 4.881430178135064),
        Block.box(15.381430178135064, 7.5, 1.881430178135064, 16.881430178135062, 9, 4.131430178135064),
        Block.box(13.881430178135064, 8.25, 1.131430178135064, 17.631430178135062, 9.75, 2.631430178135064),
        Block.box(13.131430178135064, 8.25, 1.131430178135064, 14.631430178135064, 9.75, 4.881430178135064),
        Block.box(13.131430178135064, 8.25, 4.131430178135064, 16.881430178135062, 9.75, 5.631430178135064),
        Block.box(16.131430178135062, 8.25, 1.881430178135064, 17.631430178135062, 9.75, 5.631430178135064)
        );

    public YeastPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos_, BlockState state) {
        return new ItemStack(ModBlocks.YEAST_PLANT.get().asItem());
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACING) == Direction.NORTH  && state.getValue(AGE) == 0 || state.getValue(FACING) == Direction.SOUTH  && state.getValue(AGE) == 0) {//If the Rotation is North or South and is not Grown
            return NS_PICKED;
        }
        else if(state.getValue(FACING) == Direction.NORTH  && state.getValue(AGE) == 1 || state.getValue(FACING) == Direction.SOUTH  && state.getValue(AGE) == 1)//If the Rotation is North or South and is Grown
        {
            return NS_GROWN;
        }
        else if(state.getValue(FACING) == Direction.EAST  && state.getValue(AGE) == 0 || state.getValue(FACING) == Direction.WEST  && state.getValue(AGE) == 0)//If the Rotation is East or West and is not Grown
        {
            return EW_PICKED;
        }
        else if(state.getValue(FACING) == Direction.EAST  && state.getValue(AGE) == 1 || state.getValue(FACING) == Direction.WEST  && state.getValue(AGE) == 1)//If the Rotation is East or West and is Grown
        {
            return EW_GROWN;
        }
        else//That Should not Happen
        {
            throw new IllegalStateException("!Warning! The Voxelshape can't be Provided because the States from the Block are faulty");
        }

    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 1;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        int i = state.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,rand.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hint) {
        int i = state.getValue(AGE);
        boolean flag = i == 1;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i == 1) {

            popResource(world, pos, new ItemStack(ModItems.YEAST_CONE.get(), 1 + world.random.nextInt(1)));
            world.playSound((Player)null, pos, ModSounds.VEGETATION_PICKED.get(), SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hint);
        }
    }



    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) ||
                state.is(Blocks.COARSE_DIRT) ||
                state.is(Blocks.PODZOL) ||
                state.is(Blocks.STONE) ||
                state.is(Blocks.CLAY) ||
                state.is(Blocks.MOSS_BLOCK) ||
                state.is(Blocks.COBBLED_DEEPSLATE) ||
                state.is(Blocks.COBBLESTONE) ||
                state.is(Blocks.DEEPSLATE) ||
                state.is(Blocks.GRAVEL) ||
                state.is(Blocks.ANDESITE) ||
                state.is(Blocks.GRANITE);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        if (pState.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return pLevel.getBlockState(blockpos).canSustainPlant(pLevel, blockpos, Direction.UP, this);
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    //Bonemeal
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 1;
    }

    public boolean isBonemealSuccess(Level pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, Random pRand, BlockPos pPos, BlockState pState) {

        pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(1)), 2);
    }

    //Builder
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(AGE, FACING);
    }
}

