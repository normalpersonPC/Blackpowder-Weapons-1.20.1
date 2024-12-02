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

public class NeedlefireRifleItem extends GunItem {
    public NeedlefireRifleItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public double meleeDamage() {return 2;}

    @Override
    public float bulletDamage() {return 22;}

    @Override
    public float bulletSpeed() {return 9;}

    @Override
    public float bulletSpread() {return 1.15f;}

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
        return new ItemStack[]{
                new ItemStack(ModItems.PAPER_CARTRIDGE_RIFLE.get())
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
            //reload
            if (modelstate == 1) {
                playSound(world, player, ModSounds.BOLT_0.get());
            } else if (modelstate == 2) {
                playSound(world, player, ModSounds.BOLT_1.get());
            } else if (modelstate == 4) {
                playSound(world, player, SoundEvents.WOOL_PLACE);
            } else if (modelstate == 5) {
                playSound(world, player,  ModSounds.BOLT_2.get());
            } else if (modelstate == 6) {
                finalizeReload(world, player, stack);
            }
        }
        setDelay(stack, delay);
    }

    private void finalizeReload(LevelAccessor world, Player player, ItemStack stack) {
        playSound(world, player, ModSounds.BOLT_0.get());
        consumeItem(player, ModItems.PAPER_CARTRIDGE_RIFLE.get(), 1);
        setFired(stack, false);
        setModelState(stack, 6);
        setAmmoCount(stack, 1);
        setDelay(stack, 0);
        setReloading(stack, false);
        player.getCooldowns().addCooldown(stack.getItem(), 5);
    }

    @Override
    public SoundEvent fireSFX() {
        return ModSounds.RIFLE_FIRE_2.get();
    }
}
