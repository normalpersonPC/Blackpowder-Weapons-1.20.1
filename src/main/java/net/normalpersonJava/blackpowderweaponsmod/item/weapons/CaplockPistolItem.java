package net.normalpersonJava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;

public class CaplockPistolItem extends GunItem {
    public CaplockPistolItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
    @Override
    public boolean twoHanded() {return false;}

    @Override
    public double meleeDamage() {return 1;}

    @Override
    public float bulletDamage() {return 20;}

    @Override
    public float bulletSpeed() {return 7.5f;}

    @Override
    public float bulletSpread() {return 1.5f;}

    @Override
    public float piercing() {return 2;}

    @Override
    public float knockback() {return 0.2f;}

    @Override
    public float armorPiercing() {return 0.3f;}

    @Override
    public int maxAmmo() {return 1;}

    @Override
    public int pelletCount() {return 1;}

    @Override
    public boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.PISTOL_BULLET.get())) &&
                player.getInventory().contains(new ItemStack(Items.GUNPOWDER)) &&
                player.getInventory().contains(new ItemStack(ModItems.PERCUSSION_CAP.get()));
    }

    @Override
    public void reload(LevelAccessor world, Entity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                double modelstate = getModelState(stack);
                double delay = getDelay(stack);
                if (!isLoaded(stack) && hasAmmo(player)) {
                    delay++;
                    if (delay >= 9) { // Check if 10 ticks have passed
                        delay = 0; // Reset delay for next tick
                        modelstate++;
                        setModelState(stack, modelstate);
                        //reload
                        if (modelstate == 1 && hasFired(stack)) {
                                playSound(world, player, ModSounds.FLINTLOCK_READY.get());
                        }
                        if (modelstate == 2) {
                            playSound(world, player, SoundEvents.WOOL_PLACE);
                        }
                        if (modelstate == 3 || modelstate == 8) {
                            playSound(world, player, ModSounds.FLINTLOCK_ROD0.get());
                        }
                        if (modelstate == 5) {
                            playSound(world, player, ModSounds.FLINTLOCK_ROD1.get());
                        }
                        if (modelstate == 10) {
                            playSound(world, player, SoundEvents.COPPER_PLACE);
                        }
                        if (modelstate >= 11) {
                            setModelState(stack, 11);// If fully loaded
                            setAmmoCount(stack, getAmmoCount(stack) + 1);
                            setDelay(stack, 0); // Reset delay
                            setLoaded(stack, true);
                            setFired(stack, false);

                            consumeItem(player, Items.GUNPOWDER, 1);
                            consumeItem(player, ModItems.MUSKETBALL_SMALL.get(), 1);
                            consumeItem(player, ModItems.PERCUSSION_CAP.get(), 1);

                            player.getCooldowns().addCooldown(stack.getItem(), 4);

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
