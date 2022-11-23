package net.the_goldbeards.lootdebugs.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.DrawSelectionEvent;
import net.minecraftforge.fluids.FluidStack;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.mixin.accessors.client.PlayerControllerAccess;
import net.the_goldbeards.lootdebugs.mixin.accessors.client.WorldRendererAccess;

import java.util.Collection;
import java.util.regex.Pattern;

public class HelpfullStuff
{
    public static class ItemHelpers
    {
        public static void rotateDrills(Level level, ItemStack pStack) {
            if (!level.isClientSide) {
                if (pStack.getOrCreateTag().contains("lootdebugs.drills_rot")) {
                    float i = pStack.getOrCreateTag().getFloat("lootdebugs.drills_rot");
                    i += 0.25f;
                    pStack.getOrCreateTag().putFloat("lootdebugs.drills_rot", i);

                    if (pStack.getOrCreateTag().getFloat("lootdebugs.drills_rot") >= 1f) {
                        pStack.getOrCreateTag().putFloat("lootdebugs.drills_rot", 0);
                    }
                } else if (!pStack.getOrCreateTag().contains("lootdebugs.drills_rot")) {
                    pStack.getOrCreateTag().putFloat("lootdebugs.drills_rot", 0.0f);
                }
            }
        }
    }


    public static boolean isBlockTag(Block block, TagKey<Block> tagKey)
    {
        return block.defaultBlockState().is(tagKey);
        //return Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(block).get()).is(tagKey);
    }

    public static void drawAdditionalBlockbreak(DrawSelectionEvent.HighlightBlock ev, Player player, Collection<BlockPos> blocks) {
        Vec3 renderView = ev.getCamera().getPosition();
        for (BlockPos pos : blocks) {

            ((WorldRendererAccess) ev.getLevelRenderer()).callRenderHitOutline(
                    ev.getPoseStack(),
                    ev.getMultiBufferSource().getBuffer(RenderType.lines()),
                    player,
                    renderView.x, renderView.y, renderView.z,
                    pos,
                    ClientUtils.mc().level.getBlockState(pos)


            );
        }

        PoseStack transform = ev.getPoseStack();
        transform.pushPose();
        transform.translate(-renderView.x, -renderView.y, -renderView.z);
        MultiPlayerGameMode controllerMP = ClientUtils.mc().gameMode;
        if (controllerMP.isDestroying())


            drawBlockDamageTexture(transform, ev.getMultiBufferSource(), player.level, blocks);
        transform.popPose();
    }


    public static void drawBlockDamageTexture(PoseStack matrix, MultiBufferSource buffers, Level world, Collection<BlockPos> blocks) {
        MultiPlayerGameMode controller = Minecraft.getInstance().gameMode;
        int progress = (int) (((PlayerControllerAccess) controller).getDestroyProgress() * 10f) - 1; // 0-10
        if (progress < 0 || progress >= ModelBakery.DESTROY_TYPES.size())
            return;
        BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
        for (BlockPos blockpos : blocks) {
            matrix.pushPose();
            matrix.translate(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            VertexConsumer worldRendererIn = buffers.getBuffer(ModelBakery.DESTROY_TYPES.get(progress));
            worldRendererIn = new SheetedDecalTextureGenerator(worldRendererIn, matrix.last().pose(), matrix.last().normal());
            Block block = world.getBlockState(blockpos).getBlock();
            boolean hasBreak = block instanceof ChestBlock || block instanceof EnderChestBlock
                    || block instanceof SignBlock || block instanceof SkullBlock;

            if (!hasBreak) {
                BlockState iblockstate = world.getBlockState(blockpos);
                if (iblockstate.getMaterial() != Material.AIR) {
                    blockrendererdispatcher.renderBreakingTexture(iblockstate, blockpos, world, matrix, worldRendererIn);
                }
            }
            matrix.popPose();
        }
    }

    public static class ClientUtils {
        public static Minecraft mc() {
            return Minecraft.getInstance();
        }
    }

    public static class ClassTranslator {

        public static boolean isRightDwarfClass(int dwarfClass, int dwarfclasstest) {
            return dwarfclasstest == dwarfClass;
        }

        public static TranslatableComponent getClassTranslate(IClassData.Classes pClass) {
            switch (pClass)
            {

                case Driller -> {
                    return new TranslatableComponent("player.class.driller");
                }

                case Engineer -> {
                    return new TranslatableComponent("player.class.engineer");
                }
                case Scout -> {
                    return new TranslatableComponent("player.class.scout");
                }
                case Gunner -> {
                    return new TranslatableComponent("player.class.gunner");
                }
                case TheTrueSCP -> {
                    return new TranslatableComponent("player.class.thetruescp");
                }
                case MonsieurHannes -> {
                    return new TranslatableComponent("player.class.monsieurhannes");
                }
                default -> {
                    return new TranslatableComponent("player.class.leaf_lover");
                }
            }
        }

        public static ChatFormatting getClassColor(IClassData.Classes pClass) {
            switch (pClass) {

                case Driller ->
                        {
                            return ChatFormatting.YELLOW;
                        }

                case Engineer ->
                        {
                            return ChatFormatting.DARK_RED;
                        }
                case Scout ->
                        {
                            return ChatFormatting.BLUE;
                        }
                case Gunner ->
                        {
                            return ChatFormatting.GREEN;
                        }
                case TheTrueSCP ->
                        {
                            return ChatFormatting.AQUA;
                        }
                case MonsieurHannes ->
                        {
                            return ChatFormatting.GOLD;
                        }
                default -> {
                    return ChatFormatting.DARK_GRAY;
                }
            }
        }
    }


    public static class ItemNBTHelper {

        public static boolean hasTag(ItemStack stack) {
            return stack.hasTag();
        }

        public static boolean hasKey(ItemStack stack, String key) {
            return hasTag(stack) && stack.getOrCreateTag().contains(key);
        }

        public static boolean hasKey(ItemStack stack, String key, int type) {
            return hasTag(stack) && stack.getOrCreateTag().contains(key, type);
        }

        public static void remove(ItemStack stack, String key) {
            stack.getOrCreateTag().remove(key);
        }


        public static void put(ItemStack stack, String key, Tag val) {
            stack.getOrCreateTag().put(key, val);
        }

        public static void putInt(ItemStack stack, String key, int val) {
            stack.getOrCreateTag().putInt(key, val);
        }

        public static void modifyInt(CompoundTag tagCompound, String key, int mod) {
            tagCompound.putInt(key, tagCompound.getInt(key) + mod);
        }

        public static int getInt(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getInt(key) : 0;
        }

        public static void putString(ItemStack stack, String key, String val) {
            stack.getOrCreateTag().putString(key, val);
        }

        public static String getString(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getString(key) : "";
        }

        public static void putLong(ItemStack stack, String key, long val) {
            stack.getOrCreateTag().putLong(key, val);
        }

        public static long getLong(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getLong(key) : 0;
        }

        public static void putIntArray(ItemStack stack, String key, int[] val) {
            stack.getOrCreateTag().putIntArray(key, val);
        }

        public static int[] getIntArray(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getIntArray(key) : new int[0];
        }

        public static void putFloat(ItemStack stack, String key, float val) {
            stack.getOrCreateTag().putFloat(key, val);
        }

        public static void modifyFloat(CompoundTag tagCompound, String key, float mod) {
            tagCompound.putFloat(key, tagCompound.getFloat(key) + mod);
        }

        public static float getFloat(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getFloat(key) : 0;
        }

        public static void putBoolean(ItemStack stack, String key, boolean val) {
            stack.getOrCreateTag().putBoolean(key, val);
        }

        public static boolean getBoolean(ItemStack stack, String key) {
            return hasTag(stack) && stack.getOrCreateTag().getBoolean(key);
        }

        public static void setTagCompound(ItemStack stack, String key, CompoundTag val) {
            stack.getOrCreateTag().put(key, val);
        }

        public static CompoundTag getTagCompound(ItemStack stack, String key) {
            return hasTag(stack) ? stack.getOrCreateTag().getCompound(key) : new CompoundTag();
        }

        public static void setFluidStack(ItemStack stack, String key, FluidStack val) {
            if (val != null && val.getFluid() != null)
                setTagCompound(stack, key, val.writeToNBT(new CompoundTag()));
            else
                remove(stack, key);
        }

        public static FluidStack getFluidStack(ItemStack stack, String key) {
            if (hasTag(stack))
                return FluidStack.loadFluidStackFromNBT(getTagCompound(stack, key));
            return null;
        }

        public static void setItemStack(ItemStack stack, String key, ItemStack val) {
            stack.getOrCreateTag().put(key, val.save(new CompoundTag()));
        }

        public static ItemStack getItemStack(ItemStack stack, String key) {
            if (hasTag(stack) && stack.getOrCreateTag().contains(key))
                return ItemStack.of(getTagCompound(stack, key));
            return ItemStack.EMPTY;
        }

        public static void setLore(ItemStack stack, Component... lore) {
            CompoundTag displayTag = getTagCompound(stack, "display");
            ListTag list = new ListTag();
            for (Component s : lore)
                list.add(StringTag.valueOf(Component.Serializer.toJson(s)));
            displayTag.put("Lore", list);
            setTagCompound(stack, "display", displayTag);
        }

        public static CompoundTag combineTags(CompoundTag target, CompoundTag add, Pattern pattern) {
            if (target == null || target.isEmpty())
                return add.copy();
            for (String key : add.getAllKeys())
                if (pattern == null || pattern.matcher(key).matches())
                    if (!target.contains(key))
                        target.put(key, add.get(key));
                    else {
                        switch (add.getTagType(key)) {
                            case Tag.TAG_BYTE -> target.putByte(key, (byte) (target.getByte(key) + add.getByte(key)));
                            case Tag.TAG_SHORT -> target.putShort(key, (short) (target.getShort(key) + add.getShort(key)));
                            case Tag.TAG_INT -> target.putInt(key, (target.getInt(key) + add.getInt(key)));
                            case Tag.TAG_LONG -> target.putLong(key, (target.getLong(key) + add.getLong(key)));
                            case Tag.TAG_FLOAT -> target.putFloat(key, (target.getFloat(key) + add.getFloat(key)));
                            case Tag.TAG_DOUBLE -> target.putDouble(key, (target.getDouble(key) + add.getDouble(key)));
                            case Tag.TAG_BYTE_ARRAY -> {
                                byte[] bytesTarget = target.getByteArray(key);
                                byte[] bytesAdd = add.getByteArray(key);
                                byte[] bytes = new byte[bytesTarget.length + bytesAdd.length];
                                System.arraycopy(bytesTarget, 0, bytes, 0, bytesTarget.length);
                                System.arraycopy(bytesAdd, 0, bytes, bytesTarget.length, bytesAdd.length);
                                target.putByteArray(key, bytes);
                            }
                            case Tag.TAG_STRING -> target.putString(key, (target.getString(key) + add.getString(key)));
                            case Tag.TAG_LIST -> {
                                ListTag listTarget = (ListTag) target.get(key);
                                ListTag listAdd = (ListTag) add.get(key);
                                listTarget.addAll(listAdd);
                                target.put(key, listTarget);
                            }
                            case Tag.TAG_COMPOUND -> combineTags(target.getCompound(key), add.getCompound(key), null);
                            case Tag.TAG_INT_ARRAY -> {
                                int[] intsTarget = target.getIntArray(key);
                                int[] intsAdd = add.getIntArray(key);
                                int[] ints = new int[intsTarget.length + intsAdd.length];
                                System.arraycopy(intsTarget, 0, ints, 0, intsTarget.length);
                                System.arraycopy(intsAdd, 0, ints, intsTarget.length, intsAdd.length);
                                target.putIntArray(key, ints);
                            }
                        }
                    }
            return target;
        }
    }

    public static class Math
    {
        public static float clamp(float min, float max, float value)
        {
            if(value > max)
            {
                return max;
            }
            else if(value < min)
            {
                return min;
            }
            else
            {
                return value;
            }

        }

    }
}