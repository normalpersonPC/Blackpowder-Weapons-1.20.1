package net.normalpersonJava.blackpowderweaponsmod.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.entity.projectile.BulletEntity;
import net.normalpersonJava.blackpowderweaponsmod.entity.projectile.BulletProjectileEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENITITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BlackpowderWeaponsMod.MODID);

    public static final RegistryObject<EntityType<BulletProjectileEntity>> BULLET_PROJECTILE = register("bullet_projectile", EntityType.Builder.<BulletProjectileEntity>of(BulletProjectileEntity::new, MobCategory.MISC)
            .setCustomClientFactory(BulletProjectileEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.8f, 0.8f));

    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY = register("bullet_entity", EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
        .setCustomClientFactory(BulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(1).sized(0.4f, 0.4f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return ENITITY_TYPES.register(registryname, () -> entityTypeBuilder.build(registryname));
    }
}
