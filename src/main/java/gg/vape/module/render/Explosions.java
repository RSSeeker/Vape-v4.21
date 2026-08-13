package gg.vape.module.render;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.pearl.PearlEspEnderPearlEntityWrapper;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.glu.GluSphere;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import org.lwjgl.opengl.GL11;

public class Explosions
extends Mod {
    public final BooleanValue showBlastRing = BooleanValue.create(this, "Blast ring", true, "Shows the blast radius where blocks will be broken");

    @EventHandler
    public void onRender3D(EventRender3D event) {
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        boolean blendEnabled = GL11.glIsEnabled(3042);
        double blockDamageRadius = 4.0;
        double entityDamageRadius = 8.0;
        for (Object e : Minecraft.theWorld().z()) {
            GluSphere gluSphere;
            if (!MappedClasses.qM.isAssignableFrom(e.getClass())) continue;
            PearlEspEnderPearlEntityWrapper pearl = new PearlEspEnderPearlEntityWrapper(e);
            RenderUtil.d();
            OpenGlBackendHolder.backend.enableCapability(3042);
            OpenGlBackendHolder.backend.disableCapability(3553);
            GL11.glBlendFunc((int)770, (int)771);
            float partialTicks = event.getTicks();
            double pearlX = pearl.M() + (pearl.z() - pearl.M()) * (double)partialTicks;
            double pearlY = pearl.W() + (pearl.N() - pearl.W()) * (double)partialTicks;
            double pearlZ = pearl.m$src$D$fwnne5() + (pearl.h() - pearl.m$src$D$fwnne5()) * (double)partialTicks;
            double distanceToPlayer = Minecraft.thePlayer().i(pearlX, pearlY, pearlZ);
            OpenGlBackendHolder.backend.translate(pearlX - cameraX, pearlY - cameraY, pearlZ - cameraZ);
            float pulseProgress = ((float)(pearl.getAgeTicks() % 5) - partialTicks + 1.0f) / 5.0f;
            float pulseAlpha = pearl.getAgeTicks() / 5 % 2 == 0 ? 1.0f : pulseProgress * pulseProgress;
            int alpha = Math.max(Math.min((int)(255.0f * pulseAlpha), 255), 0);
            if (this.showBlastRing.getEffectiveValue().booleanValue()) {
                OpenGlBackendHolder.backend.setColor(255.0f, 255.0f, 0.0f, (int)((float)alpha * 0.75f));
                gluSphere = new GluSphere();
                gluSphere.setDrawStyle(GluSphere.DRAW_LINE);
                gluSphere.draw((float)blockDamageRadius, 24, 24);
            }
            OpenGlBackendHolder.backend.setColor(255.0f, 0.0f, 0.0f, (int)((float)alpha * 0.4f));
            gluSphere = new GluSphere();
            gluSphere.setDrawStyle(GluSphere.DRAW_FILL);
            gluSphere.setOrientation(distanceToPlayer < entityDamageRadius ? GluSphere.ORIENTATION_INSIDE : GluSphere.ORIENTATION_OUTSIDE);
            gluSphere.draw((float)entityDamageRadius, 32, 32);
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(3042);
            GL11.glPopMatrix();
        }
        if (blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
    }


    public Explosions() {
        super("Explosions", 11534100, Category.RENDER, "Shows the explosion radius of TNT");
        this.addValue(this.showBlastRing);
    }
}

