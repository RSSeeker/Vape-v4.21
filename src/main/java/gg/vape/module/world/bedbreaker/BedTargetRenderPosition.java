package gg.vape.module.world.bedbreaker;

import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.RenderManager;
import java.util.Objects;

public class BedTargetRenderPosition {
    private final double z;
    private final double x;
    private final double y;

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public double getRelativeZ() {
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        return this.z - renderZ;
    }

    public int getBlockY() {
        return MathUtil.floor(this.y);
    }

    public double getRelativeY() {
        double renderY = RenderManager.getInterpolatedRenderPosY();
        return this.y - renderY;
    }

    public double getY() {
        return this.y;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        BedTargetRenderPosition bedTargetRenderPosition = (BedTargetRenderPosition)object;
        return this.x == bedTargetRenderPosition.x && this.y == bedTargetRenderPosition.y && this.z == bedTargetRenderPosition.z;
    }

    public double getX() {
        return this.x;
    }

    public double getRelativeX() {
        double renderX = RenderManager.getInterpolatedRenderPosX();
        return this.x - renderX;
    }

    public int getBlockZ() {
        return MathUtil.floor(this.z);
    }

    public int getBlockX() {
        return MathUtil.floor(this.x);
    }

    public BedTargetRenderPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getZ() {
        return this.z;
    }


    public BedTargetRenderPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

