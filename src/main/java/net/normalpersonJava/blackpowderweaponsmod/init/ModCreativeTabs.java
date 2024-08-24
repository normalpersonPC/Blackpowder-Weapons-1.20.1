package net.normalpersonJava.blackpowderweaponsmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlackpowderWeaponsMod.MODID);

    public static final RegistryObject<CreativeModeTab> BLACKPOWDER_WEAPONS = CREATIVE_MODE_TABS.register("blackpowder_weapons",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FLINTLOCK_MUSKET.get()))
                    .title(Component.translatable("creativetab.blackpowder_weapons"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FLINTLOCK_MUSKET.get());
                        output.accept(ModItems.FLINTLOCK_MUSKET_BAYONET.get());
                        output.accept(ModItems.FLINTLOCK_PISTOL.get());
                        output.accept(ModItems.MUSKETBALL.get());
                        output.accept(ModItems.MUSKETBALL_SMALL.get());
                        output.accept(ModItems.BARREL_SHORT.get());
                        output.accept(ModItems.BARREL_MEDIUM.get());
                        output.accept(ModItems.BARREL_LONG.get());
                        output.accept(ModItems.STOCK_PISTOL.get());
                        output.accept(ModItems.STOCK_LONG.get());
                        output.accept(ModItems.FLINTLOCK_MECHANISM.get());
                        output.accept(ModItems.TOOLKIT.get());
                        output.accept(ModItems.BULLET_MOLD.get());


                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }



}
