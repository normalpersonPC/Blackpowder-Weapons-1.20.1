package net.normalpersonJava.blackpowderweaponsmod.item.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    public float bulletDamage() {return 24;}

    @Override
    public float bulletSpeed() {return 9;}

    @Override
    public float bulletSpread() {return 1.2f;}

    @Override
    public float piercing() {return 3;}

    @Override
    public float knockback() {return 1;}

    @Override
    public int maxAmmo() {return 1;}

    @Override
    public int pelletCount() {return 1;}

    @Override
    public boolean hasAmmo(Player player) {
        return player.getInventory().contains(new ItemStack(ModItems.PAPER_CARTRIDGE_RIFLE.get()));
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
                    if (delay >= 10) { // Check if 10 ticks have passed
                        delay = 0; // Reset delay for next tick
                        modelstate++;
                        setModelState(stack, modelstate);
                        //reload
                        if (modelstate == 1) {
                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_OPEN);
                        }
                        if (modelstate == 2) {
                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_OPEN);
                        }
                        if (modelstate == 4) {
                            playSound(world, player, SoundEvents.WOOL_PLACE);
                        }
                        if (modelstate == 5) {
                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_CLOSE);
                        }
                        if (modelstate >= 6) {
                            setModelState(stack, 6);// If fully loaded
                            setAmmoCount(stack, getAmmoCount(stack) + 1);
                            setDelay(stack, 0); // Reset delay
                            setLoaded(stack, true);
                            setFired(stack, false);

                            consumeItem(player, ModItems.PAPER_CARTRIDGE_RIFLE.get(), 1);

                            player.getCooldowns().addCooldown(stack.getItem(), 4);

                            playSound(world, player, SoundEvents.IRON_TRAPDOOR_CLOSE);
                        }
                    }
                    setDelay(stack, delay);
                }
            }
        }
    }
    @Override
    public SoundEvent fireSFX() {
        return ModSounds.RIFLE_FIRE.get();
    }
}
