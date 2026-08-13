package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.Wrapper;

public class EntityRenderer
extends Wrapper {
    public ActiveRenderInfo l() {
        return new ActiveRenderInfo(MEntityRenderer.Q(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public void Y(float f) {
        MEntityRenderer.i(EntityRenderer.vapeInstance.getMappings().RY, this.I, f);
    }

    public RenderBufferBridge V() {
        return new RenderBufferBridge(MEntityRenderer.E(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public void setPointedEntity(Entity entity) {
        if (ForgeVersion.MC_1_16_5.d()) {
            MEntityRenderer.T(EntityRenderer.vapeInstance.getMappings().RY, Minecraft.i(), entity.getObject());
            return;
        }
        MEntityRenderer.T(EntityRenderer.vapeInstance.getMappings().RY, this.I, entity.getObject());
    }

    public float b() {
        return MEntityRenderer.A(EntityRenderer.vapeInstance.getMappings().RY, this.I);
    }

    public void i(float f) {
        MEntityRenderer.u(EntityRenderer.vapeInstance.getMappings().RY, this.I, f);
    }

    public void B(MatrixStack matrixStack, float f) {
        MEntityRenderer.M(EntityRenderer.vapeInstance.getMappings().RY, this.I, matrixStack.getObject(), f);
    }

    public void J(ShaderGroup shaderGroup) {
        MEntityRenderer.D(EntityRenderer.vapeInstance.getMappings().RY, this.I, shaderGroup.getObject());
    }

    public Entity getPointedEntity() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return new Entity(MEntityRenderer.Z(EntityRenderer.vapeInstance.getMappings().RY, Minecraft.i()));
        }
        return new Entity(MEntityRenderer.Z(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public ShaderGroupState K() {
        return new ShaderGroupState(MEntityRenderer.P(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public void q(float f) {
        MEntityRenderer.N(EntityRenderer.vapeInstance.getMappings().RY, this.I, f);
    }


    public void m(float f, long l) {
        MEntityRenderer.X(EntityRenderer.vapeInstance.getMappings().RY, this.I, f, l);
    }

    public void B(double d) {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8().V();
            return;
        }
        MEntityRenderer.f(EntityRenderer.vapeInstance.getMappings().RY, this.I, d);
    }

    public FogRenderer getFogRenderer() {
        return new FogRenderer(MEntityRenderer.getFogRenderer(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public GlStateManager$FogState y() {
        return new GlStateManager$FogState(MEntityRenderer.b(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public void updateShaderGroupSize(int n, int n2) {
        MEntityRenderer.i(EntityRenderer.vapeInstance.getMappings().RY, this.I, n, n2);
    }

    public ShaderGroup L() {
        return new ShaderGroup(MEntityRenderer.t(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public void s(float f, int n) {
        SharedModuleControlClaims.renderPass.blockRender();
        if (ForgeVersion.MC_1_16_5.d()) {
            EntityPlayerSP entityPlayerSP;
            float f2;
            GameSettings gameSettings = Minecraft.gameSettings();
            this.i((float)gameSettings.v() * 16.0f);
            MatrixStack matrixStack = MatrixStack.A();
            Matrix4f matrix4f = this.l(this.l(), f, true);
            matrixStack.F().getMatrix().a(matrix4f);
            this.B(matrixStack, f);
            if (gameSettings.k()) {
                this.Z(matrixStack, f);
            }
            if ((f2 = MathUtil.round(f, (entityPlayerSP = Minecraft.thePlayer()).k$src$F$1u3g0zr(), entityPlayerSP.O$src$F$1to1sdn()) * gameSettings.f() * gameSettings.f()) > 0.0f) {
                int n2 = entityPlayerSP.i(StatusEffect.V()) ? 7 : 20;
                float f3 = 5.0f / (f2 * f2 + 5.0f) - f2 * 0.04f;
                f3 *= f3;
                Vector3f vector3f = Vector3f.create(0.0f, MathUtil.sqrt(2.0f) / 2.0f, MathUtil.sqrt(2.0f) / 2.0f);
                matrixStack.i(vector3f.rotationDegrees(((float)this.Q() + f) * (float)n2));
                matrixStack.S(1.0f / f3, 1.0f, 1.0f);
                float f4 = -((float)this.Q() + f) * (float)n2;
                matrixStack.i(vector3f.rotationDegrees(f4));
            }
            Matrix4f matrix4f2 = matrixStack.F().getMatrix();
            this.setShaderGroup(matrix4f2);
            SharedModuleControlClaims.renderPass.clearClaimed();
            return;
        }
        MEntityRenderer.D(EntityRenderer.vapeInstance.getMappings().RY, this.I, f, n);
        SharedModuleControlClaims.renderPass.clearClaimed();
    }

    public LightTexture l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8() {
        return new LightTexture(MEntityRenderer.O(EntityRenderer.vapeInstance.getMappings().RY, this.I));
    }

    public Matrix4f l(ActiveRenderInfo activeRenderInfo, float f, boolean bl) {
        Object object = MEntityRenderer.z(EntityRenderer.vapeInstance.getMappings().RY, this.I, activeRenderInfo.getObject(), f, bl);
        if (object == null && ForgeVersion.MC_26_1.d()) {
            object = MEntityRenderer.o(EntityRenderer.vapeInstance.getMappings().RY, this.I);
        }
        return new Matrix4f(object);
    }

    public void setUseShader(boolean bl) {
        if (ForgeVersion.MC_1_7_10.Y()) {
            MEntityRenderer.C(EntityRenderer.vapeInstance.getMappings().RY, this.I, bl);
        }
    }

    public void setShaderGroup(Matrix4f matrix4f) {
        if (ForgeVersion.MC_1_21_0.d()) {
            RenderSystem.L(matrix4f, Items.perspective());
            return;
        }
        MEntityRenderer.o(EntityRenderer.vapeInstance.getMappings().RY, this.I, matrix4f.getObject());
    }

    public int Q() {
        return MEntityRenderer.m(EntityRenderer.vapeInstance.getMappings().RY, this.I);
    }

    public void Z(MatrixStack matrixStack, float f) {
        MEntityRenderer.z(EntityRenderer.vapeInstance.getMappings().RY, this.I, matrixStack.getObject(), f);
    }

    public void V(float f) {
        MEntityRenderer.x(EntityRenderer.vapeInstance.getMappings().RY, this.I, f);
    }

    public void D(float f, long l) {
        if (ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_21_11.d()) {
                MEntityRenderer.a(EntityRenderer.vapeInstance.getMappings().RY, this.I, Minecraft.getTimer().getObject());
            }
            MEntityRenderer.q(EntityRenderer.vapeInstance.getMappings().RY, this.I, Minecraft.getTimer().getObject());
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            MEntityRenderer.h(EntityRenderer.vapeInstance.getMappings().RY, this.I, f, l, MatrixStack.A().getObject());
            return;
        }
        MEntityRenderer.F(EntityRenderer.vapeInstance.getMappings().RY, this.I, f, l);
    }

    public boolean isUseShader() {
        if (ForgeVersion.MC_1_7_10.Y()) {
            return MEntityRenderer.c$src$Z$4ble0w(EntityRenderer.vapeInstance.getMappings().RY, this.I);
        }
        return false;
    }

    public void r(float f) {
        MEntityRenderer.j(EntityRenderer.vapeInstance.getMappings().RY, this.I, f);
    }

    public static Object[] getShaderResourceLocations() {
        return MEntityRenderer.k(EntityRenderer.vapeInstance.getMappings().RY);
    }

    public void O(double d) {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8().X();
            return;
        }
        MEntityRenderer.V(EntityRenderer.vapeInstance.getMappings().RY, this.I, d);
    }

    public static Object s$src$Ljava_lang_Object_$1ecvhy8() {
        return MEntityRenderer.G(EntityRenderer.vapeInstance.getMappings().RY);
    }

    public EntityRenderer(Object object) {
        super(object);
    }

    public float s() {
        return MEntityRenderer.c(EntityRenderer.vapeInstance.getMappings().RY, this.I);
    }
}

