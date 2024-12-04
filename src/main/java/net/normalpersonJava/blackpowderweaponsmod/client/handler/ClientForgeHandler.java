package net.normalpersonJava.blackpowderweaponsmod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normalpersonJava.blackpowderweaponsmod.BlackpowderWeaponsMod;
import net.normalpersonJava.blackpowderweaponsmod.client.KeyBindings;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;
import net.normalpersonJava.blackpowderweaponsmod.network.Network;
import net.normalpersonJava.blackpowderweaponsmod.network.packet.ReloadPacket;

@Mod.EventBusSubscriber(modid = BlackpowderWeaponsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {

    //keybinds
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (KeyBindings.INSTANCE.reload.consumeClick() && minecraft.player != null) {
            reloadHandler();
        }
    }

    public static void reloadHandler() {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        ItemStack heldItem = player.getMainHandItem();

        //single shot reload
        if (heldItem.getItem() instanceof GunItem gunItem) {
            if (!gunItem.fullyLoaded(heldItem) && gunItem.hasAmmo(player) && !gunItem.isReloading(heldItem)) {
                Network.sendToServer(new ReloadPacket());
                player.displayClientMessage(Component.literal("Reloading!"), true);
            } else if (!gunItem.hasAmmo(player)) {
                player.displayClientMessage(Component.literal("No Ammo!"), true);
            } else if (gunItem.fullyLoaded(heldItem)) {
                player.displayClientMessage(Component.literal("Already Fully Loaded!"), true);
            } else if (gunItem.isReloading(heldItem)) {
                player.displayClientMessage(Component.literal("Already Reloading!"), true);
            }
        }

    }
}
