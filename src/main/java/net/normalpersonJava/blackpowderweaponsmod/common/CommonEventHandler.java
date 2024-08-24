package net.normalpersonJava.blackpowderweaponsmod.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;

@Mod.EventBusSubscriber(modid = BlackpowderWeaponsMod.MODID)
public class CommonEventHandler {
    public CommonEventHandler(IEventBus bus) {
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickItem);
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack mainHandItem = event.getEntity().getMainHandItem();
        ItemStack offHandItem = event.getEntity().getOffhandItem();

        if (offHandItem.is(ItemTags.create(new ResourceLocation("blackpowderweaponsmod:longgun")))) {
            event.setCanceled(true);
        }
    }
}
