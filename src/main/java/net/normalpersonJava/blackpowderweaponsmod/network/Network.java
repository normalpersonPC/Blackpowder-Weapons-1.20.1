package net.normalpersonJava.blackpowderweaponsmod.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.normalpersonJava.blackpowderweaponsmod.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.normalpersonJava.blackpowderweaponsmod.network.packet.ReloadPacket;

public class Network {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;

    private static int id() {
        return packetID++;
    }

    public static void register() {

        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Constants.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(string -> true)
                .serverAcceptedVersions(string -> true)
                .simpleChannel();

        INSTANCE = network;

        network.registerMessage(0, ReloadPacket.class, ReloadPacket::encode, ReloadPacket::decode, ReloadPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
