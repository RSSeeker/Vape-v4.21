package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventFramePresent;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MRenderTarget;
import gg.vape.wrapper.impl.ForgeVersion;

/**
 * 26.1.x：在 RenderTarget.blitToScreen() 出口触发 EventFramePresent。
 *
 * blitToScreen() 通过 CommandEncoder.presentTexture 把主渲染目标
 * colorTexture 拷贝到屏幕默认 framebuffer 0，之后才 glfwSwapBuffers；
 * 在此出口做帧后处理（如动态模糊）时屏幕已含完整画面。
 */
public class RenderTargetBlitMappingTask
extends JavassistMappingTask {
    public RenderTargetBlitMappingTask() {
        super(RenderTargetBlitMappingTask.resolveRenderTargetClass());
    }

    private static Class<?> resolveRenderTargetClass() {
        Class<?> mappedClass = MappedClasses.DA;
        if (mappedClass != null) {
            return mappedClass;
        }
        if (!gg.vape.Vape.INSTANCE.isForgeAbsent()) {
            // Forge/NeoForge 运行时类名是 mojmap 名。
            try {
                return Class.forName(
                        "com.mojang.blaze3d.pipeline.RenderTarget");
            }
            catch (Throwable throwable) {
                // 继续尝试 srg 解析。
            }
        }
        // Vanilla（混淆）运行时通过 srg 映射解析。
        return gg.vape.runtime.NativeBridge.gvc(
                "com/mojang/blaze3d/pipeline/RenderTarget");
    }

    @Override
    public void transform() {
        if (!ForgeVersion.MC_1_20_1.d() || ForgeVersion.MC_26_2.d()) {
            return;
        }
        MRenderTarget mRenderTarget = Vape.INSTANCE.getMappings().renderTargetBlit;
        if (mRenderTarget == null || mRenderTarget.blitToScreenMethod == null
                || mRenderTarget.blitToScreenMethod.hasResolutionFailed()) {
            Vape.debugLog("RBT: blitToScreen mapping unavailable, skipping");
            return;
        }
        try {
            this.k(mRenderTarget.blitToScreenMethod, EventFramePresent.class, "");
            Vape.debugLog("RBT: injected EventFramePresent after blitToScreen");
        }
        catch (Throwable throwable) {
            Vape.logThrowable(throwable);
        }
    }
}
