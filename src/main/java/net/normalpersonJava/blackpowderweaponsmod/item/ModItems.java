package net.normalpersonJava.blackpowderweaponsmod.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.init.ModSounds;
import net.normalpersonJava.blackpowderweaponsmod.item.tools.BlueprintItem;
import net.normalpersonJava.blackpowderweaponsmod.item.tools.ToolkitItem;
import net.normalpersonJava.blackpowderweaponsmod.item.weapons.*;
import net.normalpersonJava.blackpowderweaponsmod.item.tools.BulletMoldItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BlackpowderWeaponsMod.MODID);

    //flintlocks
    public static final RegistryObject<Item> FLINTLOCK_MUSKET = ITEMS.register("flintlock_musket",
            () -> new FlintlockMusketItem());

    public static final RegistryObject<Item> FLINTLOCK_MUSKET_BAYONET = ITEMS.register("flintlock_musket_bayonet",
            () -> new FlintlockMusketItem(){
                @Override
                public double meleeDamage() {
                    return 5;
                }
                @Override
                public double meleeRange() {
                    return 4.0;
                }

            });

    public static final RegistryObject<Item> FLINTLOCK_MUSKET_BAYONET_DIAMOND = ITEMS.register("flintlock_musket_bayonet_diamond",
            () -> new FlintlockMusketItem(){
                @Override
                public double meleeDamage() {
                    return 6;
                }
                @Override
                public double meleeRange() {
                    return 4.0;
                }
            });

    public static final RegistryObject<Item> FLINTLOCK_BLUNDERBUSS = ITEMS.register("flintlock_blunderbuss",
            () -> new FlintlockBlunderbussItem());

    public static final RegistryObject<Item> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol",
            () -> new FlintlockPistolItem());

    public static final RegistryObject<Item> FLINTLOCK_BREECHLOADING_RIFLE = ITEMS.register("flintlock_breechloading_rifle",
            () -> new FlintlockBreechloadingRifleItem());

    //caplocks
    public static final RegistryObject<Item> CAPLOCK_MUSKET = ITEMS.register("caplock_musket",
            () -> new CaplockMusketItem());

    public static final RegistryObject<Item> CAPLOCK_BLUNDERBUSS = ITEMS.register("caplock_blunderbuss",
            () -> new CaplockBlunderbussItem());

    public static final RegistryObject<Item> CAPLOCK_BREECHLOADING_RIFLE = ITEMS.register("caplock_breechloading_rifle",
            () -> new CaplockBreechloadingRifleItem());

    public static final RegistryObject<Item> CAPLOCK_PISTOL = ITEMS.register("caplock_pistol",
            () -> new CaplockPistolItem());

    public static final RegistryObject<Item> CAPLOCK_REVOLVER = ITEMS.register("caplock_revolver",
            () -> new CaplockRevolverItem());

    //bolt rifles
    public static final RegistryObject<Item> NEEDLEFIRE_RIFLE = ITEMS.register("needlefire_rifle",
            () -> new NeedlefireRifleItem());


    //ammo
    public static final RegistryObject<Item> MUSKETBALL = ITEMS.register("musketball",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUSKETBALL_SMALL = ITEMS.register("musketball_small",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RIFLE_BULLET = ITEMS.register("rifle_bullet",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PISTOL_BULLET = ITEMS.register("pistol_bullet",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHOTGUN_PELLETS = ITEMS.register("shotgun_pellets",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PERCUSSION_CAP = ITEMS.register("percussion_cap",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PAPER_CARTRIDGE_RIFLE = ITEMS.register("paper_cartridge_rifle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PAPER_CARTRIDGE_PISTOL = ITEMS.register("paper_cartridge_pistol",
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

    public static final RegistryObject<Item> CAPLOCK_MECHANISM = ITEMS.register("caplock_mechanism",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NEEDLEFIRE_BOLT = ITEMS.register("needlefire_bolt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BREECHBLOCK = ITEMS.register("breechblock",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REVOLVER_CYLINDER = ITEMS.register("revolver_cylinder",
            () -> new Item(new Item.Properties()));


    //tools
    public static final RegistryObject<Item> BULLET_MOLD = ITEMS.register("bullet_mold",
            () -> new BulletMoldItem());

    public static final RegistryObject<Item> TOOLKIT = ITEMS.register("toolkit",
            () -> new ToolkitItem());

    public static final RegistryObject<Item> TOOLKIT_CONVERSION = ITEMS.register("toolkit_conversion",
            () -> new ToolkitItem());

    //blueprints
    public static final RegistryObject<Item> BLUEPRINT_BREECHBLOCK = ITEMS.register("blueprint_breechblock",
            () -> new BlueprintItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> BLUEPRINT_CAPLOCK_MECHANISM = ITEMS.register("blueprint_caplock_mechanism",
            () -> new BlueprintItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BLUEPRINT_NEEDLEFIRE_BOLT = ITEMS.register("blueprint_needlefire_bolt",
            () -> new BlueprintItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));


    //music disc
    public static final RegistryObject<Item> DISC_CAT_INFDEV = ITEMS.register("disc_cat_infdev",
            () -> new RecordItem(6, ModSounds.CAT_INFDEV, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 5240));
    public static final RegistryObject<Item> DISC_MY_WAY = ITEMS.register("disc_my_way",
            () -> new RecordItem(6, ModSounds.MY_WAY, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 6240));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(FLINTLOCK_MUSKET.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_MUSKET_BAYONET.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_MUSKET_BAYONET_DIAMOND.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_BLUNDERBUSS.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_PISTOL.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(FLINTLOCK_BREECHLOADING_RIFLE.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));

            ItemProperties.register(CAPLOCK_MUSKET.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(CAPLOCK_MUSKET.get(), new ResourceLocation("blackpowderweaponsmod:hasfired"), (itemStackToRender, clientWorld, entity, itemEntityId) -> hasFired(itemStackToRender) ? 1.0f : 0.0f);
            ItemProperties.register(CAPLOCK_BLUNDERBUSS.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(CAPLOCK_BLUNDERBUSS.get(), new ResourceLocation("blackpowderweaponsmod:hasfired"), (itemStackToRender, clientWorld, entity, itemEntityId) -> hasFired(itemStackToRender) ? 1.0f : 0.0f);
            ItemProperties.register(CAPLOCK_PISTOL.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(CAPLOCK_PISTOL.get(), new ResourceLocation("blackpowderweaponsmod:hasfired"), (itemStackToRender, clientWorld, entity, itemEntityId) -> hasFired(itemStackToRender) ? 1.0f : 0.0f);
            ItemProperties.register(CAPLOCK_REVOLVER.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(CAPLOCK_REVOLVER.get(), new ResourceLocation("blackpowderweaponsmod:revolve"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) revolve(itemStackToRender));
            ItemProperties.register(CAPLOCK_REVOLVER.get(), new ResourceLocation("blackpowderweaponsmod:hasfired"), (itemStackToRender, clientWorld, entity, itemEntityId) -> hasFired(itemStackToRender) ? 1.0f : 0.0f);
            ItemProperties.register(CAPLOCK_BREECHLOADING_RIFLE.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
            ItemProperties.register(CAPLOCK_BREECHLOADING_RIFLE.get(), new ResourceLocation("blackpowderweaponsmod:hasfired"), (itemStackToRender, clientWorld, entity, itemEntityId) -> hasFired(itemStackToRender) ? 1.0f : 0.0f);

            ItemProperties.register(NEEDLEFIRE_RIFLE.get(), new ResourceLocation("blackpowderweaponsmod:modelstate"), (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) renderModelState(itemStackToRender));
        });
    }

    public static double renderModelState (ItemStack itemStack) {
        return itemStack.getOrCreateTag().getDouble("modelstate");
    }

    public static boolean hasFired (ItemStack itemStack) {
        return itemStack.getOrCreateTag().getBoolean("hasfired");
    }

    public static double revolve (ItemStack itemStack) {
        return itemStack.getOrCreateTag().getDouble("revolve");
    }

    public static double ammo (ItemStack itemStack) {
        return itemStack.getOrCreateTag().getDouble("ammo");
    }

}
