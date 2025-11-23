package net.normalpersonjava.blackpowderweaponsmod.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonjava.blackpowderweaponsmod.client.particle.GunSmokeParticle;
import net.normalpersonjava.blackpowderweaponsmod.client.particle.GunSmokeSmallParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BlackpowderWeaponsMod.MODID);

    public static final RegistryObject<SimpleParticleType> GUN_SMOKE = PARTICLE_TYPES.register("gun_smoke",
            () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GUN_SMOKESMALL = PARTICLE_TYPES.register("gun_smokesmall",
            () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GUN_SMOKE.get(), GunSmokeParticle::provider);
        event.registerSpriteSet(GUN_SMOKESMALL.get(), GunSmokeSmallParticle::provider);
    }
}
