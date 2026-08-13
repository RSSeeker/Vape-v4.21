package gg.vape.ui.click.frame.impl.target;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.utils.render.EntityModelRenderCache;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityLivingBase;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TargetInfoEntityPreviewComponent
extends GuiComponent {
    @NotNull
    private Color faceColor = Color.WHITE;
    private EntityLivingBase entity;
    private static final String NULL_FACE_COLOR_MESSAGE = "faceColor is marked non-null but is null";
    @Nullable
    private Color backgroundColor = new Color(100, 100, 100, 70);
    @Nullable
    private HudModuleFrameBase frame;

    public void setBackgroundColor(@Nullable Color color) {
        this.backgroundColor = color;
    }

    @NotNull
    public Color getFaceColor() {
        return this.faceColor;
    }

    public void setFaceColor(@NotNull Color color) {
        if (color == null) {
            throw new NullPointerException(NULL_FACE_COLOR_MESSAGE);
        }
        this.faceColor = color;
    }

    @Nullable
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public TargetInfoEntityPreviewComponent(double d, double d2) {
        this.o(d);
        this.Y(d2);
    }

    @Override
    public void H() {
        if (this.getEntity() == null || this.getEntity().isNull()) {
            return;
        }
        Color color = this.backgroundColor;
        Color color2 = this.faceColor;
        float f = 1.0f;
        if (this.frame != null) {
            if (color != null) {
                color = this.frame.applyDefaultEditorAlpha(color);
            }
            color2 = this.frame.applyDefaultEditorAlpha(color2);
            f = this.frame.getEditorOpacity();
        }
        if (color != null) {
            GuiRenderPrimitives.g(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), this.L(), 12.0f, 1.0f, color);
        }
        EntityModelRenderCache.renderEntity(this.getEntity(), (float)this.G$src$D$1b2f02a(), (float)this.n(), (int)this.A(), (int)this.L(), color2, f);
    }

    public void setEntity(EntityLivingBase entityLivingBase) {
        this.entity = entityLivingBase;
    }

    public void setFrame(@Nullable HudModuleFrameBase frame) {
        this.frame = frame;
    }
}
