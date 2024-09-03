package net.normalpersonJava.blackpowderweaponsmod.item.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class BulletMoldItem extends Item {
    public BulletMoldItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
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
