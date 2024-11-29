package net.normalpersonJava.blackpowderweaponsmod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.normalpersonJava.blackpowderweaponsmod.item.base.GunItem;
import net.normalpersonJava.blackpowderweaponsmod.item.base.RevolverItem;
import net.normalpersonJava.blackpowderweaponsmod.item.base.RevolverItemOld;

import java.util.Objects;
import java.util.function.Supplier;

public class ReloadPacket {
    public ReloadPacket() {

    }

    public static void encode(ReloadPacket msg, FriendlyByteBuf buf) {
        // No data to send
    }

    public static ReloadPacket decode(FriendlyByteBuf buf) {
        return new ReloadPacket();
    }

    public static void handle(ReloadPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            assert player != null;
            ItemStack heldItem = Objects.requireNonNull(player.getMainHandItem());
            //revolver reload
            if (heldItem.getItem() instanceof RevolverItem revolverItem) {
                if (revolverItem.isEmpty(heldItem) && revolverItem.hasAmmo(player) && !revolverItem.isReloading(heldItem)) {
                    revolverItem.setReloading(heldItem, true);
                } else if (revolverItem.isLoaded(heldItem) && revolverItem.hasAmmo(player) && !revolverItem.isReloading(heldItem)) {
                    revolverItem.setReloading(heldItem, true);
                    revolverItem.setModelState(heldItem, 0);
                }
            }
            //single shot reload
            if (heldItem.getItem() instanceof GunItem gunItem) {
                if (gunItem.isEmpty(heldItem) && gunItem.hasAmmo(player) && !gunItem.isReloading(heldItem)) {
                    gunItem.setReloading(heldItem, true);
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}

