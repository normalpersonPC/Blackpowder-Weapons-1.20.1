package net.normalpersonjava.blackpowderweaponsmod.platform;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fml.ModList;
import net.normalpersonjava.blackpowderweaponsmod.entity.projectile.BulletEntity;
import net.normalpersonjava.blackpowderweaponsmod.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean bulletHitResult(BulletEntity bullet, HitResult result) {
        return net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(bullet, result);
    }

    @Override
    public void playSound(SoundEvent sound, SoundSource source, ServerLevel level, Vec3 origin) {

    }

    @Override
    public Entity getHitEntity(Entity entity) {
        return entity instanceof PartEntity<?> part ? part.getParent() : entity;
    }
}