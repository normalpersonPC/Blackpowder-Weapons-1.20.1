package net.normalpersonjava.blackpowderweaponsmod.platform.services;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.normalpersonjava.blackpowderweaponsmod.entity.projectile.BulletEntity;

public interface IPlatformHelper {

    boolean isModLoaded(String modId);
    boolean bulletHitResult(BulletEntity entity, HitResult result);
    void playSound(SoundEvent sound, SoundSource source, ServerLevel level, Vec3 origin);
    Entity getHitEntity(Entity entity);
}
