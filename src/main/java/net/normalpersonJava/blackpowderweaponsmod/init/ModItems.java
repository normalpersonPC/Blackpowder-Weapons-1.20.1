package net.normalpersonJava.blackpowderweaponsmod.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.item.FlintlockBlunderbussItem;
import net.normalpersonJava.blackpowderweaponsmod.item.FlintlockMusketBayonetItem;
import net.normalpersonJava.blackpowderweaponsmod.item.FlintlockMusketItem;
import net.normalpersonJava.blackpowderweaponsmod.item.FlintlockPistolItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BlackpowderWeaponsMod.MODID);

    //flintlocks
    public static final RegistryObject<Item> FLINTLOCK_MUSKET = ITEMS.register("flintlock_musket",
            () -> new FlintlockMusketItem());

    public static final RegistryObject<Item> FLINTLOCK_MUSKET_BAYONET = ITEMS.register("flintlock_musket_bayonet",
            () -> new FlintlockMusketBayonetItem());

    public static final RegistryObject<Item> FLINTLOCK_BLUNDERBUSS = ITEMS.register("flintlock_blunderbuss",
            () -> new FlintlockBlunderbussItem());

    public static final RegistryObject<Item> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol",
            () -> new FlintlockPistolItem());

    //caplocks


    //other


    //ammo
    public static final RegistryObject<Item> MUSKETBALL = ITEMS.register("musketball",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUSKETBALL_SMALL = ITEMS.register("musketball_small",
            () -> new Item(new Item.Properties()));

    //parts
    public static final RegistryObject<Item> BARREL_LONG = ITEMS.register("barrel_long",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARREL_SHORT = ITEMS.register("barrel_short",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARREL_MEDIUM = ITEMS.register("barrel_medium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STOCK_PISTOL = ITEMS.register("stock_pistol",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STOCK_LONG = ITEMS.register("stock_long",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLINTLOCK_MECHANISM = ITEMS.register("flintlock_mechanism",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BULLET_MOLD = ITEMS.register("bullet_mold",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOOLKIT = ITEMS.register("toolkit",
            () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(FLINTLOCK_MUSKET.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_MUSKET_BAYONET.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_BLUNDERBUSS.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_PISTOL.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
       });
    }

    public static double renderModelState (ItemStack itemStack) {
        return itemStack.getOrCreateTag().getDouble("modelstate");
    }

}
