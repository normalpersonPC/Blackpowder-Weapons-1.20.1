package net.normalpersonJava.blackpowderweaponsmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.normalpersonJava.blackpowderweaponsmod.client.handler.ClientEventHandler;
import net.normalpersonJava.blackpowderweaponsmod.common.CommonEventHandler;
import net.normalpersonJava.blackpowderweaponsmod.entity.ModEntities;
import net.normalpersonJava.blackpowderweaponsmod.init.*;
import net.normalpersonJava.blackpowderweaponsmod.init.ModLootModifiers;
import net.normalpersonJava.blackpowderweaponsmod.item.ModItems;
import net.normalpersonJava.blackpowderweaponsmod.network.Network;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlackpowderWeaponsMod.MODID)
public class BlackpowderWeaponsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "blackpowderweaponsmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public BlackpowderWeaponsMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //event bus registries
        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.ENITITY_TYPES.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.REGISTRY.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        new CommonEventHandler(modEventBus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new ClientEventHandler(modEventBus));

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(
                Network::register

        );

    }

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            workQueue.removeAll(actions);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
