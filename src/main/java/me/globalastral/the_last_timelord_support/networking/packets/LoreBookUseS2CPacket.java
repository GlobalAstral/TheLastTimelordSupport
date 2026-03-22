package me.globalastral.the_last_timelord_support.networking.packets;

import me.globalastral.the_last_timelord_support.LoreBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LoreBookUseS2CPacket {

    private final int lore_level;

    public LoreBookUseS2CPacket(int lore_level) {
        this.lore_level = lore_level;
    }

    public LoreBookUseS2CPacket(FriendlyByteBuf buf) {
        this.lore_level = buf.getInt(0);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.lore_level);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT
            Minecraft.getInstance().setScreen(new LoreBookScreen(this.lore_level));
        });
        return true;
    }
}
