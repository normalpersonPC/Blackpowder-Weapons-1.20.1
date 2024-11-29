
package net.normalpersonJava.blackpowderweaponsmod.item.base;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.normalpersonJava.blackpowderweaponsmod.entity.projectile.BulletEntity;
import net.normalpersonJava.blackpowderweaponsmod.init.ModParticles;

import javax.annotation.Nullable;

public abstract class RevolverItem extends Item {
    public RevolverItem(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", meleeDamage(), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public abstract double meleeDamage();
    public abstract float bulletDamage();
    public abstract float bulletSpeed();
    public abstract float bulletSpread();
    public abstract float piercing();
    public abstract float knockback();
    public abstract float armorPiercing();
    public abstract int maxAmmo();
    public abstract int pelletCount();
    public abstract boolean hasAmmo(Player player);
    public abstract void reload(LevelAccessor world, Entity entity, ItemStack stack);
    public abstract void revolve(LevelAccessor world, Entity entity, ItemStack stack);
    public abstract SoundEvent fireSFX();

    public boolean twoHanded() {
        return true;
    }

    public boolean isFlintlock() {
        return false;
    }

    public boolean canUse(Level level, Entity entity, ItemStack stack) {
        BlockPos blockpos = entity.blockPosition();
        if (stack.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:flintlock")))) {
            return !(level.isRainingAt(blockpos) || level.isRainingAt(BlockPos.containing(blockpos.getX(), entity.getBoundingBox().maxY, blockpos.getZ())) || entity.isUnderWater());
        }
        return !entity.isUnderWater();
    }

    public boolean canUseFrom(LivingEntity entity, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return true;
        }
        if (twoHanded()) {
            return false;
        }
        //pistol in offhand is unusable if two-handed is equipped in main hand
        ItemStack stack = entity.getMainHandItem();
        if (!stack.isEmpty() && stack.getItem() instanceof GunItem gun) {
            return !gun.twoHanded();
        }
        return true;
    }

    @Nullable
    public static InteractionHand getHoldingHand(LivingEntity entity) {
        if (isInHand(entity, InteractionHand.MAIN_HAND)) return InteractionHand.MAIN_HAND;
        if (isInHand(entity, InteractionHand.OFF_HAND)) return InteractionHand.OFF_HAND;
        return null;
    }

    public static boolean isInHand(LivingEntity entity, InteractionHand hand) {
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.isEmpty()) return false;
        if (stack.getItem() instanceof RevolverItem gun) {
            return gun.canUseFrom(entity, hand);
        }
        return false;
    }

    public boolean isHoldingGun(LivingEntity entity) {
        return getHoldingHand(entity) != null;
    }
    public boolean condition(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("condition");
    }
    public void setCondition(ItemStack stack, boolean condition) {
        stack.getOrCreateTag().putBoolean("condition", condition);
    }
    public void setAmmoCount(ItemStack stack, int count) {
        stack.getOrCreateTag().putInt("ammo", count);
    }
    public int getAmmoCount(ItemStack stack) {
        return stack.getOrCreateTag().getInt("ammo");
    }
    public boolean isEmpty(ItemStack stack) {
        return getAmmoCount(stack) == 0;
    }
    public boolean isLoaded(ItemStack stack) {
        return getAmmoCount(stack) > 0 && getAmmoCount(stack) < maxAmmo();
    }
    public boolean fullyLoaded(ItemStack stack) {
        return getAmmoCount(stack) == maxAmmo();
    }
    public void setReloading(ItemStack stack, boolean reloading) {
        stack.getOrCreateTag().putBoolean("isReloading", reloading);
    }
    public boolean isReloading(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("isReloading");
    }
    public void setFired(ItemStack stack, boolean fired) {
        stack.getOrCreateTag().putBoolean("hasfired", fired);
    }
    public boolean hasFired(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("hasfired");
    }
    public void setModelState(ItemStack stack, double state) {
        stack.getOrCreateTag().putDouble("modelstate", state);
    }
    public double getModelState(ItemStack stack) {
        return stack.getOrCreateTag().getDouble("modelstate");
    }
    public void setRevolveState(ItemStack stack, double state) {
        stack.getOrCreateTag().putDouble("revolve", state);
    }
    public double getRevolveState(ItemStack stack) {
        return stack.getOrCreateTag().getDouble("revolve");
    }
    public void setDelay(ItemStack stack, double state) {
        stack.getOrCreateTag().putDouble("delay", state);
    }
    public double getDelay(ItemStack stack) {
        return stack.getOrCreateTag().getDouble("delay");
    }

    public void smokeEffect(LevelAccessor world, LivingEntity entity) {
        ItemStack stack = entity.getMainHandItem();
        double speed = 0.1;
        if (world instanceof ServerLevel level) {
            if (stack.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:pistol")))) {
                level.sendParticles(ModParticles.GUN_SMOKESMALL.get(),
                        (entity.getX() + entity.getLookAngle().x * 1.5),
                        (entity.getY() + entity.getLookAngle().y + 1.5),
                        (entity.getZ() + entity.getLookAngle().z * 1.5), 0,
                        (entity.getLookAngle().x * speed),
                        (entity.getLookAngle().y * speed),
                        (entity.getLookAngle().z * speed), 1);
                level.sendParticles(ModParticles.GUN_SMOKESMALL.get(),
                        (entity.getX() + entity.getLookAngle().x * 3),
                        (entity.getY() + entity.getLookAngle().y * 3 + 1.5),
                        (entity.getZ() + entity.getLookAngle().z * 3), 0,
                        (entity.getLookAngle().x * speed),
                        (entity.getLookAngle().y * speed),
                        (entity.getLookAngle().z * speed), 1);
            } else {
                level.sendParticles(ModParticles.GUN_SMOKE.get(),
                        (entity.getX() + entity.getLookAngle().x * 1.5),
                        (entity.getY() + entity.getLookAngle().y + 1.5),
                        (entity.getZ() + entity.getLookAngle().z * 1.5), 0,
                        (entity.getLookAngle().x * speed),
                        (entity.getLookAngle().y * speed),
                        (entity.getLookAngle().z * speed), 1);
                level.sendParticles(ModParticles.GUN_SMOKE.get(),
                        (entity.getX() + entity.getLookAngle().x * 3),
                        (entity.getY() + entity.getLookAngle().y * 3 + 1.5),
                        (entity.getZ() + entity.getLookAngle().z * 3), 0,
                        (entity.getLookAngle().x * speed),
                        (entity.getLookAngle().y * speed),
                        (entity.getLookAngle().z * speed), 1);
            }
        }
    }

    public void playSound(LevelAccessor world, Entity entity, SoundEvent sound) {
        if (world instanceof Level level) {
            level.playSeededSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 1.5f, 1, 0);
        }
    }

    public void consumeItem(Player player, Item item, int amount) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == item) {
                stack.shrink(amount);
                if (stack.isEmpty()) {
                    player.getInventory().removeItem(stack);
                }
                break;
            }
        }
    }

    public void fire(Level level, LivingEntity entity, ItemStack stack) {
        if (entity == null) {
            return;
        }
        if(!level.isClientSide()) {
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(stack.getItem(), 4);

            if (!hasFired(stack)) {
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < pelletCount(); i++) {
                        BulletEntity bullet = new BulletEntity(serverLevel, entity, bulletDamage(), piercing(), knockback(), armorPiercing());
                        bullet.setPos((entity.getX() + entity.getLookAngle().x), (entity.getY() + entity.getLookAngle().y + 1.3), (entity.getZ() + entity.getLookAngle().z));
                        //bullet.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, bulletSpeed(), bulletSpread());
                        bullet.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, bulletSpeed(), bulletSpread());
                        bullet.setOwner(entity);
                        level.addFreshEntity(bullet);
                    }
                }

                smokeEffect(level, entity);
                playSound(level, entity, fireSFX());
                setDelay(stack, 0);
                setModelState(stack, 0);
                setAmmoCount(stack, getAmmoCount(stack) - 1);
                setFired(stack, true);
                setReloading(stack, false);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotID, boolean selected) {
        super.inventoryTick(stack, level, entity, slotID, selected);
        if (entity instanceof Player player) {
            if (selected) {
                if (isReloading(stack)) {
                    reload(level, player, stack);
                }
                if (isLoaded(stack) && hasFired(stack)) {
                    revolve(level, entity, stack);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> used = super.use(level, player, hand);
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        if (!canUseFrom(player, hand)) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 2);
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        if (!canUse(level, player, itemstack)) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            level.playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, 2f, 0);
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        if (!hasAmmo(player) && !isLoaded(itemstack)) {
            player.getCooldowns().addCooldown(itemstack.getItem(), 10);
            level.playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.5f, 3f, 0);
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        if (isLoaded(itemstack) || fullyLoaded(itemstack)) {
            fire(level, player, used.getObject());
            setReloading(itemstack, false);
        } else {
            level.playSeededSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.5f, 3f, 0);
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        return used;
    }



    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 200;
    }
}
