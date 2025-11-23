package net.normalpersonjava.blackpowderweaponsmod.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BlackpowderWeaponsMod.MODID);
    public static final RegistryObject<SoundEvent> FLINTLOCK_ROD0 = REGISTRY.register("flintlock_rod0", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_rod0")));
    public static final RegistryObject<SoundEvent> FLINTLOCK_ROD1 = REGISTRY.register("flintlock_rod1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_rod1")));
    public static final RegistryObject<SoundEvent> FLINTLOCK_READY = REGISTRY.register("flintlock_ready", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_ready")));
    public static final RegistryObject<SoundEvent> FLINTLOCK_POWDER = REGISTRY.register("flintlock_powder", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_powder")));
    public static final RegistryObject<SoundEvent> FLINTLOCK_MUSKET_SHOOT = REGISTRY.register("flintlock_musket_shoot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_musket_shoot")));
    public static final RegistryObject<SoundEvent> FLINTLOCK_PISTOL_SHOOT = REGISTRY.register("flintlock_pistol_shoot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "flintlock_pistol_shoot")));
    public static final RegistryObject<SoundEvent> RIFLE_FIRE = REGISTRY.register("rifle_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "rifle_fire")));
    public static final RegistryObject<SoundEvent> RIFLE_FIRE_2 = REGISTRY.register("rifle_fire_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "rifle_fire_2")));
    public static final RegistryObject<SoundEvent> BULLET_HIT = REGISTRY.register("bullet_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "bullet_hit")));
    public static final RegistryObject<SoundEvent> BOLT_0 = REGISTRY.register("bolt_0", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "bolt_0")));
    public static final RegistryObject<SoundEvent> BOLT_1 = REGISTRY.register("bolt_1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "bolt_1")));
    public static final RegistryObject<SoundEvent> BOLT_2 = REGISTRY.register("bolt_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "bolt_2")));

    //music discs
    public static final RegistryObject<SoundEvent> CAT_INFDEV = REGISTRY.register("cat_infdev", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "cat_infdev")));
    public static final RegistryObject<SoundEvent> MY_WAY = REGISTRY.register("my_way", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("blackpowderweaponsmod", "my_way")));
}
