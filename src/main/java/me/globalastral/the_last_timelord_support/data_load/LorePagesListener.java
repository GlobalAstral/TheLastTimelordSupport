package me.globalastral.the_last_timelord_support.data_load;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.globalastral.the_last_timelord_support.pages.LorePage;
import me.globalastral.the_last_timelord_support.pages.PageBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LorePagesListener extends SimpleJsonResourceReloadListener {

    public static final List<LorePage> LORE_PAGES = new ArrayList<>();
    private static final Gson GSON = new Gson();

    public LorePagesListener() {
        super(GSON, "lore_pages");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        LORE_PAGES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
            JsonObject obj = entry.getValue().getAsJsonObject();
            if (!obj.has("pages") || !obj.get("pages").isJsonArray())
                continue;
            obj.getAsJsonArray("pages").forEach(page -> {
                if (!page.isJsonObject())
                    return;
                JsonObject pageObj = page.getAsJsonObject();
                if (!pageObj.has("content") || !pageObj.get("content").isJsonArray())
                    return;

                ArrayList<String> lines = new ArrayList<>();

                pageObj.getAsJsonArray("content").forEach(line -> lines.add(line.getAsString()));
                if (!pageObj.has("level") || !pageObj.get("level").isJsonPrimitive())
                    return;
                int level = pageObj.get("level").getAsInt();
                PageBuilder builder = PageBuilder.create();
                if (pageObj.has("title") && pageObj.get("title").isJsonPrimitive()) {
                    builder.set_title(pageObj.get("title").getAsString());
                }
                lines.forEach(builder::add_line);
                builder.set_level(level);
                LORE_PAGES.add(builder.end());
            });
        }

    }
}
