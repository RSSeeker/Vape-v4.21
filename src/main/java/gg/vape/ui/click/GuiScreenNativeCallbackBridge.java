package gg.vape.ui.click;

import gg.vape.Vape;
import gg.vape.input.InputEventDispatcher;
import gg.vape.module.none.ClientSettings;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class GuiScreenNativeCallbackBridge {
    private static boolean clickGuiRenderLogged;
    public static void keyTyped(Object screen, char typedChar, int keyCode) {
    }

    public static void h(long timestamp) {
        InputEventDispatcher.getInstance().setWindowHandle(timestamp);
    }

    public static void updateScreen(Object screen) {
    }

    public static void mouseClicked(Object screen, int mouseX, int mouseY, int button) {
        ClientSettings.UI_EXECUTOR.execute(() -> {
            ClientSettings clientSettings = Vape.INSTANCE.getModManager().getMod(ClientSettings.class);
            if (clientSettings != null && !clientSettings.inputEnabled) {
                clientSettings.handleMouseButton(button, mouseX, mouseY);
            }
        });
    }

    public static void drawScreen(Object screen, int mouseX, int mouseY, float partialTicks) {
        ClientSettings clientSettings = Vape.INSTANCE.getModManager().getMod(ClientSettings.class);
        if (!clientSettings.inputEnabled) {
            clientSettings.renderGui();
            if (ForgeVersion.MC_26_2.d() && !clickGuiRenderLogged) {
                clickGuiRenderLogged = true;
                try {
                    RenderBatchManager batchManager = RenderBatchManager.getInstance();
                    Vape.debugLog("ClickGUI 26.2: render callback queued "
                            + batchManager.guiBatches.size() + " batch group(s), framebuffer="
                            + batchManager.getTargetFramebufferId());
                }
                catch (Exception exception) {
                    Vape.logThrowable(exception);
                }
            }
        }
    }

    public static void initGui(Object screen) {
    }

    public static void handleMouseInput(Object screen) {
    }

    public static void mouseMovedOrUp(Object screen, int mouseX, int mouseY, int button) {
    }


    public static boolean onNotification(int code, long first, long second) {
        return InputEventDispatcher.getInstance().dispatch(code, first, second);
    }
}

