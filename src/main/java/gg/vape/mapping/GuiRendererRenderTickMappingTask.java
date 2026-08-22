package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * 26.x-specific: the game GUI is rendered by GuiRenderer.render(). Inject the
 * HUD modules before it (so they sit below the game HUD) and the ClickGUI
 * after it (so it sits above the game HUD), once per frame, on the main render
 * target that participates in the composite.
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
            ctBehavior.insertBefore("{"
                    + EventRender2DHudCallback.class.getName() + "#call();}");
            ctBehavior.insertAfter("{"
                    + EventRender2DGuiCallback.class.getName() + "#call();}");
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }
}
