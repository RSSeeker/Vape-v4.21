package gg.vape.ui.click.frame.impl.target;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityLivingBase;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class TargetInfoHealthBarComponent
extends GuiComponent {
    private final TimerUtil animationTimer = new TimerUtil();
    private boolean snapToTargetHealth;
    private HudModuleFrameBase frame;
    @Nullable
    private EntityLivingBase entity;
    private float targetHealth;
    private float displayedHealth;

    public void setFrame(HudModuleFrameBase frame) {
        this.frame = frame;
    }

    @Override
    public void H() {
        double d = this.getHealthFraction();
        if (d == -1.0) {
            return;
        }
        double d2 = Math.min(this.A() * this.getHealthFraction(), this.A());
        float f = (float)this.L() / 2.0f - 0.5f;
        Color color = new Color(0, 0, 0, 100);
        Color color2 = TargetInfoHealthBarComponent.J.B;
        Color color3 = new Color(0, 0, 0, 100);
        if (this.frame != null) {
            color = this.frame.applyDefaultEditorAlpha(color);
            color2 = this.frame.applyDefaultEditorAlpha(color2);
            color3 = this.frame.applyDefaultEditorAlpha(color3);
        }
        GuiRenderPrimitives.I(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color, false, f, 1.0f, 8.0f, color3);
        GuiRenderPrimitives.I(this.G$src$D$1b2f02a(), this.n(), d2, this.L(), color2, true, f, 1.0f, 8.0f, color3);
    }

    private float getSmoothedHealth() {
        float f;
        if (this.snapToTargetHealth) {
            return this.targetHealth;
        }
        float f2 = this.targetHealth;
        long l = this.animationTimer.getLastMS();
        if ((float)l >= (f = 10.0f)) {
            double d;
            float f3 = Math.abs(this.displayedHealth - f2);
            float f4 = f3 < 0.5f && this.displayedHealth < 0.5f ? 0.05f * ((float)l / f) : 0.08f * ((float)l / f);
            if (this.displayedHealth < f2) {
                d = Math.max(1.0, Math.pow(f3, 0.5));
                this.displayedHealth = (float)((double)this.displayedHealth + (double)f4 * d);
            }
            if (this.displayedHealth > f2) {
                d = Math.max(1.0, Math.pow(f3, 0.5));
                this.displayedHealth = (float)((double)this.displayedHealth - (double)f4 * d);
            }
            this.displayedHealth = Math.max(0.0f, Math.min(this.displayedHealth, 20.0f));
            if ((double)Math.abs(this.displayedHealth - f2) < 0.001) {
                this.displayedHealth = f2;
            }
            this.animationTimer.reset();
        }
        if (Float.isNaN(this.displayedHealth) || !Float.isFinite(this.displayedHealth)) {
            this.displayedHealth = f2;
            this.animationTimer.reset();
        }
        f2 = this.displayedHealth;
        f2 = Math.max(f2, 0.0f);
        return f2;
    }

    @Override
    public void u() {
        if (this.entity != null) {
            this.targetHealth = RotationUtil.x(this.entity);
            if (this.snapToTargetHealth) {
                this.snapToTargetHealth = false;
                this.animationTimer.reset();
                this.displayedHealth = this.targetHealth;
            }
        } else {
            this.targetHealth = 0.0f;
        }
    }

    public double getHealthFraction() {
        if (this.entity == null) {
            return -1.0;
        }
        float f = this.getSmoothedHealth();
        return f / this.entity.I$src$F$14vyvep();
    }


    public void setEntity(@Nullable EntityLivingBase entityLivingBase) {
        if (entityLivingBase != null && entityLivingBase.isNotNull() && entityLivingBase.equals(this.entity)) {
            return;
        }
        this.entity = entityLivingBase;
        this.snapToTargetHealth = true;
    }

    public TargetInfoHealthBarComponent(int n, int n2) {
        this.o(n);
        this.Y(n2);
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }
}

