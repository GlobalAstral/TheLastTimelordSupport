package me.globalastral.the_last_timelord_support.pages;

import me.globalastral.the_last_timelord_support.utils.Range;

import java.util.*;

public class LoreBookPages {
    private static final List<LorePage> LORE_PAGES = List.of(
            PageBuilder.create()
                    .set_title("Wreckage")
                    .add_line("Day - Unknown.")
                    .add_line("Universe - Unknown.")
                    .add_line("Vortex State - Unknown.")
                    .add_line()
                    .add_line("The Timelords are")
                    .add_line("losing and Gallifrey is")
                    .add_line("bound to fall.")
                    .add_line("I heard about a")
                    .add_line("Timelord that shall")
                    .add_line("soon use The Moment")
                    .add_line("to let the galaxy be")
                    .add_line("devoured.")
                    .end(),

            PageBuilder.create()
                    .add_line("Due to that, attempting")
                    .add_line("to escape the war,")
                    .add_line("I jumped in the Time")
                    .add_line("Vortex as a last")
                    .add_line("resort to save myself.")
                    .add_line("I ended up in this")
                    .add_line("weird universe, and")
                    .add_line("now I have a way to")
                    .add_line("save Gallifrey. Now I")
                    .add_line("need to get back to")
                    .add_line("my universe and wipe")
                    .add_line("the daleks out of")
                    .add_line("existence.")
                    .end()
    );

    private static final HashMap<Integer, Range<Integer>> UNLOCK_MAP = new HashMap<>(Map.of(
            0, new Range<>(0, 1)
    ));

    private static Range<Integer> get_safe_range(int i) {
        Range<Integer> range = UNLOCK_MAP.get(i);
        if (range == null) {
            return UNLOCK_MAP.get(0);
        }
        return range;
    }

    public static LinkedHashSet<LorePage> get_unlocked_pages(int lore_level) {
        LinkedHashSet<LorePage> pages = new LinkedHashSet<>();
        for (int i = 0; i <= lore_level; i++) {
            Range<Integer> range = get_safe_range(i);
            List<LorePage> collection = LORE_PAGES.subList(range.start(), range.end() + 1);
            pages.addAll(collection);
        }
        return pages;
    }
}
