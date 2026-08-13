package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.render.hud.CoordinatesHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Biome;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.Chunk;
import gg.vape.wrapper.impl.ChunkWorldBridge;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class CoordinatesHudFrame
extends HudModuleConfigFrameBase {
    private double horizontalContentWidth;
    private static final int SHADOW_COLOR_ARGB = 0x80000000;
    private final CoordinatesHudModule module = (CoordinatesHudModule)this.getModule();

    private void drawText(SmoothFontRenderer font, String text, double x, double y, Color color) {
        x = (int)x;
        if (this.shouldRenderHudBackground()) {
            font.d(text, x, y, this.applyDefaultEditorAlpha(color));
        } else {
            font.T(text, x, y, color, this.applyDefaultEditorAlpha(new Color(SHADOW_COLOR_ARGB, true)));
        }
    }

    private void renderVerticalLayout() {
        SmoothFontRenderer labelFont = Vape.INSTANCE.getFontManager().K(0.7, true);
        SmoothFontRenderer valueFont = Vape.INSTANCE.getFontManager().K(1.1, true);
        double rowHeight = this.L() / 4.0;
        double leftPadding = 8.0;
        Color dividerColor = ColorUtil.withAlpha(Color.WHITE, 51);
        EntityPlayerSP player = Minecraft.thePlayer();
        int x = (int)Math.round(player.z());
        int y = (int)Math.round(player.N());
        int z = (int)Math.round(player.h());
        this.drawText(labelFont, "X", this.G$src$D$1b2f02a() + leftPadding,
                this.n() + rowHeight / 2.0, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawText(valueFont, String.valueOf(x),
                this.G$src$D$1b2f02a() + leftPadding + labelFont.N("X") + 2.5,
                this.n() + rowHeight / 2.0 - 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawDirectionArrow(this.G$src$D$1b2f02a() + this.A() - 12.0,
                this.n() + rowHeight / 2.0 + 1.5, this.isPositiveXDirection());
        this.drawText(labelFont, "Y", this.G$src$D$1b2f02a() + leftPadding,
                this.n() + rowHeight * 1.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawText(valueFont, String.valueOf(y),
                this.G$src$D$1b2f02a() + leftPadding + labelFont.N("Y") + 2.5,
                this.n() + rowHeight * 1.5 - 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawText(labelFont, "Z", this.G$src$D$1b2f02a() + leftPadding,
                this.n() + rowHeight * 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawText(valueFont, String.valueOf(z),
                this.G$src$D$1b2f02a() + leftPadding + labelFont.N("Z") + 2.5,
                this.n() + rowHeight * 2.5 - 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawDirectionArrow(this.G$src$D$1b2f02a() + this.A() - 12.0,
                this.n() + rowHeight * 2.5, this.isPositiveZDirection());
        for (int row = 1; row <= 3; ++row) {
            GuiRenderPrimitives.a(this.G$src$D$1b2f02a() + leftPadding,
                    this.n() + rowHeight * row + 1.5, this.A() - 15.0,
                    1.0f, dividerColor);
        }
        this.drawText(labelFont, "BIOME:", this.G$src$D$1b2f02a() + leftPadding,
                this.n() + rowHeight * 3.5 - 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        this.drawText(labelFont, this.getBiomeName(),
                this.G$src$D$1b2f02a() + leftPadding + labelFont.N("BIOME:") + 4.0,
                this.n() + rowHeight * 3.5 - 2.5, this.applyDefaultEditorAlpha(CoordinatesHudFrame.J.Y));
    }

    private boolean isPositiveXDirection() {
        float heading = this.getHeading();
        return heading > 0.0f && heading < 180.0f;
    }

    @Override
    public String getName() {
        return "CoordinateFrame";
    }

    private String getBiomeName() {
        EntityPlayerSP player = Minecraft.thePlayer();
        int blockX = (int)Math.floor(player.z());
        int blockZ = (int)Math.floor(player.h());
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.theWorld().Y(BlockPos.create(blockX, 0, blockZ)).n();
        }
        Chunk chunk = Minecraft.theWorld().P(blockX, blockZ);
        ChunkWorldBridge chunkWorldBridge = Minecraft.theWorld().C();
        if (chunk == null || chunkWorldBridge == null) {
            return "";
        }
        Biome biome = chunk.J(blockX, blockZ, Minecraft.theWorld().C());
        return biome.n();
    }

    private void renderHorizontalLayout() {
        SmoothFontRenderer labelFont = Vape.INSTANCE.getFontManager().K(0.75, true);
        SmoothFontRenderer valueFont = Vape.INSTANCE.getFontManager().K(1.2, true);
        double halfHeight = this.L() / 2.0;
        Color dividerColor = ColorUtil.withAlpha(Color.WHITE, 51);
        EntityPlayerSP player = Minecraft.thePlayer();
        int x = (int)Math.round(player.z());
        int y = (int)Math.round(player.N());
        int z = (int)Math.round(player.h());
        double contentOffset = 10.0;
        this.drawText(labelFont, "X", this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0 + 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += labelFont.N("X") + 2.5;
        this.drawText(valueFont, String.valueOf(x), this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += Math.max(22.0,
                5.0 + valueFont.N("0") * String.valueOf(x).length());
        this.drawDirectionArrow(this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0 + 4.0, this.isPositiveXDirection());
        contentOffset += 10.0;
        GuiRenderPrimitives.d((int)(this.G$src$D$1b2f02a() + contentOffset),
                (int)(this.n() + halfHeight / 2.0), 8.0, 1.2f, dividerColor);
        contentOffset += 10.0;
        this.drawText(labelFont, "Y", this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0 + 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += labelFont.N("Y") + 2.5;
        this.drawText(valueFont, String.valueOf(y), this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += Math.max(22.0,
                5.0 + valueFont.N("0") * String.valueOf(y).length());
        GuiRenderPrimitives.d((int)(this.G$src$D$1b2f02a() + contentOffset),
                (int)(this.n() + halfHeight / 2.0), 8.0, 1.2f, dividerColor);
        contentOffset += 10.0;
        this.drawText(labelFont, "Z", this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0 + 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += labelFont.N("Z") + 2.5;
        this.drawText(valueFont, String.valueOf(z), this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0, this.applyDefaultEditorAlpha(Color.WHITE));
        contentOffset += Math.max(22.0,
                5.0 + valueFont.N("0") * String.valueOf(z).length());
        this.drawDirectionArrow(this.G$src$D$1b2f02a() + contentOffset,
                this.n() + halfHeight / 2.0 + 4.0, this.isPositiveZDirection());
        this.horizontalContentWidth = contentOffset;
        double biomeOffset = 10.0;
        this.drawText(labelFont, "BIOME:", this.G$src$D$1b2f02a() + biomeOffset,
                this.n() + halfHeight * 1.5 - 2.5, this.applyDefaultEditorAlpha(Color.WHITE));
        biomeOffset += labelFont.N("BIOME: ");
        this.drawText(labelFont, this.getBiomeName(),
                this.G$src$D$1b2f02a() + biomeOffset,
                this.n() + halfHeight * 1.5 - 2.5, this.applyDefaultEditorAlpha(CoordinatesHudFrame.J.Y));
    }

    @Override
    public double L() {
        if (this.isVerticalMode()) {
            return 90.0;
        }
        return 35.0;
    }

    private boolean isPositiveZDirection() {
        float heading = this.getHeading();
        return heading > 90.0f && heading < 270.0f;
    }

    @Override
    public double A() {
        if (this.isVerticalMode()) {
            return 70.0;
        }
        return this.horizontalContentWidth + 12.0;
    }

    private float getHeading() {
        EntityPlayerSP player = Minecraft.thePlayer();
        float heading = player.J() % 360.0f;
        if (heading < -180.0f) {
            heading += 360.0f;
        }
        if (heading > 180.0f) {
            heading -= 360.0f;
        }
        return heading + 180.0f;
    }

    private boolean isVerticalMode() {
        if (this.module == null) {
            return false;
        }
        return this.module.displayMode.getValue() == this.module.verticalMode;
    }

    public CoordinatesHudFrame() {
        super(CoordinatesHudModule.class);
    }

    private void drawDirectionArrow(double centerX, double centerY, boolean positiveDirection) {
        double backgroundX = centerX - 4.0;
        double backgroundY = centerY - 4.0;
        GuiRenderPrimitives.d(backgroundX, backgroundY, 8.0, 8.0,
                this.applyDefaultEditorAlpha(ColorUtil.withAlpha(CoordinatesHudFrame.J.i, 145)));
        double firstX;
        double firstY;
        double secondX;
        double secondY;
        double thirdX;
        double thirdY;
        Color arrowColor;
        if (positiveDirection) {
            firstX = backgroundX + 1.8;
            firstY = backgroundY + 4.5;
            secondX = backgroundX + 4.0;
            secondY = backgroundY + 2.5;
            thirdX = backgroundX + 5.7;
            thirdY = backgroundY + 4.5;
            arrowColor = CoordinatesHudFrame.J.B;
        } else {
            firstX = backgroundX + 1.8;
            firstY = backgroundY + 3.0;
            secondX = backgroundX + 5.7;
            secondY = backgroundY + 3.0;
            thirdX = backgroundX + 4.0;
            thirdY = backgroundY + 5.0;
            arrowColor = CoordinatesHudFrame.J.d;
        }
        GuiRenderPrimitives.U(firstX, firstY, secondX, secondY, thirdX, thirdY,
                this.applyDefaultEditorAlpha(arrowColor));
    }

    @Override
    public void renderHudContent() {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (this.isVerticalMode()) {
            this.renderVerticalLayout();
            return;
        }
        this.renderHorizontalLayout();
    }

}

