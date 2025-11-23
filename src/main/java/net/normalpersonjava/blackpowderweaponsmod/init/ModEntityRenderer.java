package net.normalpersonjava.blackpowderweaponsmod.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normalpersonjava.blackpowderweaponsmod.client.model.Modelbullet;
import net.normalpersonjava.blackpowderweaponsmod.client.renderer.BulletEntityRenderer;
import net.normalpersonjava.blackpowderweaponsmod.entity.ModEntities;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderer {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BULLET_ENTITY.get(), BulletEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelbullet.LAYER_LOCATION, Modelbullet::createBodyLayer);
    }
}
