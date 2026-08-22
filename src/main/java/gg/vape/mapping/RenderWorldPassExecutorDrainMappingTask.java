package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderWorldPassExecutorDrain;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class RenderWorldPassExecutorDrainMappingTask
extends JavassistMappingTask {
    private static final String c;

    void V() {
        // 注入到 GameRenderer.update/render 入口。26.2 fabric 该入口 GL 上下文
        // 可能尚未绑定，EventRenderWorldPassExecutorDrain 内部做 GL 就绪检测并
        // 延迟任务，避免 FATAL ERROR 与死锁。
        this.c(Vape.INSTANCE.getMappings().RY.J, EventRenderWorldPassExecutorDrain.class, c);
    }

    public RenderWorldPassExecutorDrainMappingTask() {
        super(MappedClasses.FW);
    }

    @Override
    public void transform() {
        this.V();
    }

    static {
        try {
            c = "$1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
