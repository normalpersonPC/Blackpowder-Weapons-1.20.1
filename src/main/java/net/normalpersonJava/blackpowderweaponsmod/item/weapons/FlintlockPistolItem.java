package net.normalpersonJava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;

public class FlintlockPistolItem extends GunItem {
    public FlintlockPistolItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
    @Override
    public double meleeDamage() {return 2;}

    @Override
    public float bulletDamage() {return 16;}

    @Override
    public float bulletSpeed() {return 7;}

    @Override
    public float bulletSpread() {return 2;}

    @Override
    public float piercing() {return 2;}

    @Override
    public float knockback() {return 0.5f;}

    @Override
    public int maxAmmo() {return 1;}

    @Override
    public int pelletCount() {return 1;}

    @Override
    public boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.MUSKETBALL_SMALL.get())) &&
                player.getInventory().contains(new ItemStack(Items.GUNPOWDER));
    }

    @Override
    public boolean twoHanded() {return false;}

    @Override
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
                    if (delay >= 9) { // Check if 8 ticks have passed
                        delay = 0; // Reset delay for next tick
                        modelstate++;
                        setModelState(stack, modelstate);
                        if (modelstate == 1) {
                            playSound(world, player, ModSounds.FLINTLOCK_POWDER.get());
                        }
                        if (modelstate == 2) {
                            playSound(world, player, ModSounds.FLINTLOCK_READY.get());
                        }
                        if (modelstate == 4 || modelstate == 9) {
                            playSound(world, player, ModSounds.FLINTLOCK_ROD0.get());
                        }
                        if (modelstate == 6) {
                            playSound(world, player, ModSounds.FLINTLOCK_ROD1.get());
                        }
                        if (modelstate >= 10) { // If fully loaded
                            setModelState(stack, 10);
                            setAmmoCount(stack, getAmmoCount(stack) + 1);
                            setDelay(stack, 0); // Reset delay
                            setLoaded(stack, true);
                            setFired(stack, false);

                            // Consume the ammo
                            consumeItem(player, Items.GUNPOWDER, 1);
                            consumeItem(player, ModItems.MUSKETBALL_SMALL.get(), 1);

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
        return ModSounds.FLINTLOCK_PISTOL_SHOOT.get();
    }
}
