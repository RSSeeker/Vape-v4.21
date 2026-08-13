package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderItemTextBridge;

public class ItemStackRenderUtils {
    private static String legacyStatus;

    public static void renderItemOverlay(RenderItemTextBridge renderItemTextBridge, ItemStack itemStack, int x, int y) {
        Vape.INSTANCE.getMappingsMapperCompat().DI.M(renderItemTextBridge.getObject(), itemStack.getObject(), x, y);
    }

    public static void renderItemOverlay(ItemStack itemStack, int x, int y) {
        if (ForgeVersion.MC_1_20_6.d()) {
            MatrixStack matrixStack = MatrixStack.A();
            matrixStack.H();
            matrixStack.i(BufferedGuiRenderPrimitives.matrixStack.peek().toMinecraftMatrix());
            RenderItemTextBridge renderItemTextBridge = RenderItemTextBridge.t(matrixStack);
            ItemStackRenderUtils.renderItemOverlay(renderItemTextBridge, itemStack, x, y);
        } else if (ForgeVersion.MC_1_7_10.L()) {
            RenderItem renderItem = RenderItem.d();
            renderItem.c(Minecraft.getFontRenderer(), Minecraft.getTextureManager(), itemStack, x, y);
        } else {
            RenderItem renderItem = Minecraft.v();
            renderItem.c(Minecraft.getFontRenderer(), Minecraft.getTextureManager(), itemStack, x, y);
        }
    }

    public static String getLegacyStatus() {
        return legacyStatus;
    }

    public static void setLegacyStatus(String status) {
        legacyStatus = status;
    }

    static {
        if (ItemStackRenderUtils.getLegacyStatus() != null) {
            ItemStackRenderUtils.setLegacyStatus("kN9BPb");
        }
    }
}
