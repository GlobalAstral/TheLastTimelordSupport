package me.globalastral.the_last_timelord_support.data_load;

import me.globalastral.the_last_timelord_support.LoreBookItem;
import me.globalastral.the_last_timelord_support.TheLastTimelordSupport;
import me.globalastral.the_last_timelord_support.commands.IncrementLoreLevelCommand;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = TheLastTimelordSupport.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ModEvents {
    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new LorePagesListener());
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        IncrementLoreLevelCommand.register(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.getOriginal().level().isClientSide()) {
            CompoundTag new_one = event.getEntity().getPersistentData();
            CompoundTag old = event.getOriginal().getPersistentData();
            if (old.contains(LoreBookItem.LORE_LEVEL_KEY))
                new_one.putInt(LoreBookItem.LORE_LEVEL_KEY, old.getInt(LoreBookItem.LORE_LEVEL_KEY));
            else
                new_one.putInt(LoreBookItem.LORE_LEVEL_KEY, 0);
        }
    }

}
