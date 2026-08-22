package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

/**
 * RenderTarget（com/mojang/blaze3d/pipeline/RenderTarget）映射。
 *
 * 26.x 新渲染架构下，Minecraft.renderFrame 每帧调用主渲染目标的
 * blitToScreen()，把 colorTexture 呈现到屏幕默认 framebuffer 0；
 * 在其出口注入帧呈现事件（EventFramePresent）用于帧后处理。
 *
 * MappedClasses.DA 仅在 1.21.10+ 赋值，1.21.0-1.21.6 需自行解析
 * RenderTarget 类（运行时混淆名由 srg 映射翻译）。
 */
public class MRenderTarget
extends Mapping {
    public MappingMethod blitToScreenMethod;
    private static boolean renderTargetControlFlowState;

    public static boolean getRenderTargetControlFlowState() {
        return renderTargetControlFlowState;
    }

    public static boolean getDisabledControlFlowState() {
        boolean bl = MRenderTarget.getRenderTargetControlFlowState();
        return false;
    }

    public static void setRenderTargetControlFlowState(boolean state) {
        renderTargetControlFlowState = state;
    }

    public MRenderTarget() {
        this(MRenderTarget.getDisabledControlFlowState());
    }

    private MRenderTarget(boolean bl) {
        super(MRenderTarget.resolveRenderTargetClass());
        if (bl) {
            this.registerBlitToScreen();
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
            return;
        }
        this.registerBlitToScreen();
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

    private void registerBlitToScreen() {
        if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_26_2.v()) {
            // 1.21.4+（含 1.21.11 / 26.1）：新渲染架构，RenderTarget.blitToScreen()
            // 无参，每帧把主渲染目标呈现到屏幕。
            this.blitToScreenMethod = this.Y("blitToScreen", true, Void.TYPE, new Class[0]);
        } else if (ForgeVersion.MC_1_21_0.d() && ForgeVersion.MC_1_21_4.v()) {
            // 1.21.0-1.21.3：blitToScreen(int width, int height)。
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
            this.blitToScreenMethod = this.Y("blitToScreen", true, Void.TYPE, classArray);
        } else if (ForgeVersion.MC_1_20_1.d() && ForgeVersion.MC_1_21_0.v()) {
            // 1.20.1-1.20.6：blitToScreen(int width, int height)，srg 名
            // m_83938_ 由成员名映射表翻译。
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
            this.blitToScreenMethod = this.Y("blitToScreen", true, Void.TYPE, classArray);
        }
    }

    static {
        MRenderTarget.setRenderTargetControlFlowState(true);
    }
}
