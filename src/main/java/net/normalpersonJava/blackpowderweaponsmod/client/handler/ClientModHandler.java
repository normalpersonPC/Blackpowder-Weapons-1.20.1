package net.normalpersonJava.blackpowderweaponsmod.client.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.client.KeyBindings;

@Mod.EventBusSubscriber(modid = BlackpowderWeaponsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.INSTANCE.reload);
    }
}
