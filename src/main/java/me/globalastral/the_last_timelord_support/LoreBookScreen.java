package me.globalastral.the_last_timelord_support;

import com.mojang.blaze3d.vertex.PoseStack;
import me.globalastral.the_last_timelord_support.pages.LoreBookPages;
import me.globalastral.the_last_timelord_support.pages.LorePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class LoreBookScreen extends Screen {

    private List<LorePage> UNLOCKED_PAGES = new ArrayList<>();
    private final int lore_level;

    public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(TheLastTimelordSupport.MODID, "textures/gui/lore_book_gui.png");

    private static final int TEX_W = 256;
    private static final int TEX_H = 180;
    private static final int TEXT_COLOR = 0x404040;
    private static final int SCALE_FACTOR = 120;

    private int page_index = 0;

    public LoreBookScreen(int lore_level) {
        super(Component.empty());
        this.lore_level = lore_level;
    }

    private void renderString(GuiGraphics guiGraphics, String string, int x, int y) {
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                styleText(string),
                x,
                y,
                TEXT_COLOR,
                false
        );

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                styleText(string),
                x,
                y,
                TEXT_COLOR,
                false
        );
    }

    private Component styleText(String text) {
        return Component.literal(text).withStyle(s -> s.withFont(ResourceLocation.fromNamespaceAndPath(TheLastTimelordSupport.MODID, "xanhmono-regular")));
    }

    private void render_page(GuiGraphics pGuiGraphics, int x, int y, LorePage page) {
        Font font = Minecraft.getInstance().font;
        if (!page.title().isEmpty()) {
            String title = page.title();
            int title_x = x + 75 - (font.width(title) / 2);
            int title_y = y + 12;
            renderString(pGuiGraphics, title, title_x, title_y);
        }
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();

        float scalex = font.width(page.lines().get(0));
        for (int i = 1; i < page.lines().size(); i++) {
            float new_scale = font.width(page.lines().get(i));
            if (new_scale > scalex)
                scalex = new_scale;
        }

        scalex = SCALE_FACTOR / scalex;

        poseStack.scale(scalex, 1.0F, 1.0F);

        int line_x = x + 14;
        int line_y = y + 27;
        for (int i = 0; i < page.lines().size(); i++) {
            String line = page.lines().get(i);
            if (line.isEmpty())
                continue;
            int translatedY = line_y + (i * 8) - 5;
            int scaledX = (int) (line_x / scalex);
            renderString(pGuiGraphics, line, scaledX, translatedY);
        }
        poseStack.popPose();
    }

    private void render_second_page(GuiGraphics pGuiGraphics, int x, int y, LorePage page) {
        render_page(pGuiGraphics, x + 126, y, page);
    }

    private LorePage get_page_safe(int i) {
        try {
            return UNLOCKED_PAGES.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    protected void init() {
        this.UNLOCKED_PAGES = new ArrayList<>(LoreBookPages.get_unlocked_pages(lore_level));
        int i = (this.width - TEX_W) / 2;
        int j = (this.height - TEX_H) / 2;
        this.addRenderableWidget(new PageButton(i + 9, j + 160, button -> {
            if (page_index - 2 >= 0)
                page_index -= 2;
        }, true));

        this.addRenderableWidget(new PageButton(i + 229, j + 160, button -> {
            if (page_index + 2 < UNLOCKED_PAGES.size())
                page_index += 2;
        }, false));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int i = (this.width - TEX_W) / 2;
        int j = (this.height - TEX_H) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, 256, 192);

        LorePage first = get_page_safe(page_index);
        LorePage second = get_page_safe(page_index + 1);

        if (first != null)
            render_page(pGuiGraphics, i, j, first);

        if (second != null)
            render_second_page(pGuiGraphics, i, j, second);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    private static class PageButton extends Button {
        private static final int Upos = 1;
        private static final int VposL = 194;
        private static final int VposR = 207;
        private static final int ALTERNATE_OFFSET = 23;
        private static final int WIDTH = 18;
        private static final int HEIGHT = 10;

        private final boolean flip;

        public PageButton(int pX, int pY, OnPress pOnPress, boolean flip) {
            super(pX, pY, WIDTH, HEIGHT, Component.empty(), pOnPress, DEFAULT_NARRATION);
            this.flip = flip;
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        }

        @Override
        public void playDownSound(SoundManager pHandler) {
            pHandler.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

            int u = Upos + (isHovered() ? ALTERNATE_OFFSET : 0);
            int v = flip ? VposR : VposL;

            pGuiGraphics.blit(GUI_TEXTURE, this.getX(), this.getY(), u, v, WIDTH, HEIGHT);
        }
    }
}
