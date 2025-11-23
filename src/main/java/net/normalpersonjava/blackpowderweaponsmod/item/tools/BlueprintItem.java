package net.normalpersonjava.blackpowderweaponsmod.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BlueprintItem extends Item {
    public BlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;

    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        return new ItemStack(this);

    }
}
