package net.the_goldbeards.lootdebugs.Items.Armor;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.the_goldbeards.lootdebugs.capability.Class.ClassDataCap;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.client.model.Armor.DrillerMK1ArmorModel;
import net.the_goldbeards.lootdebugs.util.UsefullStuff;

import java.util.Collections;
import java.util.Map;

public abstract class ModDrillerMK1ArmorItem extends ArmorItem {


    public ModDrillerMK1ArmorItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Driller;

    public static class Helmet extends ModDrillerMK1ArmorItem {

        public Helmet(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
            super(pMaterial, pSlot, pProperties);
        }

        public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                @Override
                public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("head", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).Head,
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
            if (itemstack1.isEmpty() && canEquip(itemstack, equipmentslot,pPlayer)) {
                pPlayer.setItemSlot(equipmentslot, itemstack.copy());
                if (!pLevel.isClientSide()) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }

                itemstack.setCount(0);
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        @Override
        public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {



            entity.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass", classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name())) {

                if(entity instanceof Player player)
                {
                    player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
                }
                return false;
            }

            return super.canEquip(stack, armorType, entity);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name()))
            {
                ItemStack stack1 = stack.copy();
                player.getInventory().removeItem(stack1);
                player.getInventory().add(stack);
            }


            super.onArmorTick(stack, level, player);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "lootdebugs:textures/armor/driller_mk1.png";
        }
    }

    public static class Chestplate extends ModDrillerMK1ArmorItem {

        public Chestplate(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
            super(pMaterial, pSlot, pProperties);
        }

        public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("body", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).Body,
                                    "left_arm", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).LeftArm,
                                    "right_arm", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).RightArm,
                                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
            if (itemstack1.isEmpty() && canEquip(itemstack, equipmentslot,pPlayer)) {
                pPlayer.setItemSlot(equipmentslot, itemstack.copy());
                if (!pLevel.isClientSide()) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }

                itemstack.setCount(0);
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        @Override
        public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {



            entity.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(UsefullStuff.ItemNBTHelper.getString(stack,"driller_mk1_armor_dwarfclass") != dwarfClassToUse.name()) {

                if(entity instanceof Player player)
                {
                    player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
                }
                return false;
            }

            return super.canEquip(stack, armorType, entity);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name()))
            {
                ItemStack stack1 = stack.copy();
                player.getInventory().removeItem(stack1);
                player.getInventory().add(stack);
            }


            super.onArmorTick(stack, level, player);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "lootdebugs:textures/armor/driller_mk1.png";
        }
    }

    public static class Leggings extends ModDrillerMK1ArmorItem {

        public Leggings(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
            super(pMaterial, pSlot, pProperties);
        }

        public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("left_leg", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).LeftLeg,
                                    "right_leg", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).RightLeg,
                                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
            if (itemstack1.isEmpty() && canEquip(itemstack, equipmentslot,pPlayer)) {
                pPlayer.setItemSlot(equipmentslot, itemstack.copy());
                if (!pLevel.isClientSide()) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }

                itemstack.setCount(0);
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        @Override
        public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {



            entity.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(UsefullStuff.ItemNBTHelper.getString(stack,"driller_mk1_armor_dwarfclass") != dwarfClassToUse.name())
            {

                if(entity instanceof Player player)
                {
                    player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
                }
                return false;
            }

            return super.canEquip(stack, armorType, entity);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name()) )
            {
                ItemStack stack1 = stack.copy();
                player.getInventory().removeItem(stack1);
                player.getInventory().add(stack);
            }


            super.onArmorTick(stack, level, player);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "lootdebugs:textures/armor/driller_mk1_without_boots.png";
        }
    }

    public static class Boots extends ModDrillerMK1ArmorItem {

        public Boots(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
            super(pMaterial, pSlot, pProperties);
        }

        public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
            consumer.accept(new IItemRenderProperties() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("left_leg", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).LeftLeg,
                                    "right_leg", new DrillerMK1ArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DrillerMK1ArmorModel.LAYER_LOCATION)).RightLeg,
                                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            ItemStack itemstack1 = pPlayer.getItemBySlot(equipmentslot);
            if (itemstack1.isEmpty() && canEquip(itemstack, equipmentslot,pPlayer)) {
                pPlayer.setItemSlot(equipmentslot, itemstack.copy());
                if (!pLevel.isClientSide()) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }

                itemstack.setCount(0);
                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        @Override
        public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {



            entity.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name())) {

                if(entity instanceof Player player)
                {
                    player.displayClientMessage(new TextComponent(ChatFormatting.RED + new TranslatableComponent("tool.wrong_class").getString() + " " + UsefullStuff.ClassTranslator.getClassTranslate(dwarfClassToUse).getString()), true);
                }
                return false;
            }

            return super.canEquip(stack, armorType, entity);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {

            player.getCapability(ClassDataCap.CLASS_DATA).ifPresent(classCap ->
            {
                UsefullStuff.ItemNBTHelper.putString(stack,"driller_mk1_armor_dwarfclass",classCap.getDwarfClass().name());

            });

            if(!UsefullStuff.ItemNBTHelper.getString(stack, "driller_mk1_armor_dwarfclass").equals(dwarfClassToUse.name()))
            {
                ItemStack stack1 = stack.copy();
                player.getInventory().removeItem(stack1);
                player.getInventory().add(stack);
            }


            super.onArmorTick(stack, level, player);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "lootdebugs:textures/armor/driller_mk1_without_leggings.png";
        }
    }

    
}
