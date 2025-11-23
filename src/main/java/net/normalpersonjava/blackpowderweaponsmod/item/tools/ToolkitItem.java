package net.normalpersonjava.blackpowderweaponsmod.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class ToolkitItem extends Item {
    public ToolkitItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(100));
    }
    @Override
    public boolean hasCraftingRemainingItem() {
        return true;

    }
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }
}
