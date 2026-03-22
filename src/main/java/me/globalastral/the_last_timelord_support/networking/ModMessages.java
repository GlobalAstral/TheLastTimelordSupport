package me.globalastral.the_last_timelord_support.networking;

import me.globalastral.the_last_timelord_support.TheLastTimelordSupport;
import me.globalastral.the_last_timelord_support.networking.packets.LoreBookUseS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packet_id = 0;
    private static int id() {
        return packet_id++;
    }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(TheLastTimelordSupport.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();


        net.messageBuilder(LoreBookUseS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LoreBookUseS2CPacket::new)
                .encoder(LoreBookUseS2CPacket::toBytes)
                .consumerMainThread(LoreBookUseS2CPacket::handle)
                .add();

        INSTANCE = net;
    }

    public static <PACKET> void send_to_server(PACKET packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PACKET> void sendToClient(PACKET packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
