package net.normalpersonjava.blackpowderweaponsmod;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class Constants {
    public static final String MOD_ID = "blackpowderweaponsmod";
    public static final String MOD_NAME = "Blackpowder Weapons Mod";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);


    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Constants.MOD_ID, "bullet"));
}
