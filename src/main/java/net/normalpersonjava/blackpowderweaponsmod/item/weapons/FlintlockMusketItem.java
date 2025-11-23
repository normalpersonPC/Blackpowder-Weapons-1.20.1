
package net.normalpersonjava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonjava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonjava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonjava.blackpowderweaponsmod.item.base.GunItem;

public class FlintlockMusketItem extends GunItem {
    public FlintlockMusketItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public double meleeDamage() {
        return 2;
    }

    @Override
    public float bulletDamage() {
        return 20;
    }

    @Override
    public float bulletSpeed() {
        return 7f;
    }

    @Override
    public float bulletSpread() {
        return 1.75f;
    }

    @Override
    public float piercing() {
        return 3;
    }

    @Override
    public float knockback() {
        return 0.4f;
    }

    @Override
    public float armorPiercing() {
        return 0.4f;
    }

    @Override
    public int maxAmmo() {
        return 1;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public ItemStack[] ammoNeeded() {
        return new ItemStack[] {
                new ItemStack(ModItems.MUSKETBALL.get()),
                new ItemStack(Items.GUNPOWDER)
        };
    }

    @Override
    public boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.MUSKETBALL.get())) &&
                player.getInventory().contains(new ItemStack(Items.GUNPOWDER));
    }

    @Override
    public void reload(LevelAccessor world, Entity entity, ItemStack stack) {
        if (!(entity instanceof Player player) || world.isClientSide()) {
            return;
        }
        double modelstate = getModelState(stack);
        double delay = getDelay(stack);
        delay++;

        if (delay >= 10) { // Check if 10 ticks have passed
            delay = 0; // Reset delay for next tick
            modelstate++;
            setModelState(stack, modelstate);

            if (modelstate == 1) {
                playSound(world, player, ModSounds.FLINTLOCK_POWDER.get());
            } else if (modelstate == 2) {
                playSound(world, player, ModSounds.FLINTLOCK_READY.get());
            } else if (modelstate == 4 || modelstate == 12) {
                playSound(world, player, ModSounds.FLINTLOCK_ROD0.get());
            } else if (modelstate == 6 || modelstate == 8) {
                playSound(world, player, ModSounds.FLINTLOCK_ROD1.get());
            } else if (modelstate == 13) {
                finalizeReload(world, player, stack);
            }
        }
        setDelay(stack, delay);
    }

    private void finalizeReload(LevelAccessor world, Player player, ItemStack stack) {
        playSound(world, player, ModSounds.FLINTLOCK_READY.get());
        consumeItem(player, Items.GUNPOWDER, 1);
        consumeItem(player, ModItems.MUSKETBALL.get(), 1);
        setAmmoCount(stack, 1);
        setFired(stack, false);
        setModelState(stack, 13);
        setDelay(stack, 0);
        setReloading(stack, false);
        player.getCooldowns().addCooldown(stack.getItem(), 5);
    }

    @Override
    public SoundEvent fireSFX() {
        return ModSounds.FLINTLOCK_MUSKET_SHOOT.get();
    }
}
