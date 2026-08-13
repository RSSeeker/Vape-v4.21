package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class MultilineTextBlockComponent
extends GuiComponent {
    private final String iconResource;
    private double linkX;
    private Color linkHoverColor;
    private static final double ICON_TITLE_GAP = 3.0;
    @Nullable
    private String linkText;
    private double componentWidth;
    private final boolean useThemeAccent;
    private final Color titleColor;
    private static final double ACCENT_INSET = 2.0;
    private double linkHeight;
    private boolean linkHovered;
    private static final double TITLE_FONT_SCALE = 0.75;
    private final Color backgroundColor;
    private double linkWidth;
    private static final float CORNER_RADIUS = 2.5f;
    private static final double CONTENT_TOP_INSET = 6.0;
    private static final double CONTENT_PADDING = 8.0;
    @Nullable
    private Runnable linkAction;
    private static final double ICON_SIZE = 7.0;
    private final String title;
    private static final double BODY_FONT_SCALE = 0.75;
    private final Color bodyColor;
    private static final float BORDER_THICKNESS = 1.0f;
    private static final double ACCENT_CLIP_WIDTH = 2.0;
    private final String bodyText;
    private double linkY;
    private final Color customAccentColor;
    private Color linkColor;

    @Override
    public double C() {
        SmoothFontRenderer bodyFontRenderer = this.getFontRenderer(BODY_FONT_SCALE);
        double bodyHeight = this.measureWrappedTextHeight(bodyFontRenderer, this.bodyText, this.A() - ACCENT_INSET - CONTENT_PADDING * 2.0);
        return 15.0 + bodyHeight + CONTENT_TOP_INSET;
    }

    public void setLink(String linkText, Runnable linkAction) {
        this.linkText = linkText;
        this.linkAction = linkAction;
    }

    private double measureWrappedTextHeight(SmoothFontRenderer fontRenderer, String text, double maxWidth) {
        if (maxWidth <= 0.0) {
            maxWidth = this.componentWidth - ACCENT_INSET - CONTENT_PADDING * 2.0;
        }
        double lineHeight = fontRenderer.d("A") + 1.0;
        int lineCount = 0;
        String[] paragraphs = text.split("\n", -1);
        for (String paragraph : paragraphs) {
            if (paragraph.isEmpty()) {
                ++lineCount;
                continue;
            }
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();
            int paragraphLineCount = 1;
            for (String word : words) {
                String candidateLine = currentLine.length() == 0 ? word : currentLine + " " + word;
                double candidateWidth = fontRenderer.N(candidateLine);
                if (candidateWidth > maxWidth && currentLine.length() > 0) {
                    ++paragraphLineCount;
                    currentLine = new StringBuilder(word);
                    continue;
                }
                currentLine = new StringBuilder(candidateLine);
            }
            lineCount += paragraphLineCount;
        }
        return (double)lineCount * lineHeight;
    }


    public void setWidth(double width) {
        this.componentWidth = width;
    }

    @Override
    public double x() {
        return this.componentWidth;
    }

    @Override
    public void I() {
    }

    public MultilineTextBlockComponent(String title, String bodyText) {
        this.linkColor = MultilineTextBlockComponent.J.T;
        this.linkHoverColor = MultilineTextBlockComponent.J.X;
        this.componentWidth = 110.0;
        this.title = title;
        this.bodyText = bodyText;
        this.iconResource = "info";
        this.customAccentColor = null;
        this.useThemeAccent = true;
        this.backgroundColor = MultilineTextBlockComponent.J.m;
        this.titleColor = MultilineTextBlockComponent.J.A;
        this.bodyColor = MultilineTextBlockComponent.J.Z;
    }

    @Override
    public void u() {
    }

    private Color resolveAccentColor() {
        return this.useThemeAccent ? J.z() : this.customAccentColor;
    }

    @Override
    public void H() {
        double x = this.G$src$D$1b2f02a();
        double y = this.n();
        double width = this.A();
        double height = this.L();
        if (this.linkText != null && this.linkWidth > 0.0) {
            MousePosition mousePosition = RenderUtils.h();
            this.linkHovered = (double)mousePosition.O >= this.linkX && (double)mousePosition.O <= this.linkX + this.linkWidth && (double)mousePosition.H >= this.linkY && (double)mousePosition.H <= this.linkY + this.linkHeight;
        } else {
            this.linkHovered = false;
        }
        GuiRenderPrimitives.e(x, y, width, height, this.backgroundColor, false, CORNER_RADIUS, BORDER_THICKNESS);
        double contentX = x + ACCENT_INSET + CONTENT_PADDING;
        double contentTop = y + CONTENT_TOP_INSET;
        double bodyMaxWidth = width - ACCENT_INSET - CONTENT_PADDING * 2.0;
        Color accentColor = this.resolveAccentColor();
        double accentWidth = ICON_SIZE;
        RenderUtils.m(x, y, ACCENT_CLIP_WIDTH, height);
        GuiRenderPrimitives.p(x, y, accentWidth, height, accentColor, false, CORNER_RADIUS, BORDER_THICKNESS, (float)CONTENT_PADDING, MultilineTextBlockComponent.J.u, 9);
        RenderUtils.T();
        double iconY = contentTop + 0.5;
        ImageRenderer.drawImage(accentColor, (float)contentX, (float)iconY, this.iconResource, (float)ICON_SIZE, (float)ICON_SIZE, false);
        double titleX = contentX + ICON_SIZE + ICON_TITLE_GAP;
        SmoothFontRenderer titleFontRenderer = this.getAlternateFontRenderer(TITLE_FONT_SCALE);
        double titleHeight = titleFontRenderer.d("A");
        double titleY = iconY + ICON_SIZE / 2.0 - titleHeight / 2.0;
        titleFontRenderer.d(this.title, titleX, titleY, this.titleColor);
        double bodyY = contentTop + ICON_SIZE + ACCENT_INSET;
        SmoothFontRenderer bodyFontRenderer = this.getFontRenderer(BODY_FONT_SCALE);
        this.renderWrappedBody(bodyFontRenderer, this.bodyText, contentX, bodyY, bodyMaxWidth, this.bodyColor);
    }

    @Override
    public void g(GuiMouseEvent mouseEvent) {
        if (this.linkText == null || this.linkAction == null || this.linkWidth <= 0.0) {
            return;
        }
        if (mouseEvent.getAction() != MouseButton.LEFT_CLICK) {
            return;
        }
        if ((double)mouseEvent.getX() >= this.linkX && (double)mouseEvent.getX() <= this.linkX + this.linkWidth && (double)mouseEvent.getY() >= this.linkY && (double)mouseEvent.getY() <= this.linkY + this.linkHeight) {
            this.linkAction.run();
            mouseEvent.setCancelled(true);
        }
    }

    private void renderWrappedBody(SmoothFontRenderer fontRenderer, String text, double x, double y, double maxWidth, Color textColor) {
        if (maxWidth <= 0.0) {
            maxWidth = this.componentWidth - ACCENT_INSET - CONTENT_PADDING * 2.0;
        }
        double lineHeight = fontRenderer.d("A") + 1.0;
        double spaceWidth = fontRenderer.N(" ");
        int linkStartIndex = this.linkText != null ? text.indexOf(this.linkText) : -1;
        int linkEndIndex = linkStartIndex >= 0 ? linkStartIndex + this.linkText.length() : -1;
        this.linkX = 0.0;
        this.linkY = 0.0;
        this.linkWidth = 0.0;
        this.linkHeight = 0.0;
        double currentX = x;
        double currentY = y;
        int textIndex = 0;
        String[] paragraphs = text.split("\n", -1);
        for (int paragraphIndex = 0; paragraphIndex < paragraphs.length; ++paragraphIndex) {
            String paragraph = paragraphs[paragraphIndex];
            if (paragraphIndex > 0) {
                currentX = x;
                currentY += lineHeight;
            }
            if (paragraph.isEmpty()) {
                ++textIndex;
                continue;
            }
            String[] words = paragraph.split(" ");
            for (String word : words) {
                double wordWidth = fontRenderer.N(word);
                if (currentX + wordWidth > x + maxWidth && currentX > x) {
                    currentX = x;
                    currentY += lineHeight;
                }
                int wordEndIndex = textIndex + word.length();
                boolean linkWord = linkStartIndex >= 0 && textIndex < linkEndIndex && wordEndIndex > linkStartIndex;
                if (linkWord) {
                    Color renderedLinkColor = this.linkHovered ? this.linkHoverColor : this.linkColor;
                    fontRenderer.d(word, currentX, currentY, renderedLinkColor);
                    if (this.linkWidth == 0.0) {
                        this.linkX = currentX;
                        this.linkY = currentY;
                        this.linkHeight = lineHeight;
                    }
                    this.linkWidth = currentX + wordWidth - this.linkX;
                } else {
                    fontRenderer.d(word, currentX, currentY, textColor);
                }
                currentX += wordWidth + spaceWidth;
                textIndex = wordEndIndex + 1;
            }
            if (paragraphIndex >= paragraphs.length - 1) continue;
            textIndex = textIndex - 1 + 1;
        }
        if (this.linkText != null && this.linkWidth > 0.0) {
            double textHeight = fontRenderer.d("A");
            double underlineY = this.linkY + textHeight;
            Color underlineColor = this.linkHovered ? this.linkHoverColor : this.linkColor;
            GuiRenderPrimitives.z(this.linkX, underlineY, this.linkX + this.linkWidth, underlineY, 1.0, 1.5, underlineColor);
        }
    }

    @Override
    public void F() {
    }

    public void setLinkColors(Color linkColor, Color linkHoverColor) {
        this.linkColor = linkColor;
        this.linkHoverColor = linkHoverColor;
    }

    public MultilineTextBlockComponent(String title, String bodyText, String iconResource, Color accentColor, Color backgroundColor, Color titleColor, Color bodyColor) {
        this.linkColor = MultilineTextBlockComponent.J.T;
        this.linkHoverColor = MultilineTextBlockComponent.J.X;
        this.componentWidth = 110.0;
        this.title = title;
        this.bodyText = bodyText;
        this.iconResource = iconResource;
        this.customAccentColor = accentColor;
        this.useThemeAccent = false;
        this.backgroundColor = backgroundColor;
        this.titleColor = titleColor;
        this.bodyColor = bodyColor;
    }

    public MultilineTextBlockComponent(String title, String bodyText, Color accentColor) {
        this.linkColor = MultilineTextBlockComponent.J.T;
        this.linkHoverColor = MultilineTextBlockComponent.J.X;
        this.componentWidth = 110.0;
        this.title = title;
        this.bodyText = bodyText;
        this.iconResource = "info";
        this.customAccentColor = accentColor;
        this.useThemeAccent = false;
        this.backgroundColor = MultilineTextBlockComponent.J.m;
        this.titleColor = MultilineTextBlockComponent.J.A;
        this.bodyColor = MultilineTextBlockComponent.J.Z;
    }
}

