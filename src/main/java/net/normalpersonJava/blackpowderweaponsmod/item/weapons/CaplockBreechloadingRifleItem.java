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

public class CaplockBreechloadingRifleItem extends GunItem {
    public CaplockBreechloadingRifleItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public double meleeDamage() {return 2;}

    @Override
    public float bulletDamage() {return 22;}

    @Override
    public float bulletSpeed() {return 8f;}

    @Override
    public float bulletSpread() {return 1.3f;}

    @Override
    public float piercing() {return 3;}

    @Override
    public float knockback() {return 0.5f;}

    @Override
    public float armorPiercing() {return 0.6f;}

    @Override
    public int maxAmmo() {return 1;}

    @Override
    public int pelletCount() {return 1;}

    @Override
    public ItemStack[] ammoNeeded() {
        return new ItemStack[] {
                new ItemStack(ModItems.RIFLE_BULLET.get()),
                new ItemStack(Items.GUNPOWDER),
                new ItemStack(ModItems.PERCUSSION_CAP.get())
        };
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

            if (modelstate == 1 && hasFired(stack)) {
                playSound(world, player, ModSounds.FLINTLOCK_READY.get());
            } else if (modelstate == 2) {
                playSound(world, player, SoundEvents.IRON_TRAPDOOR_OPEN);
            } else if (modelstate == 4) {
                playSound(world, player, SoundEvents.WOOL_PLACE);
            } else if (modelstate == 5) {
                playSound(world, player, SoundEvents.IRON_TRAPDOOR_CLOSE);
            } else if (modelstate == 7) {
                playSound(world, player, SoundEvents.COPPER_PLACE);
            } else if (modelstate == 8) {
                finalizeReload(world, player, stack);
            }
        }
        setDelay(stack, delay);
    }

    private void finalizeReload(LevelAccessor world, Player player, ItemStack stack) {
        playSound(world, player, ModSounds.FLINTLOCK_READY.get());
        consumeItem(player, Items.GUNPOWDER, 1);
        consumeItem(player, ModItems.RIFLE_BULLET.get(), 1);
        consumeItem(player, ModItems.PERCUSSION_CAP.get(), 1);
        setAmmoCount(stack, 1);
        setFired(stack, false);
        setModelState(stack, 8);
        setDelay(stack, 0);
        setReloading(stack, false);
        player.getCooldowns().addCooldown(stack.getItem(), 5);
    }

    @Override
    public SoundEvent fireSFX() {
        return ModSounds.FLINTLOCK_MUSKET_SHOOT.get();
    }
}
