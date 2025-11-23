package net.normalpersonjava.blackpowderweaponsmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.normalpersonjava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.minecraft.world.damagesource.DamageType;


public class ModDamageSources {
    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BlackpowderWeaponsMod.MODID, "bullet"));
}