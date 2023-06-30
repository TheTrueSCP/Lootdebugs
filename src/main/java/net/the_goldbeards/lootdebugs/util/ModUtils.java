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
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.mixin.accessors.client.PlayerControllerAccess;
import net.the_goldbeards.lootdebugs.mixin.accessors.client.WorldRendererAccess;

import java.util.Collection;
import java.util.regex.Pattern;


public class ModUtils
{
    public static class DataTypeHelper
    {
        public static BlockPos addVecToBlockPos(BlockPos pos, Vec3 additionalVec, int normalizedNumber)
        {
            double x = pos.getX() + getNormalizedNumber(additionalVec.x, normalizedNumber);
            double y = pos.getY() + getNormalizedNumber(additionalVec.y, normalizedNumber);
            double z = pos.getZ() + getNormalizedNumber(additionalVec.z, normalizedNumber);



            return new BlockPos(x, y, z);
        }

        public static int getNormalizedNumber(double number, int normalizedNumber)
        {
            if(number > 0)
            {
                return normalizedNumber;
            }

            if(number < 0)
            {
                return -normalizedNumber;
            }

            return 0;
        }
    }

    public static class ItemNBTHelper
    {

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

        public static float getFloat(ItemStack stack, String key, float pDefault) {
            return hasKey(stack, key) ? stack.getOrCreateTag().getFloat(key) : pDefault;
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

        public static void rotateDrills(Level level, ItemStack pStack) {
            if (!level.isClientSide)
            {
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

        public static void rotateDrills(ItemStack pStack)
        {
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


    public static class BlockHelpers
    {
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

        public static boolean isBlockTag(Block block, TagKey<Block> tagKey)
        {
            return block.defaultBlockState().is(tagKey);
        }
    }

    public static class DwarfClasses
    {
        public static void setPlayerClass(Player pPlayer, IClassData.Classes newClass)
        {
            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
               classCap.setDwarfClass(newClass);
            });
        }

        public static boolean isPlayerClass(Player pPlayer, IClassData.Classes dwarfClassToUse)
        {
            var playerClassWrapper = new Object(){String pClass;};

            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                playerClassWrapper.pClass = classCap.getDwarfClass().name();
            });

            String playerClass = playerClassWrapper.pClass;

            if(playerClass.equals(dwarfClassToUse.name()))
            {
                return true;
            }
            else if(playerClass.equals("TheTrueSCP") && dwarfClassToUse == IClassData.Classes.Driller)
            {
                return true;
            }
            else if(playerClass.equals("MonsieurHannes") && dwarfClassToUse == IClassData.Classes.Scout)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static IClassData.Classes getPlayerClass(Player player)
        {
            var playerClassWrapper = new Object(){String pClass;};

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                playerClassWrapper.pClass = classCap.getDwarfClass().name();
            });


            if(playerClassWrapper.pClass != null)
            {
                return IClassData.Classes.valueOf(playerClassWrapper.pClass);
            }
            return IClassData.Classes.LeafLover;
        }

        public static boolean canPlayerInteract(Player player)
        {
            return !canPlayerDo(getPlayerClass(player), IClassData.Classes.LeafLover);
        }
        
        public static boolean canPlayerUseItem(ItemStack pStack, Player pPlayer, IClassData.Classes dwarfClassToUse)
        {
            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {

                ModUtils.ItemNBTHelper.putString(pStack, "stack_dwarfclass", classCap.getDwarfClass().name());//Write every tick the Playerclass into the item


            });

            return canPlayerDo(IClassData.Classes.valueOf(ItemNBTHelper.getString(pStack, "stack_dwarfclass")), dwarfClassToUse);
        }

        /**
         * Only use it if you have already saved the class into the itemstack!
         */
        public static boolean canItemBeUsed(ItemStack pStack, IClassData.Classes dwarfClassToUse)
        {
           return canPlayerDo(IClassData.Classes.valueOf(ItemNBTHelper.getString(pStack, "stack_dwarfclass")), dwarfClassToUse);
        }

        public static boolean canPlayerDo(IClassData.Classes currentDwarfClass, IClassData.Classes dwarfClassRequired)
        {
            if(currentDwarfClass == dwarfClassRequired)
            {
                return true;
            }
            else if(currentDwarfClass.name().equals("TheTrueSCP") && dwarfClassRequired == IClassData.Classes.Driller)
            {
                return true;
            }
            else if(currentDwarfClass.name().equals("MonsieurHannes") && dwarfClassRequired == IClassData.Classes.Scout)
            {
                return true;
            }
            else if(currentDwarfClass.name().equals("Karl"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        public static float getSalutePitch(Player pPlayer)
        {
            var playerClassWrapper = new Object(){String pClass;};

            pPlayer.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                playerClassWrapper.pClass = classCap.getDwarfClass().name();
            });

            IClassData.Classes playerClass = IClassData.Classes.valueOf(playerClassWrapper.pClass);

            switch (playerClass)
            {
                case Driller, TheTrueSCP ->
                        {
                            return 0.80f;
                        }
                case Scout, MonsieurHannes ->
                        {
                            return 1.10f;
                        }
                case Engineer ->
                        {
                            return 1;
                        }
                case Gunner ->
                        {
                            return 0.90f;
                        }
                default ->
                        {
                            return 1;
                        }
            }

        }

        public static float getSalutePitch(IClassData.Classes playerClass)
        {

            switch (playerClass)
            {
                case Driller, TheTrueSCP ->
                        {
                            return 0.80f;
                        }
                case Scout, MonsieurHannes ->
                        {
                            return 1.10f;
                        }
                case Engineer ->
                        {
                            return 1;
                        }
                case Gunner ->
                        {
                            return 0.90f;
                        }
                default ->
                        {
                            return 1;
                        }
            }

        }

        //UI Stuff
        public static TranslatableComponent getClassTranslate(IClassData.Classes pClass) {
            switch (pClass)
            {

                case Driller -> {
                    return new TranslatableComponent("class.lootdebugs.player.driller");
                }

                case Engineer -> {
                    return new TranslatableComponent("class.lootdebugs.player.engineer");
                }
                case Scout -> {
                    return new TranslatableComponent("class.lootdebugs.player.scout");
                }
                case Gunner -> {
                    return new TranslatableComponent("class.lootdebugs.player.gunner");
                }
                case TheTrueSCP -> {
                    return new TranslatableComponent("class.lootdebugs.player.thetruescp");
                }
                case MonsieurHannes -> {
                    return new TranslatableComponent("class.lootdebugs.player.monsieurhannes");
                }
                case Karl -> {
                    return new TranslatableComponent("class.lootdebugs.player.karl");
                }
                default -> {
                    return new TranslatableComponent("class.lootdebugs.player.leaf_lover");
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
                case Karl ->
                {
                    return ChatFormatting.LIGHT_PURPLE;
                }
                default -> {
                    return ChatFormatting.DARK_GRAY;
                }
            }
        }
        
        public static boolean isPlayerDwarf(Player player)
        {
            return getPlayerClass(player) != IClassData.Classes.LeafLover;
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

    public static class ClientUtils {
        public static Minecraft mc() {
            return Minecraft.getInstance();
        }
    }

    public class MouseUtil {
        public static boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
            return isMouseOver(mouseX, mouseY, x, y, 16);
        }

        public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int size) {
            return isMouseOver(mouseX, mouseY, x, y, size, size);
        }

        public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
            return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
        }
    }

}
