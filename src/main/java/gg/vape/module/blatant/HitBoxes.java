package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.UseEntityPacketBridge;
import gg.vape.wrapper.impl.Vec3;

public class HitBoxes
extends Mod {
    private final NumberValue expandAmount = NumberValue.create((Object)this, "Expand amount", "#.##", "", 0.0, 0.35, 1.0, 0.01);
    private static final long MODULE_ID = -170962805321564187L;

    public HitBoxes() {
        super("HitBoxes", (int)MODULE_ID, Category.OTHER, "Expands entities hitboxes");
        this.addValue(this.expandAmount);
    }

    @EventHandler
    public void onPacketSend(EventPacketSend eventPacketSend) {
        if (ForgeVersion.MC_1_8_9.v()) {
            return;
        }
        if (UseEntityPacketBridge.isUseEntityPacket(eventPacketSend.getPacket())) {
            UseEntityPacketBridge useEntityPacket = new UseEntityPacketBridge(eventPacketSend.getPacket());
            if (!useEntityPacket.isInteractAt()) {
                return;
            }
            Vec3 hitVector = useEntityPacket.getHitLocation();
            Entity entity = useEntityPacket.getEntity(Minecraft.theWorld());
            double expansion = (Double)this.expandAmount.getValue();
            double collisionBorder = entity.b();
            AxisAlignedBB normalBounds = entity.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().expand(collisionBorder, collisionBorder, collisionBorder);
            AxisAlignedBB expandedBounds = entity.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().expand(collisionBorder + expansion, collisionBorder + expansion, collisionBorder + expansion);
            double normalHalfWidth = (normalBounds.getMaxX() - normalBounds.getMinX()) / 2.0;
            double expandedHalfWidth = (expandedBounds.getMaxX() - expandedBounds.getMinX()) / 2.0;
            if (Math.abs(hitVector.getX()) > normalHalfWidth) {
                double xRatio = Math.min(1.0, Math.abs(hitVector.getX()) / expandedHalfWidth);
                double adjustedX = normalHalfWidth * xRatio;
                if (xRatio == 1.0) {
                    hitVector.N(hitVector.getX() > 0.0 ? normalHalfWidth : -normalHalfWidth);
                } else {
                    hitVector.N(hitVector.getX() > 0.0 ? adjustedX : -adjustedX);
                }
            }
            double normalHalfDepth = (normalBounds.getMaxZ() - normalBounds.getMinZ()) / 2.0;
            double expandedHalfDepth = (expandedBounds.getMaxZ() - expandedBounds.getMinZ()) / 2.0;
            if (Math.abs(hitVector.getZ()) > normalHalfDepth) {
                double zRatio = Math.min(1.0, Math.abs(hitVector.getZ()) / expandedHalfDepth);
                double adjustedZ = normalHalfDepth * zRatio;
                if (zRatio == 1.0) {
                    hitVector.Z(hitVector.getZ() > 0.0 ? normalHalfDepth : -normalHalfDepth);
                } else {
                    hitVector.Z(hitVector.getZ() > 0.0 ? adjustedZ : -adjustedZ);
                }
            }
            double normalTop = normalBounds.getMaxY() - entity.N();
            double normalBottom = normalBounds.getMinY() - entity.N();
            double expandedTop = expandedBounds.getMaxY() - entity.N();
            if (hitVector.getY() > normalTop) {
                double yRatio = Math.min(1.0, Math.abs(hitVector.getY()) / expandedTop);
                double adjustedY = normalTop * yRatio;
                if (yRatio == 1.0) {
                    hitVector.m(normalTop);
                } else {
                    hitVector.m(adjustedY);
                }
            } else if (hitVector.getY() < normalBottom) {
                double expandedBottom = normalBottom - expansion;
                double yRatio = hitVector.getY() / expandedBottom;
                if (yRatio >= 1.0) {
                    hitVector.m(normalBottom);
                } else {
                    hitVector.m(normalBottom * yRatio);
                }
            }
        }
    }

    public float getExpansionAmount() {
        if (!this.isEnabled()) {
            return 0.0f;
        }
        return ((Double)this.expandAmount.getValue()).floatValue();
    }

}
