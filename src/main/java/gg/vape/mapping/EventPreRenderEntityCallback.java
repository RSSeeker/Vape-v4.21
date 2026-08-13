package gg.vape.mapping;

import gg.vape.event.impl.EventPreRenderLiving;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.RenderStateBridge;

public class EventPreRenderEntityCallback
implements InsertedEventCallback {
    private final double g;
    private final Object M;
    private final double b;
    private final double W;
    private final Object q;

    public static void call(Object object, Object object2, Object object3, Object object4, int n) {
        boolean bl = new EventPreRenderEntityCallback(object, object3).fire();
        if (bl) {
            RenderStateBridge renderStateBridge = new RenderStateBridge(object2);
            renderStateBridge.Z(new ITextComponent(null));
        }
    }

    public static void call(Object object, Object object2, float f, Object object3, Object object4, int n) {
        Object object5 = new Render(object3).a(new Entity(object), f).getObject();
        EventPreRenderEntityCallback.call(object, object5, object2, object4, n);
    }

    @Override
    public boolean fire() {
        if (EventPreRenderLiving.getEventListeners().hasListeners()) {
            EventPreRenderLiving eventPreRenderLiving = new EventPreRenderLiving(this.M, this.W, this.g, this.b, this.q);
            return eventPreRenderLiving.fire();
        }
        return false;
    }

    public EventPreRenderEntityCallback(Object object, double d, double d2, double d3) {
        this.M = object;
        this.q = null;
        this.W = d;
        this.g = d2;
        this.b = d3;
    }

    public EventPreRenderEntityCallback(Object object, Object object2) {
        this.q = object2;
        this.M = object;
        Entity entity = new Entity(object);
        this.W = entity.M() + (entity.z() - entity.M()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosX();
        this.g = entity.W() + (entity.N() - entity.W()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosY();
        this.b = entity.m$src$D$fwnne5() + (entity.h() - entity.m$src$D$fwnne5()) * (double)Minecraft.getTimer().renderPartialTicks() - RenderManager.getInterpolatedRenderPosZ();
    }

    public static void call(Object object) {
        new EventPreRenderEntityCallback(object, null).fire();
    }
}

