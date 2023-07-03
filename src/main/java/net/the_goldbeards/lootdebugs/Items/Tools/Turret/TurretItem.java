package net.the_goldbeards.lootdebugs.Items.Tools.Turret;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.the_goldbeards.lootdebugs.Entities.Tools.Turret.TurretEntity;
import net.the_goldbeards.lootdebugs.capability.Class.IClassData;
import net.the_goldbeards.lootdebugs.util.ModTags;
import net.the_goldbeards.lootdebugs.util.ModUtils;

public class TurretItem extends Item {
    public TurretItem(Properties pProperties) {
        super(pProperties);
    }

    public static IClassData.Classes dwarfClassToUse = IClassData.Classes.Engineer;

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        if(ModUtils.DwarfClasses.isPlayerClass(pContext.getPlayer(), dwarfClassToUse)) {

            TurretEntity turretEntity = new TurretEntity(pContext.getLevel(), pContext.getPlayer());
            ItemStack usedStack = pContext.getItemInHand();
            BlockPos placePos = pContext.getClickedPos().above(1);

            turretEntity.setPos(placePos.getX() + 0.5f, placePos.getY(), placePos.getZ() + 0.5f);

             turretEntity.setYRot(0);

            if (ModUtils.ItemNBTHelper.hasKey(usedStack, "ammo")) {
                int ammo = ModUtils.ItemNBTHelper.getInt(usedStack, "ammo");
                turretEntity.setAmmo(ammo);
            } else {
                turretEntity.setAmmo(TurretEntity.defaultAmmo);
            }

            pContext.getLevel().addFreshEntity(turretEntity);
            pContext.getItemInHand().shrink(1);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
