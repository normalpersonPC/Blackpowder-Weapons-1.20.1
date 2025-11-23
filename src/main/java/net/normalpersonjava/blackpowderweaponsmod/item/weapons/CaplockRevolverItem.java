package net.normalpersonjava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonjava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonjava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonjava.blackpowderweaponsmod.item.base.RevolverItem;

public class CaplockRevolverItem extends RevolverItem {
    public CaplockRevolverItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
    @Override
    public boolean twoHanded() {
        return false;
    }

    @Override
    public double meleeDamage() {
        return 2;
    }

    @Override
    public float bulletDamage() {
        return 10;
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
        return 2;
    }

    @Override
    public float knockback() {
        return 0.2f;
    }

    @Override
    public float armorPiercing() {
        return 0.3f;
    }

    @Override
    public int maxAmmo() {
        return 6;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public ItemStack[] ammoNeeded() {
        return new ItemStack[] {
                new ItemStack(ModItems.PAPER_CARTRIDGE_PISTOL.get())
        };
    }

    @Override
    public void reload(LevelAccessor world, Entity entity, ItemStack stack) {
        if (!(entity instanceof Player player) || world.isClientSide()) {
            return;
        }

        double modelState = getModelState(stack);
        double delay = getDelay(stack);

        delay++;
        if (delay >= 7) {
            delay = 0;
            modelState++;
            setModelState(stack, modelState);

            // Handle intermediate states
            if (modelState == 4) {
                playSound(world, player, SoundEvents.WOOL_PLACE);
            } else if (modelState == 8) {
                playSound(world, player, SoundEvents.IRON_TRAPDOOR_OPEN);
            } else if (modelState == 9) {
                playSound(world, player, SoundEvents.IRON_TRAPDOOR_CLOSE);
                consumeItem(player, ModItems.PAPER_CARTRIDGE_PISTOL.get(), 1);
                setAmmoCount(stack, getAmmoCount(stack) + 1);

                if (getAmmoCount(stack) == maxAmmo()) {
                    finalizeReload(world, player, stack);
                } else if (hasAmmo(player)) {
                    // Reset for next round
                    setModelState(stack, 2);
                    setDelay(stack, 0);
                    setFired(stack, false);
                } else if (!hasAmmo(player) && isLoaded(stack)) {
                    //if out of bullets during reload
                    finalizeReload(world, player, stack);
                }
            }
        }

        // Final check for fully loaded state
        if (getAmmoCount(stack) == maxAmmo()) {
            finalizeReload(world, player, stack);
        }

        // Update delay
        setDelay(stack, delay);
    }

    private void finalizeReload(LevelAccessor world, Player player, ItemStack stack) {
        playSound(world, player, ModSounds.FLINTLOCK_READY.get());
        setFired(stack, false);
        setModelState(stack, 10);
        setDelay(stack, 0);
        setReloading(stack, false);
        player.getCooldowns().addCooldown(stack.getItem(), 5);
    }




    @Override
    public void revolve(LevelAccessor world, Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                double modelstate = getModelState(stack);
                double delay = getDelay(stack);
                double revolve = getRevolveState(stack);

                if (isLoaded(stack)) {
                    delay++;
                    if (delay >= 6) { // Check if 6 ticks have passed
                        delay = 0; // Reset delay for next tick
                        revolve++;
                        setRevolveState(stack, revolve);
                        setModelState(stack, 1);
                        //revolve
                        if (modelstate == 1 && revolve >= 1) {
                            playSound(world, player, ModSounds.FLINTLOCK_READY.get());
                            player.getCooldowns().addCooldown(stack.getItem(), 5);
                            setModelState(stack, 10);
                            setRevolveState(stack, 0);
                            setFired(stack, false);
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
