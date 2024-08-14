package net.normalpersonJava.blackpowderweaponsmod.platform.services;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.normalpersonJava.blackpowderweaponsmod.entity.BulletProjectileEntity;

public interface IPlatformHelper {

    boolean isModLoaded(String modId);
    boolean bulletHitResult(BulletProjectileEntity entity, HitResult result);
    void playSound(SoundEvent sound, SoundSource source, ServerLevel level, Vec3 origin);
    Entity getHitEntity(Entity entity);
}
