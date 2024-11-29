
package net.normalpersonJava.blackpowderweaponsmod.item.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public abstract class RevolverItem extends GunItem {
    public RevolverItem(Properties properties) {
        super(properties);
    }

    public abstract void revolve(LevelAccessor world, Entity entity, ItemStack stack);

    public void setRevolveState(ItemStack stack, double state) {
        stack.getOrCreateTag().putDouble("revolve", state);
    }
    public double getRevolveState(ItemStack stack) {
        return stack.getOrCreateTag().getDouble("revolve");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotID, boolean selected) {
        super.inventoryTick(stack, level, entity, slotID, selected);
        if (entity instanceof Player player && selected) {
            if (isReloading(stack)) {
                reload(level, player, stack);
            }
            if (isLoaded(stack) && hasFired(stack)) {
                revolve(level, entity, stack);
            }
        }
    }

}
