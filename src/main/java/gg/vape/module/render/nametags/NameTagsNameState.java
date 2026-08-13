package gg.vape.module.render.nametags;

import gg.vape.utils.EnchantmentUtil;
import gg.vape.utils.ItemStackFingerprint;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemStackRenderUtils;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Quaternion;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderItemFontBridge;
import gg.vape.wrapper.impl.RenderItemTextBridge;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.SharedMonsterAttributes;
import gg.vape.wrapper.impl.TagCompound;
import gg.vape.wrapper.impl.Tessellator;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class NameTagsNameState {
    private final double width;
    private static final String ENCH_TAG = "ench";
    private final double height;
    private final NameTagsFramebufferState framebufferState;

    public double getHeight() {
        return this.height;
    }

    private static void drawEnchantString(FontRenderer fontRenderer, String text, int x, int y, double scale, double alpha) {
        int color = ((int)alpha & 0xFF) << 24 | 0xFFFFFF;
        OpenGlBackendHolder.backend.pushMatrix();
        OpenGlBackendHolder.backend.scale(scale, scale, scale);
        OpenGlBackendHolder.backend.translate(0.0f, 0.0f, 2000.0f);
        fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        OpenGlBackendHolder.backend.popMatrix();
    }

    public NameTagsNameState(NameTagsFramebufferState framebufferState, double width, double height) {
        this.framebufferState = framebufferState;
        this.width = width;
        this.height = height;
    }

    public void render(double x, double y, int stackCount, double[] durabilityFractions, @Nullable MatrixStack matrixStack, RenderManager renderManager, boolean deferred) {
        int index;
        if (this.framebufferState == null || !this.framebufferState.isValid()) {
            return;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableAlpha();
        GuiRenderPrimitives.S(x, y, this.width, this.height + 2.0, this.framebufferState.getColorTexture(), deferred);
        if (stackCount != 0) {
            int textColor = -1;
            if (ForgeVersion.MC_1_16_5.d()) {
                if (matrixStack == null || matrixStack.isNull()) {
                    matrixStack = MatrixStack.A();
                }
                matrixStack.H();
                Quaternion quaternion = renderManager.getCameraOrientation();
                matrixStack.i(quaternion);
                matrixStack.i(Quaternion.fromEulerAngles(180.0f, 0.0f, 180.0f, true));
                int packedLight = 0xF000F0;
                if (ForgeVersion.MC_1_20_6.d()) {
                    Minecraft.getFontRenderer().B(stackCount + "", (float)(x + 7.0), (float)(y + 9.0), textColor, true, null, SharedMonsterAttributes.V(), null);
                } else {
                    ScorePlayerTeamTextComponent textComponent = ScorePlayerTeamTextComponent.B(stackCount + "");
                    RenderItemFontBridge fontBuffer = RenderItemFontBridge.V(Tessellator.getInstance().getWorldRenderer());
                    Minecraft.getFontRenderer().Z(textComponent, (float)(x + 7.0), (float)(y + 9.0), textColor, true, matrixStack.F().getMatrix(), fontBuffer, true, 0, packedLight);
                    fontBuffer.q();
                }
                matrixStack.U();
            } else {
                Minecraft.getFontRenderer().drawStringWithShadow(stackCount + "", x + 6.0, y + 8.0, textColor);
            }
        }
        double itemOffset = 0.0;
        for (index = 0; index < durabilityFractions.length; ++index) {
            double durabilityUsed = durabilityFractions[index];
            double durabilityRemaining;
            if (durabilityUsed > 0.0 && durabilityUsed < 1.0 && (durabilityRemaining = 1.0 - durabilityUsed) < 1.0) {
                GuiRenderPrimitives.U = false;
                int remainingColor = (int)Math.round(255.0 - durabilityUsed * 255.0);
                if (GuiRenderPrimitives.d()) {
                    BufferedRenderPrimitives.fillRect(x + 2.0 + itemOffset, y + 13.0, 13.0, 2.0, Color.BLACK);
                    BufferedRenderPrimitives.fillRect(x + 2.0 + itemOffset, y + 13.0, 12.0, 1.0, new Color((255 - remainingColor) / 4, 64, 0, 255));
                    BufferedRenderPrimitives.fillRect(x + 2.0 + itemOffset, y + 13.0, 13.0 * durabilityRemaining, 1.0, RenderUtils.S((float)durabilityRemaining));
                } else {
                    GuiRenderPrimitives.C(x + 2.0 + itemOffset, y + 13.0, 13.0, 2.0, Color.BLACK);
                    GuiRenderPrimitives.C(x + 2.0 + itemOffset, y + 13.0, 12.0, 1.0, new Color((255 - remainingColor) / 4, 64, 0, 255));
                    GuiRenderPrimitives.C(x + 2.0 + itemOffset, y + 13.0, 13.0 * durabilityRemaining, 1.0, RenderUtils.S((float)durabilityRemaining));
                }
                GuiRenderPrimitives.U = true;
            }
            itemOffset += 18.0;
        }
    }

    private static void renderItemIcon(ItemStack itemStack, float x, float totalWidth) {
        if (GuiRenderPrimitives.d()) {
            if (ForgeVersion.MC_1_21_10.d()) {
                GuiRenderPrimitives.g(itemStack, 1.0, x, -2.0, true);
                return;
            }
            boolean depthEnabled = GL11.glIsEnabled((int)2929);
            boolean blendEnabled = GL11.glIsEnabled((int)3042);
            if (!depthEnabled) {
                OpenGlBackendHolder.backend.enableCapability(2929);
            }
            if (!blendEnabled) {
                OpenGlBackendHolder.backend.enableCapability(3042);
            }
            MatrixStack matrixStack = MatrixStack.A();
            matrixStack.H();
            float guiScale = (float)Minecraft.p().k(Minecraft.gameSettings().T(), false) / 2.0f;
            float inverseGuiScale = 1.0f / guiScale;
            matrixStack.S(inverseGuiScale, inverseGuiScale, inverseGuiScale);
            matrixStack.S((float)Minecraft.J() / totalWidth / 2.0f, (float)Minecraft.h() / 36.0f, 0.0f);
            if (ForgeVersion.MC_1_20_6.d()) {
                ItemStackRenderUtils.renderItemOverlay(RenderItemTextBridge.t(matrixStack), itemStack, (int)x, 0);
            } else {
                RenderItem renderItem = Minecraft.v();
                renderItem.a(itemStack, (int)x, -2, matrixStack);
            }
            if (!depthEnabled) {
                OpenGlBackendHolder.backend.disableCapability(2929);
            }
            if (!blendEnabled) {
                OpenGlBackendHolder.backend.disableCapability(3042);
            }
            return;
        }
        GuiRenderPrimitives.g(itemStack, 1.0, x, -2.0, true);
    }

    public static NameTagsNameState create(EntityPlayer player) {
        ItemStack[] equipment = ItemStackFingerprint.T$src$ALgg_vape_wrapper_impl_ItemStack_$f6ukg1(player);
        int totalWidth = 0;
        for (ItemStack equipmentStack : equipment) {
            if (equipmentStack == null) continue;
            totalWidth += 18;
        }
        GuiRenderPrimitives.Y();
        totalWidth -= 2;
        NameTagsFramebufferState framebufferState = new NameTagsFramebufferState(0, -2, totalWidth, 18);
        if (GuiRenderPrimitives.d()) {
            RenderBatchManager.getInstance().flushGuiBatches(0.0f);
        }
        framebufferState.beginRendering();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        FontRenderer fontRenderer = Minecraft.getFontRenderer();
        int itemOffset = 0;
        for (ItemStack itemStack : equipment) {
            TagCompound tagCompound;
            if (itemStack == null) continue;
            ItemStack renderStack = itemStack.k();
            if (!ForgeVersion.MC_1_20_6.d() && (tagCompound = new TagCompound(renderStack.l())).isNotNull()) {
                tagCompound.getTagMap().remove(ENCH_TAG);
            }
            NameTagsNameState.renderItemIcon(renderStack, itemOffset, totalWidth);
            int enchantOffset = 0;
            double enchantScale = 0.7;
            double inverseEnchantScale = 1.0 / enchantScale;
            for (String enchantText : EnchantmentUtil.E(itemStack)) {
                NameTagsNameState.drawEnchantString(fontRenderer, enchantText, (int)((double)itemOffset * inverseEnchantScale), (int)((double)enchantOffset * inverseEnchantScale) - 2, enchantScale, 1.0);
                enchantOffset += 6;
            }
            itemOffset += 18;
        }
        framebufferState.endRendering();
        GuiRenderPrimitives.D();
        return new NameTagsNameState(framebufferState, totalWidth, 16.0);
    }

    public double getWidth() {
        return this.width;
    }

    public NameTagsFramebufferState getFramebufferState() {
        return this.framebufferState;
    }

}

