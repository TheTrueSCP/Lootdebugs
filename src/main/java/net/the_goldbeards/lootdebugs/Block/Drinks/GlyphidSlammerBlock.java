package net.the_goldbeards.lootdebugs.Block.Drinks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.the_goldbeards.lootdebugs.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class GlyphidSlammerBlock extends BushBlock {

    public GlyphidSlammerBlock(Properties properties) {
        super(properties);
    }
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_N = Shapes.or(
            Block.box(2.226120000000001, 4.9, 6.999999999999998, 4.97612, 6, 8.999999999999998),
            Block.box(2.726120000000001, 5.75, 7.499999999999998, 3.726120000000001, 6.75, 8.499999999999998),
            Block.box(2.476120000000001, 5.551115123125783e-17, 7.499999999999998, 3.476120000000001, 1, 8.499999999999998),
            Block.box(2.476120000000001, 3.1500000000000004, 7.499999999999998, 3.976120000000001, 4.9, 8.499999999999998),
            Block.box(1.9761200000000012, 0.7500000000000004, 7.399999999999999, 3.976120000000001, 2.7500000000000004, 8.599999999999998),
            Block.box(1.8761200000000016, 1.5000000000000004, 7.299999999999999, 4.076120000000001, 2.8000000000000007, 8.699999999999998),
            Block.box(2.1261200000000016, 2.3, 7.399999999999999, 4.076120000000001, 3.3, 8.599999999999998),
            Block.box(4.97612, 0, 6.999999999999998, 5.97612, 7, 8.999999999999998),
            Block.box(9.97612, 0, 6.999999999999998, 10.97612, 7, 8.999999999999998),
            Block.box(6.87612, 5, 9.899999999999999, 9.076119999999998, 6.6, 11.499999999999998),
            Block.box(6.87612, 5, 9.899999999999999, 9.076119999999998, 6.6, 11.499999999999998),
            Block.box(4.47612, 5, 6.899999999999999, 6.0761199999999995, 6.6, 9.099999999999998),
            Block.box(6.87612, 5, 9.899999999999999, 9.076119999999998, 6.6, 11.499999999999998),
            Block.box(6.87612, 5, 4.499999999999998, 9.076119999999998, 6.6, 6.099999999999998),
            Block.box(6.87612, 5, 4.499999999999998, 9.076119999999998, 6.6, 6.099999999999998),
            Block.box(9.876120000000002, 5, 6.899999999999999, 11.47612, 6.6, 9.099999999999998),
            Block.box(4.47612, 5, 6.899999999999999, 6.0761199999999995, 6.6, 9.099999999999998),
            Block.box(4.72612, 6.1, 6.799999999999999, 6.0761199999999995, 7.1, 9.199999999999998),
            Block.box(6.776120000000001, 6.1, 9.899999999999999, 9.17612, 7.1, 11.249999999999998),
            Block.box(9.876120000000002, 6.1, 6.799999999999999, 11.22612, 7.1, 9.199999999999998),
            Block.box(6.776120000000001, 6.1, 9.899999999999999, 9.17612, 7.1, 11.249999999999998),
            Block.box(4.72612, 6.1, 6.799999999999999, 6.0761199999999995, 7.1, 9.199999999999998),
            Block.box(4.72612, 6.1, 6.799999999999999, 6.0761199999999995, 7.1, 9.199999999999998),
            Block.box(6.776120000000001, 6.1, 4.749999999999998, 9.17612, 7.1, 6.099999999999998),
            Block.box(6.776120000000001, 6.1, 4.749999999999998, 9.17612, 7.1, 6.099999999999998),
            Block.box(4.72612, -3.608224830031759e-16, 6.799999999999999, 6.0761199999999995, 0.9999999999999997, 9.199999999999998),
            Block.box(4.72612, -3.608224830031759e-16, 6.799999999999999, 6.0761199999999995, 0.9999999999999997, 9.199999999999998),
            Block.box(6.776120000000001, -3.608224830031759e-16, 4.749999999999998, 9.17612, 0.9999999999999997, 6.099999999999998),
            Block.box(6.776120000000001, -3.608224830031759e-16, 4.749999999999998, 9.17612, 0.9999999999999997, 6.099999999999998),
            Block.box(4.72612, -3.608224830031759e-16, 6.799999999999999, 6.0761199999999995, 0.9999999999999997, 9.199999999999998),
            Block.box(6.776120000000001, -3.608224830031759e-16, 9.899999999999999, 9.17612, 0.9999999999999997, 11.249999999999998),
            Block.box(6.776120000000001, -3.608224830031759e-16, 9.899999999999999, 9.17612, 0.9999999999999997, 11.249999999999998),
            Block.box(9.876120000000002, -3.608224830031759e-16, 6.799999999999999, 11.22612, 0.9999999999999997, 9.199999999999998),
            Block.box(6.87612, 0.25, 4.499999999999998, 9.076119999999998, 1.8499999999999996, 5.999999999999998),
            Block.box(6.87612, 0.25, 4.499999999999998, 9.076119999999998, 1.8499999999999996, 5.999999999999998),
            Block.box(9.97612, 0.25, 6.899999999999999, 11.47612, 1.8499999999999996, 9.099999999999998),
            Block.box(4.47612, 0.25, 6.899999999999999, 5.97612, 1.8499999999999996, 9.099999999999998),
            Block.box(6.87612, 0.25, 9.999999999999998, 9.076119999999998, 1.8499999999999996, 11.499999999999998),
            Block.box(6.87612, 0.25, 9.999999999999998, 9.076119999999998, 1.8499999999999996, 11.499999999999998),
            Block.box(7.47612, 3.9999999999999996, 10.399999999999999, 8.47612, 5, 11.399999999999999),
            Block.box(7.47612, 3.9999999999999996, 10.399999999999999, 8.47612, 5, 11.399999999999999),
            Block.box(10.376120000000002, 3.9999999999999996, 7.499999999999998, 11.376120000000002, 5, 8.499999999999998),
            Block.box(10.376120000000002, 3.9999999999999996, 7.499999999999998, 11.376120000000002, 5, 8.499999999999998),
            Block.box(4.5761199999999995, 3.9999999999999996, 7.499999999999998, 5.5761199999999995, 5, 8.499999999999998),
            Block.box(4.5761199999999995, 3.9999999999999996, 7.499999999999998, 5.5761199999999995, 5, 8.499999999999998),
            Block.box(7.47612, 3.9999999999999996, 4.599999999999998, 8.47612, 5, 5.599999999999998),
            Block.box(7.47612, 3.9999999999999996, 4.599999999999998, 8.47612, 5, 5.599999999999998),
            Block.box(7.47612, 1.8499999999999996, 10.399999999999999, 8.47612, 2.8499999999999996, 11.399999999999999),
            Block.box(7.47612, 1.8499999999999996, 10.399999999999999, 8.47612, 2.8499999999999996, 11.399999999999999),
            Block.box(10.376120000000002, 1.8499999999999996, 7.499999999999998, 11.376120000000002, 2.8499999999999996, 8.499999999999998),
            Block.box(10.376120000000002, 1.8499999999999996, 7.499999999999998, 11.376120000000002, 2.8499999999999996, 8.499999999999998),
            Block.box(7.47612, 1.8499999999999996, 10.399999999999999, 8.47612, 2.8499999999999996, 11.399999999999999),
            Block.box(4.5761199999999995, 1.8499999999999996, 7.499999999999998, 5.5761199999999995, 2.8499999999999996, 8.499999999999998),
            Block.box(7.47612, 1.8499999999999996, 4.599999999999998, 8.47612, 2.8499999999999996, 5.599999999999998),
            Block.box(6.87612, 0.25, 9.999999999999998, 9.076119999999998, 1.8499999999999996, 11.499999999999998),
            Block.box(4.47612, 0.25, 6.899999999999999, 5.97612, 1.8499999999999996, 9.099999999999998),
            Block.box(6.97612, 0, 9.999999999999998, 8.97612, 7, 10.999999999999998),
            Block.box(6.97612, 0, 4.999999999999998, 8.97612, 7, 5.999999999999998),
            Block.box(6.97612, 0, 9.999999999999998, 8.97612, 7, 10.999999999999998),
            Block.box(6.97612, 0, 4.999999999999998, 8.97612, 7, 5.999999999999998),
            Block.box(9.97612, 0, 6.999999999999998, 10.97612, 7, 8.999999999999998),
            Block.box(4.97612, 0, 6.999999999999998, 5.97612, 7, 8.999999999999998),
            Block.box(5.97612, 0.1, 5.999999999999998, 9.97612, 1.1, 9.999999999999998),
            Block.box(4.97612, 7, 5.999999999999998, 8.97612, 9, 8.999999999999998),
            Block.box(5.97612, 1, 5.999999999999998, 9.97612, 8, 9.999999999999998),
            Block.box(5.97612, 5.1, 4.999999999999998, 8.97612, 8, 10.999999999999998)
    );

    private static final VoxelShape SHAPE_E = Shapes.or(
            Block.box(7.03806, 4.9, 2.2880599999999998, 9.03806, 6, 5.038059999999998),
            Block.box(7.53806, 5.75, 2.7880599999999998, 8.53806, 6.75, 3.7880599999999998),
            Block.box(7.53806, 5.551115123125783e-17, 2.5380599999999998, 8.53806, 1, 3.5380599999999998),
            Block.box(7.53806, 3.1500000000000004, 2.5380599999999998, 8.53806, 4.9, 4.03806),
            Block.box(7.43806, 0.7500000000000004, 2.0380599999999998, 8.63806, 2.7500000000000004, 4.03806),
            Block.box(7.3380600000000005, 1.5000000000000004, 1.9380600000000001, 8.738059999999999, 2.8000000000000007, 4.1380599999999985),
            Block.box(7.43806, 2.3, 2.18806, 8.63806, 3.3, 4.1380599999999985),
            Block.box(7.03806, 0, 5.038059999999998, 9.03806, 7, 6.038059999999998),
            Block.box(7.03806, 0, 10.038059999999998, 9.03806, 7, 11.038059999999998),
            Block.box(4.53806, 5, 6.938059999999998, 6.138059999999999, 6.6, 9.138059999999996),
            Block.box(4.53806, 5, 6.938059999999998, 6.138059999999999, 6.6, 9.138059999999996),
            Block.box(6.93806, 5, 4.538059999999998, 9.13806, 6.6, 6.138059999999998),
            Block.box(4.53806, 5, 6.938059999999998, 6.138059999999999, 6.6, 9.138059999999996),
            Block.box(9.93806, 5, 6.938059999999998, 11.53806, 6.6, 9.138059999999996),
            Block.box(9.93806, 5, 6.938059999999998, 11.53806, 6.6, 9.138059999999996),
            Block.box(6.93806, 5, 9.93806, 9.13806, 6.6, 11.538059999999998),
            Block.box(6.93806, 5, 4.538059999999998, 9.13806, 6.6, 6.138059999999998),
            Block.box(6.8380600000000005, 6.1, 4.788059999999998, 9.238059999999999, 7.1, 6.138059999999998),
            Block.box(4.78806, 6.1, 6.838059999999999, 6.138059999999999, 7.1, 9.238059999999997),
            Block.box(6.8380600000000005, 6.1, 9.93806, 9.238059999999999, 7.1, 11.288059999999998),
            Block.box(4.78806, 6.1, 6.838059999999999, 6.138059999999999, 7.1, 9.238059999999997),
            Block.box(6.8380600000000005, 6.1, 4.788059999999998, 9.238059999999999, 7.1, 6.138059999999998),
            Block.box(6.8380600000000005, 6.1, 4.788059999999998, 9.238059999999999, 7.1, 6.138059999999998),
            Block.box(9.93806, 6.1, 6.838059999999999, 11.28806, 7.1, 9.238059999999997),
            Block.box(9.93806, 6.1, 6.838059999999999, 11.28806, 7.1, 9.238059999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 4.788059999999998, 9.238059999999999, 0.9999999999999997, 6.138059999999998),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 4.788059999999998, 9.238059999999999, 0.9999999999999997, 6.138059999999998),
            Block.box(9.93806, -3.608224830031759e-16, 6.838059999999999, 11.28806, 0.9999999999999997, 9.238059999999997),
            Block.box(9.93806, -3.608224830031759e-16, 6.838059999999999, 11.28806, 0.9999999999999997, 9.238059999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 4.788059999999998, 9.238059999999999, 0.9999999999999997, 6.138059999999998),
            Block.box(4.78806, -3.608224830031759e-16, 6.838059999999999, 6.138059999999999, 0.9999999999999997, 9.238059999999997),
            Block.box(4.78806, -3.608224830031759e-16, 6.838059999999999, 6.138059999999999, 0.9999999999999997, 9.238059999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 9.93806, 9.238059999999999, 0.9999999999999997, 11.288059999999998),
            Block.box(10.03806, 0.25, 6.938059999999998, 11.53806, 1.8499999999999996, 9.138059999999996),
            Block.box(10.03806, 0.25, 6.938059999999998, 11.53806, 1.8499999999999996, 9.138059999999996),
            Block.box(6.93806, 0.25, 10.038059999999998, 9.13806, 1.8499999999999996, 11.538059999999998),
            Block.box(6.93806, 0.25, 4.538059999999998, 9.13806, 1.8499999999999996, 6.038059999999998),
            Block.box(4.53806, 0.25, 6.938059999999998, 6.03806, 1.8499999999999996, 9.138059999999996),
            Block.box(4.53806, 0.25, 6.938059999999998, 6.03806, 1.8499999999999996, 9.138059999999996),
            Block.box(4.638059999999999, 3.9999999999999996, 7.538059999999998, 5.638059999999999, 5, 8.538059999999998),
            Block.box(4.638059999999999, 3.9999999999999996, 7.538059999999998, 5.638059999999999, 5, 8.538059999999998),
            Block.box(7.53806, 3.9999999999999996, 10.43806, 8.53806, 5, 11.43806),
            Block.box(7.53806, 3.9999999999999996, 10.43806, 8.53806, 5, 11.43806),
            Block.box(7.53806, 3.9999999999999996, 4.638059999999998, 8.53806, 5, 5.638059999999998),
            Block.box(7.53806, 3.9999999999999996, 4.638059999999998, 8.53806, 5, 5.638059999999998),
            Block.box(10.43806, 3.9999999999999996, 7.538059999999998, 11.43806, 5, 8.538059999999998),
            Block.box(10.43806, 3.9999999999999996, 7.538059999999998, 11.43806, 5, 8.538059999999998),
            Block.box(4.638059999999999, 1.8499999999999996, 7.538059999999998, 5.638059999999999, 2.8499999999999996, 8.538059999999998),
            Block.box(4.638059999999999, 1.8499999999999996, 7.538059999999998, 5.638059999999999, 2.8499999999999996, 8.538059999999998),
            Block.box(7.53806, 1.8499999999999996, 10.43806, 8.53806, 2.8499999999999996, 11.43806),
            Block.box(7.53806, 1.8499999999999996, 10.43806, 8.53806, 2.8499999999999996, 11.43806),
            Block.box(4.638059999999999, 1.8499999999999996, 7.538059999999998, 5.638059999999999, 2.8499999999999996, 8.538059999999998),
            Block.box(7.53806, 1.8499999999999996, 4.638059999999998, 8.53806, 2.8499999999999996, 5.638059999999998),
            Block.box(10.43806, 1.8499999999999996, 7.538059999999998, 11.43806, 2.8499999999999996, 8.538059999999998),
            Block.box(4.53806, 0.25, 6.938059999999998, 6.03806, 1.8499999999999996, 9.138059999999996),
            Block.box(6.93806, 0.25, 4.538059999999998, 9.13806, 1.8499999999999996, 6.038059999999998),
            Block.box(5.03806, 0, 7.038059999999998, 6.03806, 7, 9.038059999999998),
            Block.box(10.03806, 0, 7.038059999999998, 11.03806, 7, 9.038059999999998),
            Block.box(5.03806, 0, 7.038059999999998, 6.03806, 7, 9.038059999999998),
            Block.box(10.03806, 0, 7.038059999999998, 11.03806, 7, 9.038059999999998),
            Block.box(7.03806, 0, 10.038059999999998, 9.03806, 7, 11.038059999999998),
            Block.box(7.03806, 0, 5.038059999999998, 9.03806, 7, 6.038059999999998),
            Block.box(6.03806, 0.1, 6.038059999999998, 10.03806, 1.1, 10.038059999999998),
            Block.box(7.03806, 7, 5.038059999999998, 10.03806, 9, 9.038059999999998),
            Block.box(6.03806, 1, 6.038059999999998, 10.03806, 8, 10.038059999999998),
            Block.box(5.03806, 5.1, 6.038059999999998, 11.03806, 8, 9.038059999999998)
    );

    private static final VoxelShape SHAPE_S = Shapes.or(
            Block.box(11, 4.9, 7, 13.75, 6, 9),
            Block.box(12.25, 5.75, 7.5, 13.25, 6.75, 8.5),
            Block.box(12.5, 5.551115123125783e-17, 7.5, 13.5, 1, 8.5),
            Block.box(12, 3.1500000000000004, 7.5, 13.5, 4.9, 8.5),
            Block.box(12, 0.7500000000000004, 7.4, 14, 2.7500000000000004, 8.6),
            Block.box(11.9, 1.5000000000000004, 7.300000000000001, 14.1, 2.8000000000000007, 8.7),
            Block.box(11.9, 2.3, 7.4, 13.85, 3.3, 8.6),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(6.9, 5, 4.5, 9.1, 6.6, 6.1),
            Block.box(6.9, 5, 4.5, 9.1, 6.6, 6.1),
            Block.box(9.9, 5, 6.9, 11.5, 6.6, 9.1),
            Block.box(6.9, 5, 4.5, 9.1, 6.6, 6.1),
            Block.box(6.9, 5, 9.9, 9.1, 6.6, 11.5),
            Block.box(6.9, 5, 9.9, 9.1, 6.6, 11.5),
            Block.box(4.5, 5, 6.9, 6.1, 6.6, 9.1),
            Block.box(9.9, 5, 6.9, 11.5, 6.6, 9.1),
            Block.box(9.9, 6.1, 6.800000000000001, 11.25, 7.1, 9.2),
            Block.box(6.800000000000001, 6.1, 4.75, 9.2, 7.1, 6.1),
            Block.box(4.75, 6.1, 6.800000000000001, 6.1, 7.1, 9.2),
            Block.box(6.800000000000001, 6.1, 4.75, 9.2, 7.1, 6.1),
            Block.box(9.9, 6.1, 6.800000000000001, 11.25, 7.1, 9.2),
            Block.box(9.9, 6.1, 6.800000000000001, 11.25, 7.1, 9.2),
            Block.box(6.800000000000001, 6.1, 9.9, 9.2, 7.1, 11.25),
            Block.box(6.800000000000001, 6.1, 9.9, 9.2, 7.1, 11.25),
            Block.box(9.9, -3.608224830031759e-16, 6.800000000000001, 11.25, 0.9999999999999997, 9.2),
            Block.box(9.9, -3.608224830031759e-16, 6.800000000000001, 11.25, 0.9999999999999997, 9.2),
            Block.box(6.800000000000001, -3.608224830031759e-16, 9.9, 9.2, 0.9999999999999997, 11.25),
            Block.box(6.800000000000001, -3.608224830031759e-16, 9.9, 9.2, 0.9999999999999997, 11.25),
            Block.box(9.9, -3.608224830031759e-16, 6.800000000000001, 11.25, 0.9999999999999997, 9.2),
            Block.box(6.800000000000001, -3.608224830031759e-16, 4.75, 9.2, 0.9999999999999997, 6.1),
            Block.box(6.800000000000001, -3.608224830031759e-16, 4.75, 9.2, 0.9999999999999997, 6.1),
            Block.box(4.75, -3.608224830031759e-16, 6.800000000000001, 6.1, 0.9999999999999997, 9.2),
            Block.box(6.9, 0.25, 10, 9.1, 1.8499999999999996, 11.5),
            Block.box(6.9, 0.25, 10, 9.1, 1.8499999999999996, 11.5),
            Block.box(4.5, 0.25, 6.9, 6, 1.8499999999999996, 9.1),
            Block.box(10, 0.25, 6.9, 11.5, 1.8499999999999996, 9.1),
            Block.box(6.9, 0.25, 4.5, 9.1, 1.8499999999999996, 6),
            Block.box(6.9, 0.25, 4.5, 9.1, 1.8499999999999996, 6),
            Block.box(7.5, 3.9999999999999996, 4.6, 8.5, 5, 5.6),
            Block.box(7.5, 3.9999999999999996, 4.6, 8.5, 5, 5.6),
            Block.box(4.6, 3.9999999999999996, 7.5, 5.6, 5, 8.5),
            Block.box(4.6, 3.9999999999999996, 7.5, 5.6, 5, 8.5),
            Block.box(10.4, 3.9999999999999996, 7.5, 11.4, 5, 8.5),
            Block.box(10.4, 3.9999999999999996, 7.5, 11.4, 5, 8.5),
            Block.box(7.5, 3.9999999999999996, 10.4, 8.5, 5, 11.4),
            Block.box(7.5, 3.9999999999999996, 10.4, 8.5, 5, 11.4),
            Block.box(7.5, 1.8499999999999996, 4.6, 8.5, 2.8499999999999996, 5.6),
            Block.box(7.5, 1.8499999999999996, 4.6, 8.5, 2.8499999999999996, 5.6),
            Block.box(4.6, 1.8499999999999996, 7.5, 5.6, 2.8499999999999996, 8.5),
            Block.box(4.6, 1.8499999999999996, 7.5, 5.6, 2.8499999999999996, 8.5),
            Block.box(7.5, 1.8499999999999996, 4.6, 8.5, 2.8499999999999996, 5.6),
            Block.box(10.4, 1.8499999999999996, 7.5, 11.4, 2.8499999999999996, 8.5),
            Block.box(7.5, 1.8499999999999996, 10.4, 8.5, 2.8499999999999996, 11.4),
            Block.box(6.9, 0.25, 4.5, 9.1, 1.8499999999999996, 6),
            Block.box(10, 0.25, 6.9, 11.5, 1.8499999999999996, 9.1),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(7, 0, 5, 9, 7, 6),
            Block.box(7, 0, 10, 9, 7, 11),
            Block.box(5, 0, 7, 6, 7, 9),
            Block.box(10, 0, 7, 11, 7, 9),
            Block.box(6, 0.1, 6, 10, 1.1, 10),
            Block.box(7, 7, 7, 11, 9, 10),
            Block.box(6, 1, 6, 10, 8, 10),
            Block.box(7, 5.1, 5, 10, 8, 11)
    );


    private static final VoxelShape SHAPE_W = Shapes.or(
            Block.box(7.03806, 4.9, 11.061939999999998, 9.03806, 6, 13.811939999999996),
            Block.box(7.53806, 5.75, 12.311939999999996, 8.53806, 6.75, 13.311939999999996),
            Block.box(7.53806, 5.551115123125783e-17, 12.561939999999996, 8.53806, 1, 13.561939999999996),
            Block.box(7.53806, 3.1500000000000004, 12.061939999999996, 8.53806, 4.9, 13.561939999999996),
            Block.box(7.43806, 0.7500000000000004, 12.061939999999996, 8.63806, 2.7500000000000004, 14.061939999999996),
            Block.box(7.3380600000000005, 1.5000000000000004, 11.961939999999997, 8.738059999999999, 2.8000000000000007, 14.161939999999996),
            Block.box(7.43806, 2.3, 11.961939999999997, 8.63806, 3.3, 13.911939999999996),
            Block.box(7.03806, 0, 10.061939999999998, 9.03806, 7, 11.061939999999998),
            Block.box(7.03806, 0, 5.061939999999998, 9.03806, 7, 6.061939999999998),
            Block.box(9.93806, 5, 6.96194, 11.53806, 6.6, 9.161939999999998),
            Block.box(9.93806, 5, 6.96194, 11.53806, 6.6, 9.161939999999998),
            Block.box(6.93806, 5, 9.961939999999998, 9.13806, 6.6, 11.561939999999998),
            Block.box(9.93806, 5, 6.96194, 11.53806, 6.6, 9.161939999999998),
            Block.box(4.53806, 5, 6.96194, 6.138059999999999, 6.6, 9.161939999999998),
            Block.box(4.53806, 5, 6.96194, 6.138059999999999, 6.6, 9.161939999999998),
            Block.box(6.93806, 5, 4.561939999999998, 9.13806, 6.6, 6.161939999999996),
            Block.box(6.93806, 5, 9.961939999999998, 9.13806, 6.6, 11.561939999999998),
            Block.box(6.8380600000000005, 6.1, 9.961939999999998, 9.238059999999999, 7.1, 11.311939999999998),
            Block.box(9.93806, 6.1, 6.861939999999999, 11.28806, 7.1, 9.261939999999997),
            Block.box(6.8380600000000005, 6.1, 4.811939999999998, 9.238059999999999, 7.1, 6.161939999999996),
            Block.box(9.93806, 6.1, 6.861939999999999, 11.28806, 7.1, 9.261939999999997),
            Block.box(6.8380600000000005, 6.1, 9.961939999999998, 9.238059999999999, 7.1, 11.311939999999998),
            Block.box(6.8380600000000005, 6.1, 9.961939999999998, 9.238059999999999, 7.1, 11.311939999999998),
            Block.box(4.78806, 6.1, 6.861939999999999, 6.138059999999999, 7.1, 9.261939999999997),
            Block.box(4.78806, 6.1, 6.861939999999999, 6.138059999999999, 7.1, 9.261939999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 9.961939999999998, 9.238059999999999, 0.9999999999999997, 11.311939999999998),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 9.961939999999998, 9.238059999999999, 0.9999999999999997, 11.311939999999998),
            Block.box(4.78806, -3.608224830031759e-16, 6.861939999999999, 6.138059999999999, 0.9999999999999997, 9.261939999999997),
            Block.box(4.78806, -3.608224830031759e-16, 6.861939999999999, 6.138059999999999, 0.9999999999999997, 9.261939999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 9.961939999999998, 9.238059999999999, 0.9999999999999997, 11.311939999999998),
            Block.box(9.93806, -3.608224830031759e-16, 6.861939999999999, 11.28806, 0.9999999999999997, 9.261939999999997),
            Block.box(9.93806, -3.608224830031759e-16, 6.861939999999999, 11.28806, 0.9999999999999997, 9.261939999999997),
            Block.box(6.8380600000000005, -3.608224830031759e-16, 4.811939999999998, 9.238059999999999, 0.9999999999999997, 6.161939999999996),
            Block.box(4.53806, 0.25, 6.96194, 6.03806, 1.8499999999999996, 9.161939999999998),
            Block.box(4.53806, 0.25, 6.96194, 6.03806, 1.8499999999999996, 9.161939999999998),
            Block.box(6.93806, 0.25, 4.561939999999998, 9.13806, 1.8499999999999996, 6.061939999999998),
            Block.box(6.93806, 0.25, 10.061939999999998, 9.13806, 1.8499999999999996, 11.561939999999998),
            Block.box(10.03806, 0.25, 6.96194, 11.53806, 1.8499999999999996, 9.161939999999998),
            Block.box(10.03806, 0.25, 6.96194, 11.53806, 1.8499999999999996, 9.161939999999998),
            Block.box(10.43806, 3.9999999999999996, 7.561939999999998, 11.43806, 5, 8.561939999999998),
            Block.box(10.43806, 3.9999999999999996, 7.561939999999998, 11.43806, 5, 8.561939999999998),
            Block.box(7.53806, 3.9999999999999996, 4.661939999999996, 8.53806, 5, 5.661939999999996),
            Block.box(7.53806, 3.9999999999999996, 4.661939999999996, 8.53806, 5, 5.661939999999996),
            Block.box(7.53806, 3.9999999999999996, 10.461939999999998, 8.53806, 5, 11.461939999999998),
            Block.box(7.53806, 3.9999999999999996, 10.461939999999998, 8.53806, 5, 11.461939999999998),
            Block.box(4.638059999999999, 3.9999999999999996, 7.561939999999998, 5.638059999999999, 5, 8.561939999999998),
            Block.box(4.638059999999999, 3.9999999999999996, 7.561939999999998, 5.638059999999999, 5, 8.561939999999998),
            Block.box(10.43806, 1.8499999999999996, 7.561939999999998, 11.43806, 2.8499999999999996, 8.561939999999998),
            Block.box(10.43806, 1.8499999999999996, 7.561939999999998, 11.43806, 2.8499999999999996, 8.561939999999998),
            Block.box(7.53806, 1.8499999999999996, 4.661939999999996, 8.53806, 2.8499999999999996, 5.661939999999996),
            Block.box(7.53806, 1.8499999999999996, 4.661939999999996, 8.53806, 2.8499999999999996, 5.661939999999996),
            Block.box(10.43806, 1.8499999999999996, 7.561939999999998, 11.43806, 2.8499999999999996, 8.561939999999998),
            Block.box(7.53806, 1.8499999999999996, 10.461939999999998, 8.53806, 2.8499999999999996, 11.461939999999998),
            Block.box(4.638059999999999, 1.8499999999999996, 7.561939999999998, 5.638059999999999, 2.8499999999999996, 8.561939999999998),
            Block.box(10.03806, 0.25, 6.96194, 11.53806, 1.8499999999999996, 9.161939999999998),
            Block.box(6.93806, 0.25, 10.061939999999998, 9.13806, 1.8499999999999996, 11.561939999999998),
            Block.box(10.03806, 0, 7.061939999999998, 11.03806, 7, 9.061939999999998),
            Block.box(5.03806, 0, 7.061939999999998, 6.03806, 7, 9.061939999999998),
            Block.box(10.03806, 0, 7.061939999999998, 11.03806, 7, 9.061939999999998),
            Block.box(5.03806, 0, 7.061939999999998, 6.03806, 7, 9.061939999999998),
            Block.box(7.03806, 0, 5.061939999999998, 9.03806, 7, 6.061939999999998),
            Block.box(7.03806, 0, 10.061939999999998, 9.03806, 7, 11.061939999999998),
            Block.box(6.03806, 0.1, 6.061939999999998, 10.03806, 1.1, 10.061939999999998),
            Block.box(6.03806, 7, 7.061939999999998, 9.03806, 9, 11.061939999999998),
            Block.box(6.03806, 1, 6.061939999999998, 10.03806, 8, 10.061939999999998),
            Block.box(5.03806, 5.1, 7.061939999999998, 11.03806, 8, 10.061939999999998)
    );

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return SHAPE_N;

            case EAST:
                return SHAPE_E;

            case WEST:
                return SHAPE_W;

            case SOUTH:
                return SHAPE_S;

            default:
                return SHAPE_N;
        }
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

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return !pState.is(ModTags.Blocks.BEER_CAN_PLACE_ON);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
