package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderPlayer;

public class EventRenderPlayerPost
extends Event {
    private final RenderPlayer renderer;
    private final EntityPlayer player;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final MatrixStack matrixStack;
    private final float partialTicks;

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public EventRenderPlayerPost(Object rendererHandle, Object playerHandle, float partialTicks, Object matrixStackHandle) {
        this.renderer = new RenderPlayer(rendererHandle);
        this.player = new EntityPlayer(playerHandle);
        this.partialTicks = partialTicks;
        this.matrixStack = new MatrixStack(matrixStackHandle);
    }

    public EntityPlayer getEntityPlayer() {
        return this.player;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public EventRenderPlayerPost(Object rendererHandle, Object playerHandle, float partialTicks) {
        this.renderer = new RenderPlayer(rendererHandle);
        this.player = new EntityPlayer(playerHandle);
        this.partialTicks = partialTicks;
        this.matrixStack = null;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public EventRenderPlayerPost(Object playerHandle, Object matrixStackHandle, Object rendererHandle) {
        this.renderer = new RenderPlayer(rendererHandle);
        this.player = new EntityPlayer(playerHandle);
        this.matrixStack = new MatrixStack(matrixStackHandle);
        this.partialTicks = Minecraft.getTimer().renderPartialTicks();
    }

    public RenderPlayer getRenderer() {
        return this.renderer;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }
}
