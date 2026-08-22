package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * 26.x-specific: the game GUI is rendered by GuiRenderer.render(); draw the
 * Vape HUD/ClickGUI right after it, once per frame, on whatever target the
 * game leaves bound (the main render target during the GUI pass).
 */
public class GuiRendererRenderTickMappingTask
extends JavassistMappingTask {
    public GuiRendererRenderTickMappingTask() {
        super(MappedClasses.w);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Ca.z;
        if (mappingMethod == null || mappingMethod.hasResolutionFailed()) {
            return;
        }
        CtBehavior ctBehavior = this.F(mappingMethod);
        if (ctBehavior == null) {
            return;
        }
        try {
            ctBehavior.insertAfter("{"
                    + EventRender2DStaticCallback.class.getName() + "#call();}");
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }
}