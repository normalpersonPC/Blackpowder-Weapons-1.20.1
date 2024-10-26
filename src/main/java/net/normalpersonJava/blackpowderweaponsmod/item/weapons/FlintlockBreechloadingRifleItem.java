package net.normalpersonJava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;

public class FlintlockBreechloadingRifleItem extends GunItem {
    public FlintlockBreechloadingRifleItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public double meleeDamage() {
        return 2;
    }

    @Override
    public float bulletDamage() {
        return 24;
    }

    @Override
    public float bulletSpeed() {
        return 7.5f;
    }

    @Override
    public float bulletSpread() {
        return 1.5f;
    }

    @Override
    public float piercing() {
        return 3;
    }

    @Override
    public float knockback() {
        return 0.5f;
    }

    @Override
    public float armorPiercing() {return 0.6f;}

    @Override
    public int maxAmmo() {
        return 1;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.MUSKETBALL.get())) &&
                player.getInventory().contains(new ItemStack(Items.GUNPOWDER));
    }

    public void reload (LevelAccessor world, Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                double modelstate = getModelState(stack);
                double delay = getDelay(stack);

                // Ensure the item is not already loaded and that the player has ammo
                if (!isLoaded(stack) && hasAmmo(player)) {
                    delay++;
                    if (delay >= 10) { // Check if 10 ticks have passed
                        delay = 0; // Reset delay for next tick
                        modelstate++;
                        setModelState(stack, modelstate);
                        if (modelstate == 1) {
                            playSound(world, player, ModSounds.FLINTLOCK_POWDER.get());
                        }
                        if (modelstate == 2) {
                            playSound(world, player, ModSounds.FLINTLOCK_READY.get());
                        }
                        if (modelstate == 3) {
                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_OPEN);
                        }
                        if (modelstate == 6) {
                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_CLOSE);
                        }
                        if (modelstate >= 7) { // If fully loaded
                            setModelState(stack, 7);
                            setAmmoCount(stack, getAmmoCount(stack) + 1);
                            setDelay(stack, 0); // Reset delay
                            setLoaded(stack, true);
                            setFired(stack, false);

                            // Consume the ammo
                            consumeItem(player, ModItems.MUSKETBALL.get(), 1);
                            consumeItem(player, Items.GUNPOWDER, 1);

                            player.getCooldowns().addCooldown(stack.getItem(), 2);

                            // Play the sound
                            playSound(world, player, ModSounds.FLINTLOCK_READY.get());
                        }
                    }
                    setDelay(stack, delay);
                }
            }
        }
    }

    @Override
    public SoundEvent fireSFX() {
        return ModSounds.FLINTLOCK_MUSKET_SHOOT.get();
    }
}
