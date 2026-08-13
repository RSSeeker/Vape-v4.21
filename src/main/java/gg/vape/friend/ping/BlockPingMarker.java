package gg.vape.friend.ping;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendColorUtil;
import gg.vape.friend.ping.PingMarker;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MathUtil;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class BlockPingMarker
extends PingMarker {
    private static boolean obfuscationFlag;

    public void drawOutline(AxisAlignedBB axisAlignedBB, Color color, float lineWidth) {
        if (GuiRenderPrimitives.d()) {
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            BufferedRenderPrimitives.drawLine3D(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), lineWidth, color);
            return;
        }
        OpenGlBackendHolder.backend.setColor((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glEnd();
    }

    static {
        if (!BlockPingMarker.getObfuscationConstant()) {
            BlockPingMarker.setObfuscationFlag(true);
        }
    }

    public static void setObfuscationFlag(boolean flag) {
        obfuscationFlag = flag;
    }


    @Override
    public PingTargetData toTargetData() {
        return PingTargetData.Y((int)this.getX(), (int)this.getY(), (int)this.getZ());
    }

    public void drawFill(AxisAlignedBB axisAlignedBB, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), color);
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), color);
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), color);
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), color);
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), color);
            BufferedRenderPrimitives.fillQuad(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ(), color);
            return;
        }
        OpenGlBackendHolder.backend.setColor((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, 0.6f);
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMaxX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMinZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMinY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMaxZ());
        GL11.glVertex3d((double)axisAlignedBB.getMinX(), (double)axisAlignedBB.getMaxY(), (double)axisAlignedBB.getMinZ());
        GL11.glEnd();
    }

    public BlockPingMarker(OnlineFriend onlineFriend, double[] position) {
        super(onlineFriend, position);
    }

    public static boolean isObfuscationFlagSet() {
        return obfuscationFlag;
    }

    @Override
    public void render3D() {
        double blockX = MathUtil.floor(this.getX());
        double blockY = MathUtil.floor(this.getY());
        double blockZ = MathUtil.floor(this.getZ());
        Block block = Minecraft.theWorld().getBlock(blockX, blockY, blockZ);
        if (block == null || block.isNull()) {
            return;
        }
        OpenGlBackendHolder.backend.pushMatrix();
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (blendEnabled) {
            GL11.glBlendFunc((int)770, (int)771);
            OpenGlBackendHolder.backend.enableCapability(2848);
            OpenGlBackendHolder.backend.setLineWidth(2.0f);
            OpenGlBackendHolder.backend.disableCapability(3553);
            OpenGlBackendHolder.backend.setDepthMask(false);
            double renderX = Minecraft.D().getRenderPosX();
            double renderY = Minecraft.D().getRenderPosY();
            double renderZ = Minecraft.D().getRenderPosZ();
            OpenGlBackendHolder.backend.translate(-renderX, -renderY, -renderZ);
            OpenGlBackendHolder.backend.translate(blockX, blockY, blockZ);
            RenderUtils.g();
            Color color = Color.red;
            Color color2 = Color.red;
            this.drawFill(AxisAlignedBB.create(-0.001, -0.001, -0.001, 1.001, 1.001, 1.001), color);
            this.drawOutline(AxisAlignedBB.create(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).expand(0.002f, 0.002f, 0.002f), color2, 2.0f);
            OpenGlBackendHolder.backend.setDepthMask(true);
            RenderUtils.f();
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(2848);
            OpenGlBackendHolder.backend.popMatrix();
            return;
        }
        OpenGlBackendHolder.backend.enableCapability(3042);
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlBackendHolder.backend.enableCapability(2848);
        OpenGlBackendHolder.backend.setLineWidth(2.0f);
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.setDepthMask(false);
        double renderX = Minecraft.D().getRenderPosX();
        double renderY = Minecraft.D().getRenderPosY();
        double renderZ = Minecraft.D().getRenderPosZ();
        OpenGlBackendHolder.backend.translate(-renderX, -renderY, -renderZ);
        OpenGlBackendHolder.backend.translate(blockX, blockY, blockZ);
        RenderUtils.g();
        Color color = Color.red;
        Color color3 = Color.red;
        this.drawFill(AxisAlignedBB.create(-0.001, -0.001, -0.001, 1.001, 1.001, 1.001), color);
        this.drawOutline(AxisAlignedBB.create(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).expand(0.002f, 0.002f, 0.002f), color3, 2.0f);
        OpenGlBackendHolder.backend.setDepthMask(true);
        RenderUtils.f();
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(3042);
        OpenGlBackendHolder.backend.disableCapability(2848);
        OpenGlBackendHolder.backend.popMatrix();
    }

    public static boolean getObfuscationConstant() {
        boolean flag = BlockPingMarker.isObfuscationFlagSet();
        return !flag;
    }

    @Override
    public void render2D(boolean offscreen) {
        Color color = OnlineFriendColorUtil.getGroupRoleColor(this.getFriend());
        double centerX = 0.0;
        double centerY = 0.0;
        double markerSize = 12.0;
        double halfMarkerSize = markerSize / 2.0;
        GuiRenderPrimitives.m((float)(centerX - halfMarkerSize), (float)(centerY - halfMarkerSize - 8.0), (float)markerSize, 6.0f, 1.0f, color);
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().W(1.0, false);
        smoothFontRenderer.f(this.getLabel(), centerX, centerY + halfMarkerSize - 6.0, color);
    }
}

