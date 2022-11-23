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

public class SeasonedMoonriderBlock extends BushBlock{

    public SeasonedMoonriderBlock(Properties properties) {
        super(properties);
    }


    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_N = Shapes.or(
            Block.box(1.7839796410747004, 1.25, 7.386904761904763, 3.0339796410747004, 7.75, 8.636904761904763),
            Block.box(1.5339796410747004, 5.25, 7.136904761904763, 3.2839796410747004, 6.25, 8.886904761904763),
            Block.box(1.5339796410747004, 6.5, 7.136904761904763, 3.2839796410747004, 7.5, 8.886904761904763),
            Block.box(1.5339796410747004, 4.1, 7.136904761904763, 3.2839796410747004, 5.1, 8.886904761904763),
            Block.box(1.5339796410747004, 1.8, 7.136904761904763, 3.2839796410747004, 2.8, 8.886904761904763),
            Block.box(1.5339796410747004, 2.95, 7.136904761904763, 3.2839796410747004, 3.95, 8.886904761904763),
            Block.box(4.857476475498014, 0, 6.5738836638629845, 5.857476475498014, 6.95, 8.573883663862985),
            Block.box(1.8642058461286277, 4.422288122281052, 7.261904761904763, 4.864205846128627, 5.422288122281052, 8.761904761904763),
            Block.box(9.85747647549802, 0, 6.5738836638629845, 10.85747647549802, 6.95, 8.573883663862985),
            Block.box(6.857476475498016, 0, 9.573883663862985, 8.85747647549802, 6.95, 10.573883663862985),
            Block.box(6.857476475498016, 0, 4.5738836638629845, 8.85747647549802, 6.95, 5.5738836638629845),
            Block.box(7.0339796410746995, 0, 10.011904761904763, 9.033979641074703, 6.95, 11.011904761904763),
            Block.box(7.0339796410746995, 0, 5.011904761904763, 9.033979641074703, 6.95, 6.011904761904763),
            Block.box(10.033979641074703, 0, 7.011904761904763, 11.033979641074703, 6.95, 9.011904761904763),
            Block.box(5.0339796410746995, 0, 7.011904761904763, 6.0339796410746995, 6.95, 9.011904761904763),
            Block.box(6.0339796410746995, 0.10000000000000009, 6.011904761904763, 10.033979641074703, 1.1, 10.011904761904763),
            Block.box(6.7839796410746995, 1, 4.761904761904763, 9.283979641074703, 6, 5.761904761904763),
            Block.box(6.6839796410747, 5.5, 4.511904761904763, 9.383979641074703, 6.5, 5.511904761904763),
            Block.box(6.507476475498015, 5.5, 4.0738836638629845, 9.207476475498018, 6.5, 5.0738836638629845),
            Block.box(6.4990585376267465, 5.5, 10.946439035913048, 9.19905853762675, 6.5, 11.946439035913048),
            Block.box(10.533979641074703, 5.5, 6.661904761904763, 11.533979641074703, 6.5, 9.361904761904762),
            Block.box(6.4990585376267465, 5.5, 4.946439035913047, 9.19905853762675, 6.5, 5.946439035913047),
            Block.box(4.5339796410746995, 5.5, 6.661904761904763, 5.5339796410746995, 6.5, 9.361904761904762),
            Block.box(4.349058537626746, 5.5, 7.096439035913047, 5.349058537626746, 6.5, 9.796439035913046),
            Block.box(6.6839796410747, 5.5, 10.511904761904763, 9.383979641074703, 6.5, 11.511904761904763),
            Block.box(6.4990585376267465, 0.5, 4.946439035913047, 9.19905853762675, 1.5, 5.946439035913047),
            Block.box(6.6839796410747, 0.5, 4.511904761904763, 9.383979641074703, 1.5, 5.511904761904763),
            Block.box(7.533979641074701, 0, 4.011904761904763, 8.533979641074703, 1.5, 5.011904761904763),
            Block.box(11.033979641074703, 0, 7.511904761904763, 12.033979641074703, 1.5, 8.511904761904763),
            Block.box(7.533979641074701, 0, 11.011904761904763, 8.533979641074703, 1.5, 12.011904761904763),
            Block.box(4.0339796410747, 0, 7.511904761904763, 5.0339796410746995, 1.5, 8.511904761904763),
            Block.box(6.507476475498015, 0.5, 4.0738836638629845, 9.207476475498018, 1.5, 5.0738836638629845),
            Block.box(10.533979641074703, 0.5, 6.661904761904763, 11.533979641074703, 1.5, 9.361904761904762),
            Block.box(4.5339796410746995, 0.5, 6.661904761904763, 5.5339796410746995, 1.5, 9.361904761904762),
            Block.box(4.349058537626746, 0.5, 7.096439035913047, 5.349058537626746, 1.5, 9.796439035913046),
            Block.box(6.6839796410747, 0.5, 10.511904761904763, 9.383979641074703, 1.5, 11.511904761904763),
            Block.box(6.4990585376267465, 0.5, 10.946439035913048, 9.19905853762675, 1.5, 11.946439035913048),
            Block.box(6.7839796410746995, 1, 10.261904761904763, 9.283979641074703, 6, 11.261904761904763),
            Block.box(4.7839796410746995, 1, 6.761904761904763, 5.7839796410746995, 6, 9.261904761904763),
            Block.box(10.283979641074703, 1, 6.636904761904763, 11.283979641074703, 6, 9.386904761904763),
            Block.box(6.607476475498016, 1, 4.3238836638629845, 9.10747647549802, 6, 5.3238836638629845),
            Block.box(6.607476475498016, 1, 9.823883663862985, 9.10747647549802, 6, 10.823883663862985),
            Block.box(4.607476475498014, 1, 6.3238836638629845, 5.607476475498014, 6, 8.823883663862985),
            Block.box(6.6589796410746995, 2.1, 4.511904761904763, 9.408979641074703, 3.5, 5.511904761904763),
            Block.box(6.6589796410746995, 3.6, 4.511904761904763, 9.408979641074703, 5, 5.511904761904763),
            Block.box(6.474058537626746, 2.1, 4.946439035913047, 9.22405853762675, 3.5, 5.946439035913047),
            Block.box(6.474058537626746, 3.6, 4.946439035913047, 9.22405853762675, 5, 5.946439035913047),
            Block.box(4.5339796410746995, 2.1, 6.636904761904763, 5.5339796410746995, 3.5, 9.386904761904763),
            Block.box(4.5339796410746995, 3.6, 6.636904761904763, 5.5339796410746995, 5, 9.386904761904763),
            Block.box(6.6589796410746995, 2.1, 10.511904761904763, 9.408979641074703, 3.5, 11.511904761904763),
            Block.box(6.6589796410746995, 3.6, 10.511904761904763, 9.408979641074703, 5, 11.511904761904763),
            Block.box(4.349058537626746, 2.1, 7.071439035913048, 5.349058537626746, 3.5, 9.821439035913048),
            Block.box(4.349058537626746, 3.6, 7.071439035913048, 5.349058537626746, 5, 9.821439035913048),
            Block.box(10.533979641074703, 2.1, 6.636904761904763, 11.533979641074703, 3.5, 9.386904761904763),
            Block.box(10.533979641074703, 3.6, 6.636904761904763, 11.533979641074703, 5, 9.386904761904763),
            Block.box(10.34905853762675, 3.6, 7.071439035913048, 11.34905853762675, 5, 9.821439035913048),
            Block.box(10.34905853762675, 2.1, 7.071439035913048, 11.34905853762675, 3.5, 9.821439035913048),
            Block.box(6.474058537626746, 2.1, 10.946439035913048, 9.22405853762675, 3.5, 11.946439035913048),
            Block.box(6.474058537626746, 3.6, 10.946439035913048, 9.22405853762675, 5, 11.946439035913048),
            Block.box(10.10747647549802, 1, 6.3238836638629845, 11.10747647549802, 6, 8.823883663862985),
            Block.box(5.0339796410746995, 7, 6.011904761904763, 9.033979641074703, 9, 9.011904761904763),
            Block.box(6.383979641074699, 5, 5.261904761904763, 9.133979641074703, 8, 10.911904761904763),
            Block.box(6.0339796410746995, 1, 6.111904761904762, 10.033979641074703, 8, 10.011904761904763)
    );

    private static final VoxelShape SHAPE_S =Shapes.or(
            Block.box(13, 1.25, 7.375, 14.25, 7.75, 8.625),
            Block.box(12.75, 5.25, 7.125, 14.5, 6.25, 8.875),
            Block.box(12.75, 6.5, 7.125, 14.5, 7.5, 8.875),
            Block.box(12.75, 4.1, 7.125, 14.5, 5.1, 8.875),
            Block.box(12.75, 1.8, 7.125, 14.5, 2.8, 8.875),
            Block.box(12.75, 2.95, 7.125, 14.5, 3.95, 8.875),
            Block.box(10.176503165576685, 0, 7.438021098041778, 11.176503165576685, 6.95, 9.438021098041778),
            Block.box(11.169773794946073, 4.422288122281052, 7.25, 14.169773794946074, 5.422288122281052, 8.75),
            Block.box(5.176503165576684, 0, 7.438021098041778, 6.176503165576683, 6.95, 9.438021098041778),
            Block.box(7.176503165576683, 0, 5.438021098041778, 9.176503165576683, 6.95, 6.438021098041778),
            Block.box(7.176503165576683, 0, 10.438021098041778, 9.176503165576683, 6.95, 11.438021098041778),
            Block.box(7, 0, 5, 9, 6.95, 6),
            Block.box(7, 0, 10, 9, 6.95, 11),
            Block.box(5, 0, 7, 6, 6.95, 9),
            Block.box(10, 0, 7, 11, 6.95, 9),
            Block.box(6, 0.10000000000000009, 6, 10, 1.1, 10),
            Block.box(6.75, 1, 10.25, 9.25, 6, 11.25),
            Block.box(6.65, 5.5, 10.5, 9.35, 6.5, 11.5),
            Block.box(6.8265031655766855, 5.5, 10.938021098041778, 9.526503165576685, 6.5, 11.938021098041778),
            Block.box(6.834921103447954, 5.5, 4.065465725991715, 9.534921103447953, 6.5, 5.065465725991715),
            Block.box(4.5, 5.5, 6.65, 5.5, 6.5, 9.35),
            Block.box(6.834921103447954, 5.5, 10.065465725991716, 9.534921103447953, 6.5, 11.065465725991716),
            Block.box(10.5, 5.5, 6.65, 11.5, 6.5, 9.35),
            Block.box(10.684921103447953, 5.5, 6.2154657259917165, 11.684921103447953, 6.5, 8.915465725991716),
            Block.box(6.65, 5.5, 4.5, 9.35, 6.5, 5.5),
            Block.box(6.834921103447954, 0.5, 10.065465725991716, 9.534921103447953, 1.5, 11.065465725991716),
            Block.box(6.65, 0.5, 10.5, 9.35, 1.5, 11.5),
            Block.box(7.5, 0, 11, 8.5, 1.5, 12),
            Block.box(4, 0, 7.5, 5, 1.5, 8.5),
            Block.box(7.5, 0, 4, 8.5, 1.5, 5),
            Block.box(11, 0, 7.5, 12, 1.5, 8.5),
            Block.box(6.8265031655766855, 0.5, 10.938021098041778, 9.526503165576685, 1.5, 11.938021098041778),
            Block.box(4.5, 0.5, 6.65, 5.5, 1.5, 9.35),
            Block.box(10.5, 0.5, 6.65, 11.5, 1.5, 9.35),
            Block.box(10.684921103447953, 0.5, 6.2154657259917165, 11.684921103447953, 1.5, 8.915465725991716),
            Block.box(6.65, 0.5, 4.5, 9.35, 1.5, 5.5),
            Block.box(6.834921103447954, 0.5, 4.065465725991715, 9.534921103447953, 1.5, 5.065465725991715),
            Block.box(6.75, 1, 4.75, 9.25, 6, 5.75),
            Block.box(10.25, 1, 6.75, 11.25, 6, 9.25),
            Block.box(4.75, 1, 6.625, 5.75, 6, 9.375),
            Block.box(6.926503165576683, 1, 10.688021098041778, 9.426503165576683, 6, 11.688021098041778),
            Block.box(6.926503165576683, 1, 5.188021098041778, 9.426503165576683, 6, 6.188021098041778),
            Block.box(10.426503165576685, 1, 7.188021098041778, 11.426503165576685, 6, 9.688021098041778),
            Block.box(6.625, 2.1, 10.5, 9.375, 3.5, 11.5),
            Block.box(6.625, 3.6, 10.5, 9.375, 5, 11.5),
            Block.box(6.809921103447953, 2.1, 10.065465725991716, 9.559921103447953, 3.5, 11.065465725991716),
            Block.box(6.809921103447953, 3.6, 10.065465725991716, 9.559921103447953, 5, 11.065465725991716),
            Block.box(10.5, 2.1, 6.625, 11.5, 3.5, 9.375),
            Block.box(10.5, 3.6, 6.625, 11.5, 5, 9.375),
            Block.box(6.625, 2.1, 4.5, 9.375, 3.5, 5.5),
            Block.box(6.625, 3.6, 4.5, 9.375, 5, 5.5),
            Block.box(10.684921103447953, 2.1, 6.190465725991714, 11.684921103447953, 3.5, 8.940465725991714),
            Block.box(10.684921103447953, 3.6, 6.190465725991714, 11.684921103447953, 5, 8.940465725991714),
            Block.box(4.5, 2.1, 6.625, 5.5, 3.5, 9.375),
            Block.box(4.5, 3.6, 6.625, 5.5, 5, 9.375),
            Block.box(4.684921103447953, 3.6, 6.190465725991714, 5.684921103447953, 5, 8.940465725991714),
            Block.box(4.684921103447953, 2.1, 6.190465725991714, 5.684921103447953, 3.5, 8.940465725991714),
            Block.box(6.809921103447953, 2.1, 4.065465725991715, 9.559921103447953, 3.5, 5.065465725991715),
            Block.box(6.809921103447953, 3.6, 4.065465725991715, 9.559921103447953, 5, 5.065465725991715),
            Block.box(4.926503165576684, 1, 7.188021098041778, 5.926503165576684, 6, 9.688021098041778),
            Block.box(7, 7, 7, 11, 9, 10),
            Block.box(6.9, 5, 5.1, 9.65, 8, 10.75),
            Block.box(6, 1, 6, 10, 8, 9.9)
    );

    private static final VoxelShape SHAPE_W = Shapes.or(
            Block.box(7.397942201489734, 1.25, 12.988962560415029, 8.647942201489734, 7.75, 14.238962560415029),
            Block.box(7.147942201489734, 5.25, 12.738962560415029, 8.897942201489734, 6.25, 14.488962560415029),
            Block.box(7.147942201489734, 6.5, 12.738962560415029, 8.897942201489734, 7.5, 14.488962560415029),
            Block.box(7.147942201489734, 4.1, 12.738962560415029, 8.897942201489734, 5.1, 14.488962560415029),
            Block.box(7.147942201489734, 1.8, 12.738962560415029, 8.897942201489734, 2.8, 14.488962560415029),
            Block.box(7.147942201489734, 2.95, 12.738962560415029, 8.897942201489734, 3.95, 14.488962560415029),
            Block.box(6.584921103447956, 0, 10.165465725991714, 8.584921103447956, 6.95, 11.165465725991714),
            Block.box(7.272942201489734, 4.422288122281052, 11.158736355361102, 8.772942201489734, 5.422288122281052, 14.158736355361102),
            Block.box(6.584921103447956, 0, 5.165465725991714, 8.584921103447956, 6.95, 6.165465725991714),
            Block.box(9.584921103447956, 0, 7.165465725991712, 10.584921103447956, 6.95, 9.165465725991712),
            Block.box(4.584921103447956, 0, 7.165465725991712, 5.584921103447956, 6.95, 9.165465725991712),
            Block.box(10.022942201489734, 0, 6.988962560415029, 11.022942201489734, 6.95, 8.988962560415029),
            Block.box(5.022942201489734, 0, 6.988962560415029, 6.022942201489734, 6.95, 8.988962560415029),
            Block.box(7.022942201489734, 0, 4.988962560415029, 9.022942201489734, 6.95, 5.988962560415029),
            Block.box(7.022942201489734, 0, 9.988962560415029, 9.022942201489734, 6.95, 10.988962560415029),
            Block.box(6.022942201489734, 0.10000000000000009, 5.988962560415029, 10.022942201489734, 1.1, 9.988962560415029),
            Block.box(4.772942201489734, 1, 6.738962560415029, 5.772942201489734, 6, 9.238962560415029),
            Block.box(4.522942201489734, 5.5, 6.638962560415029, 5.522942201489734, 6.5, 9.338962560415029),
            Block.box(4.084921103447956, 5.5, 6.815465725991714, 5.084921103447956, 6.5, 9.515465725991714),
            Block.box(10.95747647549802, 5.5, 6.823883663862983, 11.95747647549802, 6.5, 9.523883663862982),
            Block.box(6.672942201489734, 5.5, 4.488962560415031, 9.372942201489733, 6.5, 5.488962560415029),
            Block.box(4.957476475498018, 5.5, 6.823883663862983, 5.957476475498018, 6.5, 9.523883663862982),
            Block.box(6.672942201489734, 5.5, 10.488962560415029, 9.372942201489733, 6.5, 11.488962560415029),
            Block.box(7.107476475498018, 5.5, 10.673883663862982, 9.807476475498017, 6.5, 11.673883663862982),
            Block.box(10.522942201489734, 5.5, 6.638962560415029, 11.522942201489734, 6.5, 9.338962560415029),
            Block.box(4.957476475498018, 0.5, 6.823883663862983, 5.957476475498018, 1.5, 9.523883663862982),
            Block.box(4.522942201489734, 0.5, 6.638962560415029, 5.522942201489734, 1.5, 9.338962560415029),
            Block.box(4.022942201489734, 0, 7.488962560415029, 5.022942201489734, 1.5, 8.488962560415029),
            Block.box(7.522942201489734, 0, 3.988962560415031, 8.522942201489734, 1.5, 4.988962560415029),
            Block.box(11.022942201489734, 0, 7.488962560415029, 12.022942201489734, 1.5, 8.488962560415029),
            Block.box(7.522942201489734, 0, 10.988962560415029, 8.522942201489734, 1.5, 11.988962560415029),
            Block.box(4.084921103447956, 0.5, 6.815465725991714, 5.084921103447956, 1.5, 9.515465725991714),
            Block.box(6.672942201489734, 0.5, 4.488962560415031, 9.372942201489733, 1.5, 5.488962560415029),
            Block.box(6.672942201489734, 0.5, 10.488962560415029, 9.372942201489733, 1.5, 11.488962560415029),
            Block.box(7.107476475498018, 0.5, 10.673883663862982, 9.807476475498017, 1.5, 11.673883663862982),
            Block.box(10.522942201489734, 0.5, 6.638962560415029, 11.522942201489734, 1.5, 9.338962560415029),
            Block.box(10.95747647549802, 0.5, 6.823883663862983, 11.95747647549802, 1.5, 9.523883663862982),
            Block.box(10.272942201489734, 1, 6.738962560415029, 11.272942201489734, 6, 9.238962560415029),
            Block.box(6.772942201489734, 1, 10.238962560415029, 9.272942201489734, 6, 11.238962560415029),
            Block.box(6.647942201489734, 1, 4.738962560415029, 9.397942201489734, 6, 5.738962560415029),
            Block.box(4.334921103447956, 1, 6.915465725991712, 5.334921103447956, 6, 9.415465725991712),
            Block.box(9.834921103447956, 1, 6.915465725991712, 10.834921103447956, 6, 9.415465725991712),
            Block.box(6.334921103447956, 1, 10.415465725991714, 8.834921103447956, 6, 11.415465725991714),
            Block.box(4.522942201489734, 2.1, 6.613962560415029, 5.522942201489734, 3.5, 9.363962560415029),
            Block.box(4.522942201489734, 3.6, 6.613962560415029, 5.522942201489734, 5, 9.363962560415029),
            Block.box(4.957476475498018, 2.1, 6.798883663862982, 5.957476475498018, 3.5, 9.548883663862982),
            Block.box(4.957476475498018, 3.6, 6.798883663862982, 5.957476475498018, 5, 9.548883663862982),
            Block.box(6.647942201489734, 2.1, 10.488962560415029, 9.397942201489734, 3.5, 11.488962560415029),
            Block.box(6.647942201489734, 3.6, 10.488962560415029, 9.397942201489734, 5, 11.488962560415029),
            Block.box(10.522942201489734, 2.1, 6.613962560415029, 11.522942201489734, 3.5, 9.363962560415029),
            Block.box(10.522942201489734, 3.6, 6.613962560415029, 11.522942201489734, 5, 9.363962560415029),
            Block.box(7.082476475498019, 2.1, 10.673883663862982, 9.83247647549802, 3.5, 11.673883663862982),
            Block.box(7.082476475498019, 3.6, 10.673883663862982, 9.83247647549802, 5, 11.673883663862982),
            Block.box(6.647942201489734, 2.1, 4.488962560415031, 9.397942201489734, 3.5, 5.488962560415029),
            Block.box(6.647942201489734, 3.6, 4.488962560415031, 9.397942201489734, 5, 5.488962560415029),
            Block.box(7.082476475498019, 3.6, 4.673883663862982, 9.83247647549802, 5, 5.673883663862982),
            Block.box(7.082476475498019, 2.1, 4.673883663862982, 9.83247647549802, 3.5, 5.673883663862982),
            Block.box(10.95747647549802, 2.1, 6.798883663862982, 11.95747647549802, 3.5, 9.548883663862982),
            Block.box(10.95747647549802, 3.6, 6.798883663862982, 11.95747647549802, 5, 9.548883663862982),
            Block.box(6.334921103447956, 1, 4.915465725991714, 8.834921103447956, 6, 5.915465725991714),
            Block.box(6.022942201489734, 7, 6.988962560415029, 9.022942201489734, 9, 10.988962560415029),
            Block.box(5.272942201489734, 5, 6.888962560415029, 10.922942201489734, 8, 9.63896256041503),
            Block.box(6.1229422014897334, 1, 5.988962560415029, 10.022942201489734, 8, 9.988962560415029)
    );



    private static final VoxelShape SHAPE_E =Shapes.or(
            Block.box(7.28603743958497, 1.25, 1.6729422014897302, 8.536037439584968, 7.75, 2.92294220148973),
            Block.box(7.03603743958497, 5.25, 1.4229422014897302, 8.786037439584968, 6.25, 3.17294220148973),
            Block.box(7.03603743958497, 6.5, 1.4229422014897302, 8.786037439584968, 7.5, 3.17294220148973),
            Block.box(7.03603743958497, 4.1, 1.4229422014897302, 8.786037439584968, 5.1, 3.17294220148973),
            Block.box(7.03603743958497, 1.8, 1.4229422014897302, 8.786037439584968, 2.8, 3.17294220148973),
            Block.box(7.03603743958497, 2.95, 1.4229422014897302, 8.786037439584968, 3.95, 3.17294220148973),
            Block.box(7.349058537626748, 0, 4.746439035913047, 9.349058537626746, 6.95, 5.746439035913047),
            Block.box(7.16103743958497, 4.422288122281052, 1.7531684065436575, 8.661037439584968, 5.422288122281052, 4.75316840654366),
            Block.box(7.349058537626748, 0, 9.746439035913053, 9.349058537626746, 6.95, 10.746439035913053),
            Block.box(5.349058537626748, 0, 6.746439035913049, 6.349058537626748, 6.95, 8.746439035913053),
            Block.box(10.349058537626746, 0, 6.746439035913049, 11.349058537626746, 6.95, 8.746439035913053),
            Block.box(4.91103743958497, 0, 6.922942201489732, 5.91103743958497, 6.95, 8.922942201489736),
            Block.box(9.911037439584968, 0, 6.922942201489732, 10.911037439584968, 6.95, 8.922942201489736),
            Block.box(6.91103743958497, 0, 9.922942201489736, 8.911037439584968, 6.95, 10.922942201489736),
            Block.box(6.91103743958497, 0, 4.922942201489732, 8.911037439584968, 6.95, 5.922942201489732),
            Block.box(5.91103743958497, 0.10000000000000009, 5.922942201489732, 9.911037439584968, 1.1, 9.922942201489736),
            Block.box(10.161037439584968, 1, 6.672942201489732, 11.161037439584968, 6, 9.172942201489736),
            Block.box(10.411037439584968, 5.5, 6.572942201489733, 11.411037439584968, 6.5, 9.272942201489736),
            Block.box(10.849058537626746, 5.5, 6.396439035913048, 11.849058537626746, 6.5, 9.09643903591305),
            Block.box(3.9765031655766845, 5.5, 6.388021098041779, 4.976503165576684, 6.5, 9.088021098041782),
            Block.box(6.56103743958497, 5.5, 10.422942201489736, 9.261037439584968, 6.5, 11.422942201489736),
            Block.box(9.976503165576684, 5.5, 6.388021098041779, 10.976503165576684, 6.5, 9.088021098041782),
            Block.box(6.56103743958497, 5.5, 4.422942201489732, 9.261037439584968, 6.5, 5.422942201489732),
            Block.box(6.126503165576686, 5.5, 4.238021098041779, 8.826503165576684, 6.5, 5.238021098041779),
            Block.box(4.41103743958497, 5.5, 6.572942201489733, 5.41103743958497, 6.5, 9.272942201489736),
            Block.box(9.976503165576684, 0.5, 6.388021098041779, 10.976503165576684, 1.5, 9.088021098041782),
            Block.box(10.411037439584968, 0.5, 6.572942201489733, 11.411037439584968, 1.5, 9.272942201489736),
            Block.box(10.911037439584968, 0, 7.422942201489734, 11.911037439584968, 1.5, 8.422942201489736),
            Block.box(7.41103743958497, 0, 10.922942201489736, 8.411037439584968, 1.5, 11.922942201489736),
            Block.box(3.91103743958497, 0, 7.422942201489734, 4.91103743958497, 1.5, 8.422942201489736),
            Block.box(7.41103743958497, 0, 3.922942201489732, 8.411037439584968, 1.5, 4.922942201489732),
            Block.box(10.849058537626746, 0.5, 6.396439035913048, 11.849058537626746, 1.5, 9.09643903591305),
            Block.box(6.56103743958497, 0.5, 10.422942201489736, 9.261037439584968, 1.5, 11.422942201489736),
            Block.box(6.56103743958497, 0.5, 4.422942201489732, 9.261037439584968, 1.5, 5.422942201489732),
            Block.box(6.126503165576686, 0.5, 4.238021098041779, 8.826503165576684, 1.5, 5.238021098041779),
            Block.box(4.41103743958497, 0.5, 6.572942201489733, 5.41103743958497, 1.5, 9.272942201489736),
            Block.box(3.9765031655766845, 0.5, 6.388021098041779, 4.976503165576684, 1.5, 9.088021098041782),
            Block.box(4.66103743958497, 1, 6.672942201489732, 5.66103743958497, 6, 9.172942201489736),
            Block.box(6.66103743958497, 1, 4.672942201489732, 9.161037439584968, 6, 5.672942201489732),
            Block.box(6.53603743958497, 1, 10.172942201489736, 9.286037439584968, 6, 11.172942201489736),
            Block.box(10.599058537626746, 1, 6.496439035913049, 11.599058537626746, 6, 8.996439035913053),
            Block.box(5.099058537626748, 1, 6.496439035913049, 6.099058537626748, 6, 8.996439035913053),
            Block.box(7.099058537626748, 1, 4.496439035913047, 9.599058537626746, 6, 5.496439035913047),
            Block.box(10.411037439584968, 2.1, 6.547942201489732, 11.411037439584968, 3.5, 9.297942201489736),
            Block.box(10.411037439584968, 3.6, 6.547942201489732, 11.411037439584968, 5, 9.297942201489736),
            Block.box(9.976503165576684, 2.1, 6.363021098041779, 10.976503165576684, 3.5, 9.113021098041783),
            Block.box(9.976503165576684, 3.6, 6.363021098041779, 10.976503165576684, 5, 9.113021098041783),
            Block.box(6.53603743958497, 2.1, 4.422942201489732, 9.286037439584968, 3.5, 5.422942201489732),
            Block.box(6.53603743958497, 3.6, 4.422942201489732, 9.286037439584968, 5, 5.422942201489732),
            Block.box(4.41103743958497, 2.1, 6.547942201489732, 5.41103743958497, 3.5, 9.297942201489736),
            Block.box(4.41103743958497, 3.6, 6.547942201489732, 5.41103743958497, 5, 9.297942201489736),
            Block.box(6.101503165576684, 2.1, 4.238021098041779, 8.851503165576682, 3.5, 5.238021098041779),
            Block.box(6.101503165576684, 3.6, 4.238021098041779, 8.851503165576682, 5, 5.238021098041779),
            Block.box(6.53603743958497, 2.1, 10.422942201489736, 9.286037439584968, 3.5, 11.422942201489736),
            Block.box(6.53603743958497, 3.6, 10.422942201489736, 9.286037439584968, 5, 11.422942201489736),
            Block.box(6.101503165576684, 3.6, 10.238021098041783, 8.851503165576682, 5, 11.238021098041783),
            Block.box(6.101503165576684, 2.1, 10.238021098041783, 8.851503165576682, 3.5, 11.238021098041783),
            Block.box(3.9765031655766845, 2.1, 6.363021098041779, 4.976503165576684, 3.5, 9.113021098041783),
            Block.box(3.9765031655766845, 3.6, 6.363021098041779, 4.976503165576684, 5, 9.113021098041783),
            Block.box(7.099058537626748, 1, 9.996439035913053, 9.599058537626746, 6, 10.996439035913053),
            Block.box(6.91103743958497, 7, 4.922942201489732, 9.911037439584968, 9, 8.922942201489736),
            Block.box(5.011037439584969, 5, 6.272942201489732, 10.661037439584968, 8, 9.022942201489736),
            Block.box(5.91103743958497, 1, 5.922942201489732, 9.811037439584968, 8, 9.922942201489736)
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
