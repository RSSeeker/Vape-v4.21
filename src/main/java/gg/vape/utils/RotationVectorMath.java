package gg.vape.utils;

import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.FastAtanMath;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.Vec3;

public class RotationVectorMath {
    public static final double T = Math.PI / 180;
    public static final double e = 57.29577951308232;

    public static Vec3 l(RotationAngles rotationAngles) {
        float f = MathUtil.cos(-rotationAngles.getYaw() * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = MathUtil.sin(-rotationAngles.getYaw() * ((float)Math.PI / 180) - (float)Math.PI);
        float f3 = -MathUtil.cos(-rotationAngles.getPitch() * ((float)Math.PI / 180));
        float f4 = MathUtil.sin(-rotationAngles.getPitch() * ((float)Math.PI / 180));
        return Vec3.create(f2 * f3, f4, f * f3);
    }

    public static RotationAngles H(Vec3 vec3, Vec3 vec32, float f, boolean bl) {
        double[] dArray = new double[]{vec3.getX() - vec32.getX(), vec3.getY() - vec32.getY(), vec3.getZ() - vec32.getZ()};
        double d = Math.atan2(dArray[0], -dArray[2]);
        double d2 = Math.sqrt(dArray[0] * dArray[0] + dArray[2] * dArray[2]);
        double d3 = Math.atan2(dArray[1], d2);
        double d4 = d * 57.29577951308232;
        double d5 = (int)((d4 - (double)f) % 360.0);
        d5 = Math.abs(MathUtil.wrapAngleTo180(d5));
        if (bl && d5 > 90.0) {
            d3 = 90.0;
        }
        return new RotationAngles((float)(d * 57.29577951308232), (float)(d3 * 57.29577951308232));
    }


    public static RotationAngles l(RotationAngles rotationAngles, RotationAngles rotationAngles2) {
        float f = MathUtil.wrapAngleTo180(rotationAngles2.getYaw()) - MathUtil.wrapAngleTo180(rotationAngles.getYaw());
        if (f <= -180.0f) {
            f += 360.0f;
        } else if (f > 180.0f) {
            f -= 360.0f;
        }
        return new RotationAngles(f, rotationAngles2.getPitch() - rotationAngles.getPitch());
    }

    public static RotationAngles d(Vec3 vec3, Vec3 vec32, float f, float f2) {
        double d = vec3.getX() - vec32.getX();
        double d2 = vec3.getY() - vec32.getY();
        double d3 = vec3.getZ() - vec32.getZ();
        double d4 = (double)FastAtanMath.atan2Approximation((float)d, (float)(-d3)) * 57.29577951308232;
        double d5 = Math.sqrt(d * d + d3 * d3);
        double d6 = (double)FastAtanMath.atan2Approximation((float)d2, (float)d5) * 57.29577951308232;
        float f3 = RotationManager.INSTANCE.getMouseSensitivity();
        float f4 = f3 * 0.6f + 0.2f;
        float f5 = f4 * f4 * f4 * 8.0f;
        float f6 = (float)(0.0 + (double)f5 * 0.15);
        float f7 = MathUtil.wrapAngleTo180((float)(d4 - (double)f));
        int n = Math.round(f7 / f6);
        float f8 = MathUtil.wrapAngleTo180((float)(d6 - (double)f2));
        int n2 = Math.round(f8 / f6);
        return new RotationAngles(f + f6 * (float)n, f2 + f6 * (float)n2);
    }
}
