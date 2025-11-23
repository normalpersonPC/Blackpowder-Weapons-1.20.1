package net.normalpersonjava.blackpowderweaponsmod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.normalpersonjava.blackpowderweaponsmod.item.base.GunItem;
import net.normalpersonjava.blackpowderweaponsmod.item.base.RevolverItem;

import java.util.Objects;
import java.util.function.Supplier;

public class ReloadPacket {
    public ReloadPacket() {

    }

    public ReloadPacket(FriendlyByteBuf buf) {
        // No data to send
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            assert player != null;
            ServerLevel level = player.serverLevel();

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
        return true;
    }
}

