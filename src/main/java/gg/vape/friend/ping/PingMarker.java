package gg.vape.friend.ping;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendColorUtil;
import gg.vape.notification.NotificationSounds;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.RenderWorldLastEvent;
import gg.vape.wrapper.impl.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class PingMarker {
    private double[] clippedScreenPosition;
    private long triggeredAt = Long.MAX_VALUE;
    private double height = 20.0;
    private final OnlineFriend friend;
    private double width = 20.0;
    private static GuiComponent[] obfuscationComponents;
    private double[] projectedScreenPosition;
    private boolean visible = true;
    protected double[] worldPosition;
    private double edgePadding = 6.0;
    private final List<DoubleAnimation> animations = new ArrayList<DoubleAnimation>();

    public double[] getUpdatedScreenPosition() {
        this.updateScreenPosition();
        return this.clippedScreenPosition;
    }

    public double calculateArrowRadius(double angle) {
        double[] center = this.clippedScreenPosition;
        double rayLength = 100.0;
        double[] centerRayEnd = new double[]{center[0] + rayLength * Math.cos(angle), center[1] + rayLength * Math.sin(angle)};
        double left = center[0] - this.getWidth() / 2.0;
        double right = center[0] + this.getWidth() / 2.0;
        double top = center[1] - this.getHeight() / 2.0;
        double bottom = center[1] + this.getHeight() / 2.0;
        Double[] topLeft = new Double[]{left, top};
        Double[] topRight = new Double[]{right, top};
        Double[] bottomLeft = new Double[]{left, bottom};
        Double[] bottomRight = new Double[]{right, bottom};
        ArrayList<Double[][]> edges = new ArrayList<Double[][]>(Arrays.asList(new Double[][][]{{topLeft, topRight}, {topRight, bottomRight}, {bottomLeft, bottomRight}, {topLeft, bottomLeft}}));
        double centerDistance = Double.MAX_VALUE;
        for (Double[][] edge : edges) {
            double[] intersection = this.intersectLines(center, centerRayEnd, edge[0], edge[1]);
            if (intersection == null) continue;
            double distance = RotationUtil.V(center[0], center[1], intersection[0], intersection[1]);
            if (distance >= centerDistance) continue;
            centerDistance = distance;
        }
        centerDistance += 4.0;
        double angularOffset = Math.min(this.getWidth(), this.getHeight()) - (this.getWidth() < this.getHeight() ? this.getWidth() / this.getHeight() : this.getHeight() / this.getWidth()) * centerDistance;
        double[] leftRayEnd = new double[]{center[0] + rayLength * Math.cos(Math.toRadians(Math.toDegrees(angle) - angularOffset)), center[1] + rayLength * Math.sin(Math.toRadians(Math.toDegrees(angle) - angularOffset))};
        double[] rightRayEnd = new double[]{center[0] + rayLength * Math.cos(Math.toRadians(Math.toDegrees(angle) + angularOffset)), center[1] + rayLength * Math.sin(Math.toRadians(Math.toDegrees(angle) + angularOffset))};
        double leftDistance = Double.MAX_VALUE;
        for (Double[][] edge : edges) {
            double[] intersection = this.intersectLines(center, leftRayEnd, edge[0], edge[1]);
            if (intersection == null) continue;
            double distance = RotationUtil.V(center[0], center[1], intersection[0], intersection[1]);
            if (distance >= leftDistance) continue;
            leftDistance = distance;
        }
        double rightDistance = Double.MAX_VALUE;
        for (Double[][] edge : edges) {
            double[] intersection = this.intersectLines(center, rightRayEnd, edge[0], edge[1]);
            if (intersection == null) continue;
            double distance = RotationUtil.V(center[0], center[1], intersection[0], intersection[1]);
            if (distance >= rightDistance) continue;
            rightDistance = distance;
        }
        double maxDistance = 0.0;
        Double[] distances = new Double[]{centerDistance, leftDistance, rightDistance};
        for (Double distance : distances) {
            if (!(distance.doubleValue() > maxDistance)) continue;
            maxDistance = distance.doubleValue();
        }
        return maxDistance - 0.009 * (maxDistance - centerDistance + (maxDistance - leftDistance) + (maxDistance - rightDistance)) / 2.0;
    }

    public abstract void render2D(boolean offscreen);

    public double getHeight() {
        return this.height;
    }

    public void updateScreenPosition() {
        RenderManager renderManager = Minecraft.D();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player == null || renderManager == null) {
            return;
        }
        double worldX = this.getX();
        double worldY = this.getY();
        double worldZ = this.getZ();
        double renderX = RenderManager.getInterpolatedRenderPosX();
        double renderEyeY = RenderManager.getInterpolatedRenderPosY() + (double)player.X();
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        double distance = RotationUtil.y(worldX, worldY, worldZ, renderX, renderEyeY, renderZ);
        double distanceScale = Math.min((38.0 - distance) / distance, 0.0);
        boolean withinProjectionRange = distanceScale == 0.0;
        double relativeY = withinProjectionRange ? worldY - (renderEyeY - (double)player.X()) : worldY - renderEyeY;
        Double[] relativePosition = new Double[]{worldX - renderX, relativeY, worldZ - renderZ};
        Double[] scaledOffset = new Double[]{relativePosition[0] * distanceScale, relativePosition[1] * distanceScale, relativePosition[2] * distanceScale};
        Double[] adjustedPosition = new Double[]{relativePosition[0] + scaledOffset[0], relativePosition[1] + scaledOffset[1], relativePosition[2] + scaledOffset[2]};
        RenderUtil.d();
        double projectionY = adjustedPosition[1] + (withinProjectionRange ? 0.0 : (double)player.X());
        double[] projected = RenderUtil.W(adjustedPosition[0], projectionY, adjustedPosition[2]);
        RenderUtil.Y();
        this.projectedScreenPosition = new double[]{projected[0], (double)Minecraft.h() - projected[1], projected[2]};
        this.clippedScreenPosition = this.projectedScreenPosition;
        if (!this.isOutsideViewport(this.projectedScreenPosition[0], this.projectedScreenPosition[1], this.projectedScreenPosition[2])) {
            return;
        }
        this.clipProjectedPosition(projected[2]);
    }

    private void clipProjectedPosition(double depth) {
        double screenWidth = Minecraft.J();
        double screenHeight = Minecraft.h();
        double markerBoundsWidth = this.getRightBound() - this.getLeftBound() + 4.0;
        double markerBoundsHeight = this.getBottomBound() - this.getTopBound() + 6.0;
        double screenCenteredX = this.projectedScreenPosition[0] - screenWidth / 2.0;
        double screenCenteredY = this.projectedScreenPosition[1] - screenHeight / 2.0;
        double markerCenteredX = this.projectedScreenPosition[0] - markerBoundsWidth / 2.0 - (screenWidth - markerBoundsWidth) / 2.0;
        double markerCenteredY = this.projectedScreenPosition[1] - markerBoundsHeight / 2.0 - (screenHeight - markerBoundsHeight) / 4.0;
        double screenScaleX = screenCenteredX < 0.0 ? -screenWidth / 2.0 / screenCenteredX : screenWidth / 2.0 / screenCenteredX;
        double screenScaleY = screenCenteredY < 0.0 ? -screenHeight / 2.0 / screenCenteredY : screenHeight / 2.0 / screenCenteredY;
        double markerScaleX = markerCenteredX < 0.0 ? -markerBoundsWidth / 2.0 / markerCenteredX : markerBoundsWidth / 2.0 / markerCenteredX;
        double markerScaleY = markerCenteredY < 0.0 ? -markerBoundsHeight / 2.0 / markerCenteredY : markerBoundsHeight / 2.0 / markerCenteredY;
        double screenScale = Math.abs(screenScaleX) < Math.abs(screenScaleY) ? screenScaleX : screenScaleY;
        double markerScale = Math.abs(markerScaleX) < Math.abs(markerScaleY) ? markerScaleX : markerScaleY;
        screenCenteredX *= depth >= 1.0 ? -screenScale : screenScale;
        screenCenteredY *= depth >= 1.0 ? -screenScale : screenScale;
        markerCenteredX *= depth >= 1.0 ? -markerScale : markerScale;
        markerCenteredY *= depth >= 1.0 ? -markerScale : markerScale;
        this.projectedScreenPosition = new double[]{screenCenteredX + screenWidth / 2.0, screenCenteredY + screenHeight / 2.0, this.projectedScreenPosition[2]};
        this.clippedScreenPosition = new double[]{markerCenteredX + markerBoundsWidth / 2.0 + (screenWidth - markerBoundsWidth) / 2.0, markerCenteredY + markerBoundsHeight / 2.0 + (screenHeight - markerBoundsHeight) / 4.0, this.projectedScreenPosition[2]};
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.triggeredAt >= this.getDurationMillis();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void renderScreenMarker() {
        float renderResolutionMultiplier = RenderWorldLastEvent.getRenderResolutionMultiplier();
        float uiScale = 1.0f;
        float coordinateScale = 2.0f;
        double screenX = this.clippedScreenPosition[0] / (double)coordinateScale / (double)uiScale / (double)renderResolutionMultiplier;
        double screenY = this.clippedScreenPosition[1] / (double)renderResolutionMultiplier / (double)coordinateScale / (double)uiScale;
        boolean offscreen = !this.projectedScreenPosition.equals(this.clippedScreenPosition);
        OpenGlBackendHolder.backend.translate(screenX, screenY, 0.0);
        this.render2D(offscreen);
        if (offscreen) {
            this.renderOffscreenArrow();
        }
        OpenGlBackendHolder.backend.translate(-screenX, -screenY, 0.0);
    }

    public boolean isNear(double x, double y, double z) {
        return RotationUtil.y(this.getX(), this.getY(), this.getZ(), x, y, z) <= 0.25;
    }

    public abstract PingTargetData toTargetData();

    static {
        if (PingMarker.getObfuscationComponents() != null) {
            PingMarker.setObfuscationComponents(new GuiComponent[1]);
        }
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    public String getLabel() {
        return this.friend != null ? this.friend.getDisplayName() : "";
    }

    public double getX() {
        return this.worldPosition[0];
    }

    public double getWidth() {
        return this.width;
    }

    private double getBottomBound() {
        return (double)Minecraft.h() - this.getHeight() - this.getEdgePadding() - 60.0;
    }

    public boolean isNear(double[] position) {
        return this.isNear(position[0], position[1], position[2]);
    }

    private double[] intersectLines(double[] firstStart, double[] firstEnd, Double[] secondStart, Double[] secondEnd) {
        double firstA = firstEnd[1] - firstStart[1];
        double firstB = firstStart[0] - firstEnd[0];
        double firstConstant = firstA * firstStart[0] + firstB * firstStart[1];
        double secondA = secondEnd[1] - secondStart[1];
        double secondB = secondStart[0] - secondEnd[0];
        double secondConstant = secondA * secondStart[0] + secondB * secondStart[1];
        double determinant = firstA * secondB - secondA * firstB;
        if (determinant == 0.0) {
            return null;
        }
        double intersectionX = (secondB * firstConstant - firstB * secondConstant) / determinant;
        double intersectionY = (firstA * secondConstant - secondA * firstConstant) / determinant;
        return new double[]{intersectionX, intersectionY};
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void trigger() {
        Vape.INSTANCE.getNotificationSoundPlayer().queue(NotificationSounds.PING);
        double animationDelay = 0.0;
        for (int i = 0; i < 1; ++i) {
            DoubleAnimation doubleAnimation = new DoubleAnimation(1.0, animationDelay, 0.0, 1.0);
            doubleAnimation.c();
            this.animations.add(doubleAnimation);
            animationDelay += 0.25;
        }
        this.triggeredAt = System.currentTimeMillis();
    }

    public long getRemainingMillis() {
        return this.getDurationMillis() - (System.currentTimeMillis() - this.triggeredAt);
    }


    public abstract void render3D();

    private boolean isOutsideViewport(double x, double y, double depth) {
        if (x <= this.getLeftBound() || x >= this.getRightBound()) {
            return true;
        }
        if (y > this.getBottomBound() || y < this.getTopBound()) {
            return true;
        }
        return depth > 1.0;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public double getLeftBound() {
        return this.getWidth() + this.getEdgePadding() * 3.0;
    }

    public PingMarker(OnlineFriend onlineFriend, double[] worldPosition) {
        this.friend = onlineFriend;
        this.worldPosition = worldPosition;
    }

    public OnlineFriend getFriend() {
        return this.friend;
    }

    public boolean isTriggered() {
        return this.triggeredAt != Long.MAX_VALUE;
    }

    public double getEdgePadding() {
        return this.edgePadding;
    }

    public void setWorldPosition(double[] worldPosition) {
        this.worldPosition = worldPosition;
    }

    public double[] getWorldPosition() {
        return this.worldPosition;
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    public void update(World world) {
    }

    public List<DoubleAnimation> getAnimations() {
        return this.animations;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setEdgePadding(double edgePadding) {
        this.edgePadding = edgePadding;
    }

    public void retrigger() {
        Vape.INSTANCE.getNotificationSoundPlayer().queue(NotificationSounds.PING);
        DoubleAnimation doubleAnimation = new DoubleAnimation(1.0, 0.0, 1.0);
        doubleAnimation.c();
        this.animations.add(doubleAnimation);
        doubleAnimation = new DoubleAnimation(1.0, 0.25, 0.0, 1.0);
        doubleAnimation.c();
        this.animations.add(doubleAnimation);
        if (this.isTriggered()) {
            this.trigger();
        }
    }

    protected float calculateAngle(double startX, double endX, double startY, double endY) {
        return (float)Math.toDegrees(Math.atan2(endY - startY, endX - startX));
    }

    public double getZ() {
        return this.worldPosition[2];
    }

    private double calculateAngleBetweenPoints(double[] first, double[] second) {
        return Math.atan2(first[1] - second[1], first[0] - second[0]) * 57.29577951308232;
    }

    public double getY() {
        return this.worldPosition[1];
    }

    private double getRightBound() {
        double screenWidth = Minecraft.J();
        return screenWidth - (this.getWidth() + this.getEdgePadding() * 3.0);
    }

    public void renderOffscreenArrow() {
        float renderResolutionMultiplier = RenderWorldLastEvent.getRenderResolutionMultiplier();
        Color color = OnlineFriendColorUtil.getGroupRoleColor(this.getFriend());
        renderResolutionMultiplier *= 2.0f;
        double angleDegrees = this.calculateAngleBetweenPoints(this.projectedScreenPosition, this.clippedScreenPosition);
        double leftAngle = Math.toRadians(angleDegrees + 45.0);
        double rightAngle = Math.toRadians(angleDegrees - 45.0);
        double angle = Math.toRadians(angleDegrees);
        double radius = Math.min(this.getWidth() / 2.0, this.calculateArrowRadius(angle));
        double tipX = (double)(1.0f / renderResolutionMultiplier) + (radius + this.getEdgePadding()) * Math.cos(angle);
        double tipY = (double)(1.0f / renderResolutionMultiplier) + (radius + this.getEdgePadding()) * Math.sin(angle);
        double leftX = tipX - this.getEdgePadding() * Math.cos(leftAngle);
        double leftY = tipY - this.getEdgePadding() * Math.sin(leftAngle);
        double notchX = tipX - this.getEdgePadding() * 0.6 * Math.cos(angle);
        double notchY = tipY - this.getEdgePadding() * 0.6 * Math.sin(angle);
        double rightX = tipX - this.getEdgePadding() * Math.cos(rightAngle);
        double rightY = tipY - this.getEdgePadding() * Math.sin(rightAngle);
        GuiRenderPrimitives.u(leftX, leftY, tipX, tipY, rightX, rightY, notchX, notchY, new Color(0, 0, 0, 255));
        GuiRenderPrimitives.u(leftX, leftY, tipX, tipY, rightX, rightY, notchX, notchY, color);
    }

    public double[] getProjectedScreenPosition() {
        return this.projectedScreenPosition;
    }

    private double getTopBound() {
        return this.getHeight() + this.getEdgePadding() * 3.0;
    }

    public long getDurationMillis() {
        return TimeUnit.SECONDS.toMillis(4L);
    }

    public double[] getClippedScreenPosition() {
        return this.clippedScreenPosition;
    }

    protected boolean isWithinBounds(double x, double y) {
        return x > this.getLeftBound() && x < this.getRightBound() && y > this.getTopBound() && y < this.getBottomBound();
    }
}
