package net.normalpersonJava.blackpowderweaponsmod.common;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.entity.BulletProjectileEntity;
import net.normalpersonJava.blackpowderweaponsmod.init.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.init.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.init.ModParticles;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;

public class GunMethods {

    public void fire(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        CompoundTag tag = itemstack.getOrCreateTag();
        if (entity == null) {
            return;
        }
        if(!world.isClientSide()) {
            if (tag.getBoolean("isLoaded")) {
                double speed = 0.1;
                if (entity instanceof Player _player)
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 2);
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ModSounds.FLINTLOCK_MUSKET_SHOOT.get(), SoundSource.PLAYERS, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ModSounds.FLINTLOCK_MUSKET_SHOOT.get(), SoundSource.PLAYERS, 1, 1, false);
                    }
                }
                if (world instanceof ServerLevel projectileLevel) {
                    Projectile _entityToSpawn = new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                            AbstractArrow entityToSpawn = new BulletProjectileEntity(ModEntities.BULLET_PROJECTILE.get(), level);
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.setBaseDamage(damage);
                            entityToSpawn.setKnockback(knockback);
                            entityToSpawn.setSilent(true);
                            entityToSpawn.setPierceLevel(piercing);
                            return entityToSpawn;
                        }
                    }.getArrow(projectileLevel, entity, 4, 1, (byte) 2);
                    _entityToSpawn.setPos((entity.getX() + entity.getLookAngle().x), (entity.getY() + entity.getLookAngle().y + 1.5), (entity.getZ() + entity.getLookAngle().z));
                    _entityToSpawn.shoot((entity.getLookAngle().x), (entity.getLookAngle().y), (entity.getLookAngle().z), 7, 1);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }
                if (world instanceof ServerLevel _level)
                    _level.sendParticles(ModParticles.GUN_SMOKE.get(),
                            (entity.getX() + entity.getLookAngle().x * 1.5),
                            (entity.getY() + entity.getLookAngle().y + 1.5),
                            (entity.getZ() + entity.getLookAngle().z * 1.5), 0,
                            (entity.getLookAngle().x * speed),
                            (entity.getLookAngle().y * speed),
                            (entity.getLookAngle().z * speed), 1);
                if (world instanceof ServerLevel _level)
                    _level.sendParticles(ModParticles.GUN_SMOKE.get(),
                            (entity.getX() + entity.getLookAngle().x * 3),
                            (entity.getY() + entity.getLookAngle().y * 3 + 1.5),
                            (entity.getZ() + entity.getLookAngle().z * 3), 0,
                            (entity.getLookAngle().x * speed),
                            (entity.getLookAngle().y * speed),
                            (entity.getLookAngle().z * speed), 1);

            }
            tag.putBoolean("isLoaded",false);
            tag.putDouble("modelstate", 0);
        }
    }

    public void reload (LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) {
            return;
        }
        if (!world.isClientSide()) {
            if (entity instanceof Player player) {
                boolean hasAmmo = player.getInventory().contains(new ItemStack(ModItems.MUSKETBALL.get())) &&
                        player.getInventory().contains(new ItemStack(Items.GUNPOWDER));

                CompoundTag tag = itemstack.getOrCreateTag();
                double modelstate = tag.getDouble("modelstate");
                double delay = tag.getDouble("delay");

                // Ensure the item is not already loaded and that the player has ammo
                if (!tag.getBoolean("isLoaded") && hasAmmo) {
                    delay++;


                    if (delay >= 10) { // Check if 10 ticks have passed
                        delay = 0; // Reset delay for next tick
                        modelstate++;
                        tag.putDouble("modelstate", modelstate);
                        if (modelstate == 1) {
                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, player.blockPosition(), ModSounds.FLINTLOCK_POWDER.get(), SoundSource.PLAYERS, 1, 1);
                                } else {
                                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), ModSounds.FLINTLOCK_POWDER.get(), SoundSource.PLAYERS, 1, 1, false);
                                }
                            }
                        }
                        if (modelstate == 2) {
                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, player.blockPosition(), ModSounds.FLINTLOCK_READY.get(), SoundSource.PLAYERS, 1, 1);
                                } else {
                                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), ModSounds.FLINTLOCK_READY.get(), SoundSource.PLAYERS, 1, 1, false);
                                }
                            }
                        }
                        if (modelstate == 4 || modelstate == 12) {
                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, player.blockPosition(), ModSounds.FLINTLOCK_ROD0.get(), SoundSource.PLAYERS, 1, 1);
                                } else {
                                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), ModSounds.FLINTLOCK_ROD0.get(), SoundSource.PLAYERS, 1, 1, false);
                                }
                            }
                        }
                        if (modelstate == 6 || modelstate == 8) {
                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, player.blockPosition(), ModSounds.FLINTLOCK_ROD1.get(), SoundSource.PLAYERS, 1, 1);
                                } else {
                                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), ModSounds.FLINTLOCK_ROD1.get(), SoundSource.PLAYERS, 1, 1, false);
                                }
                            }
                        }
                        if (modelstate >= 13) { // If fully loaded
                            tag.putBoolean("isLoaded", true);
                            tag.putDouble("delay", 0); // Reset delay

                            // Consume the ammo
                            consumeItem(player, Items.GUNPOWDER);
                            consumeItem(player, ModItems.MUSKETBALL.get());

                            player.getCooldowns().addCooldown(itemstack.getItem(), 2);

                            // Play the sound
                            if (world instanceof Level level) {
                                if (!level.isClientSide()) {
                                    level.playSound(null, player.blockPosition(), ModSounds.FLINTLOCK_READY.get(), SoundSource.PLAYERS, 1, 1);
                                } else {
                                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), ModSounds.FLINTLOCK_READY.get(), SoundSource.PLAYERS, 1, 1, false);
                                }
                            }
                        }

                        System.out.println("tick");
                    }
                    tag.putDouble("delay", delay);
                }
            }
        }
    }

    public void consumeItem(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == item) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.getInventory().removeItem(stack);
                }
                break;
            }
        }
    }
}
