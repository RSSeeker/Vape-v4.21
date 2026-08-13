package gg.vape.friend.ping;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendColorUtil;
import gg.vape.friend.ping.PingMarker;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderMatrix4f;
import java.awt.Color;
import java.util.List;

public class OnlineFriendPingMarker
extends PingMarker {
    private static boolean obfuscationFlag;
    private static final String LOCATION_ICON;
    private DoubleAnimation burstAnimation = new DoubleAnimation(0.5, 0.0, 1.0);

    public static boolean getObfuscationConstant() {
        boolean flag = OnlineFriendPingMarker.isObfuscationFlagSet();
        return true;
    }

    public void renderRipple(double expansion, double centerX, double centerY, double baseSize, double padding, Color color, DoubleAnimation doubleAnimation) {
        double progress = doubleAnimation.X();
        GuiRenderPrimitives.m((float)(centerX - baseSize / 2.0 - padding - expansion / 2.0 * progress), (float)(centerY - baseSize / 2.0 - padding - expansion / 2.0 * progress), (float)(baseSize + padding * 2.0 + expansion * progress), 2.0f, 1.5f, new Color(26, 25, 26, (int)((1.0 - progress) * 100.0)));
        padding = 0.0;
        GuiRenderPrimitives.m((float)(centerX - baseSize / 2.0 - padding - expansion / 2.0 * progress), (float)(centerY - baseSize / 2.0 - padding - expansion / 2.0 * progress), (float)(baseSize + padding * 2.0 + expansion * progress), 2.0f, 1.0f, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)((1.0 - progress) * 255.0)));
    }

    @Override
    public void trigger() {
        super.trigger();
        this.burstAnimation.c();
    }

    public static boolean isObfuscationFlagSet() {
        return obfuscationFlag;
    }

    static {
        OnlineFriendPingMarker.setObfuscationFlag(false);
        LOCATION_ICON = "ping_location";
    }

    @Override
    public void render3D() {
    }


    public OnlineFriendPingMarker(OnlineFriend onlineFriend, double[] position) {
        super(onlineFriend, position);
        this.setWidth(18.0);
        this.setHeight(18.0);
    }

    @Override
    public PingTargetData toTargetData() {
        return PingTargetData.C(this.getX(), this.getY(), this.getZ());
    }

    public static void setObfuscationFlag(boolean flag) {
        obfuscationFlag = flag;
    }

    @Override
    public void render2D(boolean offscreen) {
        float verticalShift;
        Color color = OnlineFriendColorUtil.getGroupRoleColor(this.getFriend());
        double burstProgress = this.burstAnimation.X();
        int alpha = (int)(100.0 + 150.0 * burstProgress);
        Color baseColor = color;
        Color markerColor = baseColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
        if (color.equals(OnlineFriendColorUtil.getGroupRoleColor(-1))) {
            markerColor = new Color(0, 0, 0, alpha);
        }
        double centerX = 0.0;
        double markerY = 0.0;
        double borderWidth = 1.0;
        double markerSize = 5.0;
        double halfMarkerSize = 2.5;
        if (offscreen) {
            GuiRenderPrimitives.m(-3.5f, (float)(markerY - halfMarkerSize) - 1.0f, 7.0f, 2.0f, (float)borderWidth, Color.WHITE);
            GuiRenderPrimitives.V(-halfMarkerSize, (float)(markerY - halfMarkerSize), markerSize, borderWidth, markerColor);
        } else {
            float iconSize = 12.0f;
            float iconX = 0.0f - iconSize / 2.0f;
            float iconY = (float)markerY - iconSize;
            ImageRenderer.drawImage(Color.WHITE, iconX, iconY, LOCATION_ICON, iconSize, iconSize, false);
            GuiRenderPrimitives.m(-2.5f, (float)((markerY -= 7.7) - 2.5), 5.0f, 2.0f, 1.0f, new Color(0, 0, 0, alpha));
            GuiRenderPrimitives.V(-2.5, (float)(markerY - 2.5), 5.0, 1.0, markerColor);
        }
        boolean hasActiveRipples = false;
        List<DoubleAnimation> animations = this.getAnimations();
        for (int index = animations.size() - 1; index >= 0; --index) {
            if (!animations.get(index).N()) {
                this.renderRipple(40.0, centerX, markerY, markerSize, borderWidth, baseColor, animations.get(index));
                hasActiveRipples = true;
                continue;
            }
            animations.remove(index);
        }
        verticalShift = offscreen ? 0.0f : 7.7f;
        if (hasActiveRipples) {
            double progress = this.burstAnimation.X();
            float angle = -45.0f;
            double clampedProgress = Math.min(progress, 0.98);
            double radius = 500.0 - 500.0 * clampedProgress - 2.0;
            double rayLength = 4.0 + (7.0 - progress * 7.0);
            double rayScale = 4.0 - progress * 4.0 + 1.0;
            for (int index = 0; index < 4; ++index) {
                double offsetX = Math.cos(Math.toRadians(angle)) * (radius + 1.0);
                double offsetY = Math.sin(Math.toRadians(angle)) * (radius + 1.0);
                if (GuiRenderPrimitives.d()) {
                    RenderMatrix4f renderMatrix4f = BufferedGuiRenderPrimitives.matrixStack.peek();
                    OpenGlBackendHolder.backend.pushMatrix();
                    BufferedGuiRenderPrimitives.matrixStack.peek().multiply(renderMatrix4f);
                } else {
                    OpenGlBackendHolder.backend.pushMatrix();
                }
                OpenGlBackendHolder.backend.translate(centerX + offsetX, markerY + offsetY - (double)verticalShift, 0.0);
                OpenGlBackendHolder.backend.rotate(angle, 0.0f, 0.0f, 1.0f);
                GuiRenderPrimitives.B(0.0, 0.0, rayLength * rayScale, 0.5 * rayScale, new MutableColor(Color.BLACK).withAlpha(255), 1.0f);
                GuiRenderPrimitives.B(0.0, 0.0, rayLength * rayScale, 0.5 * rayScale, new MutableColor(Color.WHITE).withAlpha(255), 1.0f);
                OpenGlBackendHolder.backend.popMatrix();
                angle += 90.0f;
            }
        }
    }
}

