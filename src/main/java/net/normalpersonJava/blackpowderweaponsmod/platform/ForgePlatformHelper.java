package net.normalpersonJava.blackpowderweaponsmod.platform;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fml.ModList;
import net.normalpersonJava.blackpowderweaponsmod.entity.projectile.BulletEntity;
import net.normalpersonJava.blackpowderweaponsmod.network.Network;
import net.normalpersonJava.blackpowderweaponsmod.platform.services.IPlatformHelper;

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
        Network.S2CSound(sound, source, level.dimension(), origin);
    }

    @Override
    public Entity getHitEntity(Entity entity) {
        return entity instanceof PartEntity<?> part ? part.getParent() : entity;
    }
}