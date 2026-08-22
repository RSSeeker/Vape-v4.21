package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

/**
 * 帧呈现事件（26.x 新渲染架构）。
 *
 * 在 RenderTarget.blitToScreen() 出口触发：此时主渲染目标（colorTexture）
 * 已通过 CommandEncoder.presentTexture 拷贝到屏幕默认 framebuffer 0，
 * 但 glfwSwapBuffers 尚未执行——是帧后处理（如动态模糊）读取/覆盖
 * 屏幕画面的正确时机。
 */
public class EventFramePresent
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();

    public EventFramePresent() {
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}
