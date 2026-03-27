package me.globalastral.the_last_timelord_support.pages;

import java.util.ArrayList;

public class PageBuilder {
    private String title = "";
    private ArrayList<String> lines = new ArrayList<>();
    private int level = 0;

    public static PageBuilder create() {
        return new PageBuilder();
    }

    public PageBuilder add_line(String s) {
        this.lines.add(s);
        return this;
    }

    public PageBuilder set_title(String title) {
        this.title = title;
        return this;
    }

    public PageBuilder add_line() {
        return this.add_line("");
    }
    public PageBuilder set_level(int lvl) {
        this.level = lvl;
        return this;
    }
    public LorePage end() {
        return new LorePage(this.title, this.lines, this.level);
    }
}
