package me.globalastral.the_last_timelord_support.pages;

import java.util.*;

import static me.globalastral.the_last_timelord_support.data_load.LorePagesListener.LORE_PAGES;

public class LoreBookPages {
    public static ArrayList<LorePage> get_unlocked_pages(int lore_level) {
        ArrayList<LorePage> pages = new ArrayList<>();
        for (LorePage page : LORE_PAGES) {
            if (page.level() <= lore_level) {
                pages.add(page);
            }
        }
        return pages;
    }
}
