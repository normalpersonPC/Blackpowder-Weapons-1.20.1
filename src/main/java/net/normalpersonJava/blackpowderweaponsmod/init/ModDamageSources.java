package net.normalpersonJava.blackpowderweaponsmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.registries.RegisterEvent;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModDamageSources {
    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BlackpowderWeaponsMod.MODID, "bullet"));
}