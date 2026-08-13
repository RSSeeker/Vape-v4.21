package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipboardUtil {
    private static int clipboardBackend = 0;

    public static void setText(String string) {
        try {
            if (clipboardBackend == 0) {
                ClipboardUtil.detectClipboardBackend();
            }
            switch (clipboardBackend) {
                case 1: {
                    StringSelection stringSelection = new StringSelection(string);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, stringSelection);
                }
                case 2: {
                    Minecraft.r().y(string);
                }
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public static String getText() {
        try {
            if (clipboardBackend == 0) {
                ClipboardUtil.detectClipboardBackend();
            }
            switch (clipboardBackend) {
                case 1: {
                    Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                    if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        return (String)transferable.getTransferData(DataFlavor.stringFlavor);
                    }
                }
                case 2: {
                    Minecraft.r().F();
                }
            }
            return "";
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
            return "";
        }
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    private static void detectClipboardBackend() {
        int selectedBackend;
        boolean headless = GraphicsEnvironment.isHeadless();
        clipboardBackend = selectedBackend = headless ? 2 : 1;
    }
}
