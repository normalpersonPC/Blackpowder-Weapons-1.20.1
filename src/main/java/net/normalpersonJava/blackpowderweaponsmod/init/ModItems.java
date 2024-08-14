package net.normalpersonJava.blackpowderweaponsmod.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.item.FlintlockMusketItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BlackpowderWeaponsMod.MODID);

    //weapons
    public static final RegistryObject<Item> FLINTLOCK_MUSKET = ITEMS.register("flintlock_musket",
            () -> new FlintlockMusketItem());

    public static final RegistryObject<Item> FLINTLOCK_MUSKET_BAYONET = ITEMS.register("flintlock_musket_bayonet",
            () -> new Item(new Item.Properties()));

    //ammo
    public static final RegistryObject<Item> MUSKETBALL = ITEMS.register("musketball",
            () -> new Item(new Item.Properties()));

    //parts

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
