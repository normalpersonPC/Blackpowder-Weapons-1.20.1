package net.normalpersonJava.blackpowderweaponsmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;

import net.normalpersonJava.blackpowderweaponsmod.entity.BulletProjectileEntity;
import net.normalpersonJava.blackpowderweaponsmod.init.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.init.ModParticles;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;

import java.util.Objects;

public class FlintlockMusketItem extends Item {
    public FlintlockMusketItem() {
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
        ItemStack itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (itemStack.getOrCreateTag().getBoolean("isLoaded")) {
            fire(level, player.getX(), player.getY(), player.getZ(), player, used.getObject());
        } else {
            reload(level, player.getX(), player.getY(), player.getZ(), player, used.getObject());
        }
        return used;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);

    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    public void fire(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null) {
            return;
        }
        if(!world.isClientSide()) {
            if (itemstack.getOrCreateTag().getBoolean("isLoaded")) {
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

                itemstack.getOrCreateTag().putBoolean("isLoaded",false);
                itemstack.getOrCreateTag().putDouble("state", 0);
            }
        }
    }

    public void reload (LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (!itemstack.getOrCreateTag().getBoolean("isLoaded")) {
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 10);

            itemstack.getOrCreateTag().putBoolean("isLoaded",true);

        }

    }

}

