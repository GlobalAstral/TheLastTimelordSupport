package me.globalastral.the_last_timelord_support;

import me.globalastral.the_last_timelord_support.networking.packets.LoreBookUseS2CPacket;
import me.globalastral.the_last_timelord_support.networking.ModMessages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LoreBookItem extends Item {
    public LoreBookItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pLevel.isClientSide()) {
            return InteractionResultHolder.fail(stack);
        }

        CompoundTag nbt = pPlayer.getPersistentData();

        if (!nbt.contains(TheLastTimelordSupport.MODID + ":lore_level")) {
            nbt.putInt(TheLastTimelordSupport.MODID + ":lore_level", 0);
        }

        int lore_level = nbt.getInt(TheLastTimelordSupport.MODID + ":lore_level");

        ModMessages.sendToClient(new LoreBookUseS2CPacket(lore_level), (ServerPlayer) pPlayer);

        return InteractionResultHolder.success(stack);
    }
}
