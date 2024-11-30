package net.normalpersonJava.blackpowderweaponsmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.item.ModItems;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlackpowderWeaponsMod.MODID);

    public static final RegistryObject<CreativeModeTab> BLACKPOWDER_WEAPONS = CREATIVE_MODE_TABS.register("blackpowder_weapons",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FLINTLOCK_MUSKET.get()))
                    .title(Component.translatable("creativetab.blackpowder_weapons"))
                    .displayItems((itemDisplayParameters, output) -> {

                        //flintlocks
                        output.accept(ModItems.FLINTLOCK_MUSKET.get());
                        output.accept(ModItems.FLINTLOCK_MUSKET_BAYONET.get());
                        output.accept(ModItems.FLINTLOCK_MUSKET_BAYONET_DIAMOND.get());
                        output.accept(ModItems.FLINTLOCK_BLUNDERBUSS.get());
                        output.accept(ModItems.FLINTLOCK_PISTOL.get());
                        output.accept(ModItems.FLINTLOCK_BREECHLOADING_RIFLE.get());

                        //caplocks
                        output.accept(ModItems.CAPLOCK_MUSKET.get());
                        output.accept(ModItems.CAPLOCK_PISTOL.get());
                        output.accept(ModItems.CAPLOCK_BREECHLOADING_RIFLE.get());
                        output.accept(ModItems.CAPLOCK_BLUNDERBUSS.get());
                        output.accept(ModItems.CAPLOCK_REVOLVER.get());

                        //other
                        output.accept(ModItems.NEEDLEFIRE_RIFLE.get());

                        //ammo
                        output.accept(ModItems.MUSKETBALL.get());
                        output.accept(ModItems.MUSKETBALL_SMALL.get());
                        output.accept(ModItems.SHOTGUN_PELLETS.get());
                        output.accept(ModItems.RIFLE_BULLET.get());
                        output.accept(ModItems.PISTOL_BULLET.get());
                        output.accept(ModItems.PERCUSSION_CAP.get());
                        output.accept(ModItems.PAPER_CARTRIDGE_RIFLE.get());
                        output.accept(ModItems.PAPER_CARTRIDGE_PISTOL.get());

                        //parts
                        output.accept(ModItems.BARREL_SHORT.get());
                        output.accept(ModItems.BARREL_MEDIUM.get());
                        output.accept(ModItems.BARREL_LONG.get());
                        output.accept(ModItems.STOCK_PISTOL.get());
                        output.accept(ModItems.STOCK_LONG.get());
                        output.accept(ModItems.FLINTLOCK_MECHANISM.get());
                        output.accept(ModItems.CAPLOCK_MECHANISM.get());
                        output.accept(ModItems.NEEDLEFIRE_BOLT.get());
                        output.accept(ModItems.BREECHBLOCK.get());
                        output.accept(ModItems.REVOLVER_CYLINDER.get());

                        //blueprints
                        output.accept(ModItems.BLUEPRINT_BREECHBLOCK.get());
                        output.accept(ModItems.BLUEPRINT_CAPLOCK_MECHANISM.get());
                        output.accept(ModItems.BLUEPRINT_NEEDLEFIRE_BOLT.get());

                        //tools
                        output.accept(ModItems.TOOLKIT.get());
                        output.accept(ModItems.TOOLKIT_CONVERSION.get());
                        output.accept(ModItems.BULLET_MOLD.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }



}
