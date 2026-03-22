package me.globalastral.the_last_timelord_support.data_load;

import me.globalastral.the_last_timelord_support.TheLastTimelordSupport;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheLastTimelordSupport.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class LorePageReloadListener {
    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new LorePagesListener());
    }

}
