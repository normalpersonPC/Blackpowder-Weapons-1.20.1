package net.normalpersonJava.blackpowderweaponsmod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.entity.BulletProjectileEntity;
import net.normalpersonJava.blackpowderweaponsmod.init.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.init.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.init.ModParticles;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;

public class FlintlockBlunderbussItem extends Item {
    public FlintlockBlunderbussItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1000;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 2d, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> used = super.use(level, player, hand);
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (hand == InteractionHand.OFF_HAND) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 2);
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        if (itemstack.getOrCreateTag().getBoolean("isLoaded")) {
            fire(level, player.getX(), player.getY(), player.getZ(), player, used.getObject());
        }
        return used;
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack itemstack, int useDuration) {
        super.onUseTick(level, entity, itemstack, useDuration);
        reload(level, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
    }


    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }


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
                    for (int a = 0; a < 10; a++) {
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
                    }.getArrow(projectileLevel, entity, 2.5f, 1, (byte) 1);
                    _entityToSpawn.setPos((entity.getX() + entity.getLookAngle().x), (entity.getY() + entity.getLookAngle().y + 1.5), (entity.getZ() + entity.getLookAngle().z));
                    _entityToSpawn.shoot((entity.getLookAngle().x), (entity.getLookAngle().y), (entity.getLookAngle().z), 6, 25);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                    }
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

