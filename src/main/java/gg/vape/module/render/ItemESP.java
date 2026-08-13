package gg.vape.module.render;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventGuiOpen;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.module.render.item.ItemESPGroup;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.ActiveRenderInfo;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityItem;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Matrix4f;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Quaternion;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.RenderItemFontBridge;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.SharedMonsterAttributes;
import gg.vape.wrapper.impl.Tessellator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ItemESP
extends Mod {
    private final Color backgroundColor;
    private final LimitValue allowedItems;
    private final List<ItemESPGroup> groups;
    private final BooleanValue autoScale;
    private final NumberValue scale;
    private final BooleanValue whitelistOnly;
    private final BooleanValue groupItems;
    private final BooleanValue showDistance = BooleanValue.create(this, "Distance", false, "Shows the distance of the item.");

    @EventHandler
    public void onRender3D(EventRender3D event) {
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        EntityPlayerSP player = Minecraft.thePlayer();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
        RenderHelper.e();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableLighting();
        for (ItemESPGroup group : this.groups) {
            if (group.isEmpty()) continue;
            double renderX = group.previousX + (group.currentX - group.previousX) * (double)event.getTicks() - cameraX;
            double renderY = group.previousY + (group.currentY - group.previousY) * (double)event.getTicks() - cameraY;
            double renderZ = group.previousZ + (group.currentZ - group.previousZ) * (double)event.getTicks() - cameraZ;
            this.renderGroupLabel(player, group, renderX, renderY, renderZ, ForgeVersion.MC_1_16_5.d() ? event.getMatrixStack() : null);
        }
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        if (!blendEnabled) {
            GlStateManager.disableBlend();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.s();
        Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
    }

    private void mergeNearbyGroups(EntityPlayerSP player) {
        for (int i = 0; i < this.groups.size(); ++i) {
            for (int j = i + 1; j < this.groups.size(); ++j) {
                ItemESPGroup first = this.groups.get(i);
                ItemESPGroup second = this.groups.get(j);
                EntityItem firstItem = new EntityItem(first.getRepresentativeHandle());
                EntityItem secondItem = new EntityItem(second.getRepresentativeHandle());
                double firstDistance = player.getDistanceToEntity(firstItem);
                double secondDistance = player.getDistanceToEntity(secondItem);
                double averageDistance = (firstDistance + secondDistance) / 2.0;
                double mergeRadius = Math.max(1.5, averageDistance / 5.0);
                double deltaX = first.currentX - second.currentX;
                double deltaY = first.currentY - second.currentY;
                double deltaZ = first.currentZ - second.currentZ;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                if (!(distance <= mergeRadius)) continue;
                first.merge(second);
                this.groups.remove(j);
                --j;
            }
        }
    }

    public ItemESP() {
        super("ItemESP", 48779, Category.RENDER, "Renders tags on dropped items.");
        this.groupItems = BooleanValue.create(this, "Group Items", false, "Groups items into easier to read tags.");
        this.autoScale = BooleanValue.create(this, "Auto Scale", true, "Automatically scales up tags\nthe further the distance.");
        this.whitelistOnly = BooleanValue.create(this, "Whitelist Only", false, "Only renders whitelisted items.");
        this.allowedItems = LimitValue.create(this, "itemesp-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Collections.emptyList());
        this.scale = NumberValue.create((Object)this, "Scale", "#.#", "", 0.1, 1.0, 1.5, 0.1);
        this.backgroundColor = new Color(20, 20, 20, 64);
        this.groups = new ArrayList<ItemESPGroup>();
        this.groupItems.addChangeListener(this::onGroupItemsChanged);
        this.whitelistOnly.addDependentValues(this.allowedItems);
        this.addValue(this.showDistance, this.groupItems, this.autoScale, this.scale, this.whitelistOnly, this.allowedItems);
    }

    public boolean isGroupingEnabled() {
        return this.groupItems.getEffectiveValue().booleanValue();
    }

    private void renderGroupLabel(EntityPlayerSP player, ItemESPGroup group, double renderX, double renderY, double renderZ, MatrixStack matrixStack) {
        EntityItem entityItem = new EntityItem(group.getRepresentativeHandle());
        List<ItemStack> stacks = group.getStacks();
        int maxHalfWidth = 0;
        String[] lines = new String[stacks.size()];
        FontRenderer fontRenderer = Minecraft.getFontRenderer();
        for (int index = 0; index < stacks.size(); ++index) {
            ItemStack stack = stacks.get(index);
            String line = stack.x();
            if (this.showDistance.getEffectiveValue().booleanValue() && index == 0) {
                line = ClientSettings.FORMAT_CODE + "a[" + ClientSettings.FORMAT_CODE + "f" + (int)player.getDistanceToEntity(entityItem) + ClientSettings.FORMAT_CODE + "a]" + ClientSettings.FORMAT_CODE + "r " + line;
            }
            if (stack.P() > 1 && stack.t() > 1) {
                line = line + ClientSettings.FORMAT_CODE + "r x" + stack.t();
            }
            maxHalfWidth = Math.max(fontRenderer.getStringWidth(line) / 2, maxHalfWidth);
            lines[index] = line;
        }
        int textColor = -1;
        float labelScale = (float)(0.03333335 * (Double)this.scale.getValue());
        if (this.autoScale.getEffectiveValue().booleanValue()) {
            float distance = player.getDistanceToEntity(entityItem);
            float distanceScale = distance / 5.0f <= 2.0f ? 2.0f : distance / 5.0f;
            labelScale = (float)(0.01666666753590107 * ((double)distanceScale * (Double)this.scale.getValue()));
        }
        RenderUtil.d();
        float viewPitch = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderPitch() : Minecraft.D().getPlayerViewX();
        float viewYaw = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderYaw() : Minecraft.D().getPlayerViewY();
        if (ForgeVersion.MC_1_16_5.d()) {
            if (Minecraft.gameSettings().x() == 0) {
                OpenGlBackendHolder.backend.translate(renderX, renderY + 0.75, renderZ);
                OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-viewPitch, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-viewYaw, -1.0f, 0.0f, 0.0f);
            } else {
                ActiveRenderInfo activeRenderInfo = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l();
                double cameraOffsetX = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosX() - activeRenderInfo.o().getX();
                double cameraOffsetY = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosY() - activeRenderInfo.o().getY();
                double cameraOffsetZ = GuiRenderPrimitives.d() ? 0.0 : RenderManager.getInterpolatedRenderPosZ() - activeRenderInfo.o().getZ();
                OpenGlBackendHolder.backend.translate(renderX + cameraOffsetX, renderY + cameraOffsetY + 0.75, renderZ + cameraOffsetZ);
                OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(-viewPitch, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(viewYaw, 1.0f, 0.0f, 0.0f);
            }
        } else {
            OpenGlBackendHolder.backend.translate(renderX, renderY + 0.75, renderZ);
            OpenGlBackendHolder.backend.setNormal(0.0f, 1.0f, 0.0f);
            if (Minecraft.gameSettings().x() == 2) {
                OpenGlBackendHolder.backend.rotate(-viewPitch, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(viewYaw, -1.0f, 0.0f, 0.0f);
            } else {
                OpenGlBackendHolder.backend.rotate(-viewPitch, 0.0f, 1.0f, 0.0f);
                OpenGlBackendHolder.backend.rotate(viewYaw, 1.0f, 0.0f, 0.0f);
            }
        }
        OpenGlBackendHolder.backend.scale(-labelScale, -labelScale, labelScale);
        int fontHeight = fontRenderer.getFontHeight();
        int top = -(fontHeight * lines.length - 1);
        RenderUtils.M((double)(-maxHalfWidth) - 2.0, top, (double)maxHalfWidth + 2.0, 2.0, 0.0, this.backgroundColor, this.backgroundColor);
        GlStateManager.enableTexture2D();
        GlStateManager.disableDepth();
        OpenGlBackendHolder.backend.setColor(1.0f, 1.0f, 1.0f);
        if (ForgeVersion.MC_1_16_5.d() && (matrixStack == null || matrixStack.isNull() || !matrixStack.isInstance(MappedClasses.DQ))) {
            matrixStack = MatrixStack.A();
        }
        int lineY = top + 2;
        for (String line : lines) {
            RenderItemFontBridge fontBuffer;
            int halfWidth = fontRenderer.getStringWidth(line) / 2;
            if (GuiRenderPrimitives.d()) {
                RenderMatrix4f renderMatrix = new RenderMatrix4f().setIdentity();
                renderMatrix.multiply(BufferedGuiRenderPrimitives.viewMatrix);
                renderMatrix.multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
                if (ForgeVersion.MC_1_20_6.d()) {
                    fontRenderer.h(line, -halfWidth, lineY, textColor, false, renderMatrix, SharedMonsterAttributes.V());
                } else {
                    int packedLight = 0xF000F0;
                    fontBuffer = Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
                    ScorePlayerTeamTextComponent textComponent = ScorePlayerTeamTextComponent.B(line);
                    fontRenderer.Z(textComponent, -halfWidth, lineY, textColor, false, renderMatrix.toMinecraftMatrix(), fontBuffer, true, 0, packedLight);
                    fontBuffer.q();
                }
            } else if (ForgeVersion.MC_1_16_5.d()) {
                matrixStack.H();
                Quaternion cameraOrientation = Minecraft.D().getCameraOrientation();
                matrixStack.i(cameraOrientation);
                matrixStack.i(Quaternion.fromEulerAngles(180.0f, 0.0f, 180.0f, true));
                ScorePlayerTeamTextComponent textComponent = ScorePlayerTeamTextComponent.B(line);
                fontBuffer = RenderItemFontBridge.V(Tessellator.getInstance().getWorldRenderer());
                int packedLight = 0xF000F0;
                Matrix4f matrix = matrixStack.F().getMatrix();
                fontRenderer.Z(textComponent, -halfWidth, lineY, textColor, false, matrix, fontBuffer, true, 0, packedLight);
                fontBuffer.q();
                matrixStack.U();
            } else {
                fontRenderer.drawString(line, (double)(-halfWidth), (double)lineY, textColor);
            }
            lineY += fontHeight;
        }
        RenderUtil.Y();
    }


    @EventHandler
    public void onGuiOpen(EventGuiOpen event) {
        GuiScreen guiScreen = event.getGuiScreen();
        if (guiScreen.isInstance(MappedClasses.u5) || guiScreen.isInstance(MappedClasses.D6) || guiScreen.isInstance(MappedClasses.F_)) {
            this.groups.clear();
        }
    }

    @EventHandler
    public void onPostTick(EventPostTick event) {
        if (Minecraft.thePlayer().isNull() || Minecraft.theWorld().isNull()) {
            this.groups.clear();
            return;
        }
        List<Object> worldEntities = Minecraft.theWorld().z();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (!this.groupItems.getEffectiveValue().booleanValue()) {
            this.groups.clear();
            for (Object entityHandle : worldEntities) {
                Entity entity = new Entity(entityHandle);
                if (!entity.isInstance(MappedClasses.zW)) continue;
                EntityItem entityItem = new EntityItem(entity);
                if (this.whitelistOnly.getEffectiveValue().booleanValue() && !this.allowedItems.matches(entityItem.getItemStack())) continue;
                this.groups.add(new ItemESPGroup(this, entityItem));
            }
            return;
        }
        Iterator<ItemESPGroup> groupIterator = this.groups.iterator();
        while (groupIterator.hasNext()) {
            ItemESPGroup group = groupIterator.next();
            group.update(worldEntities, player);
            if (!group.isEmpty()) continue;
            groupIterator.remove();
        }
        this.mergeNearbyGroups(player);
        HashSet<Object> groupedEntities = new HashSet<Object>();
        for (ItemESPGroup group : this.groups) {
            groupedEntities.addAll(group.getEntityHandles());
        }
        for (Object entityObject : worldEntities) {
            Entity entity = new Entity(entityObject);
            if (!entity.isInstance(MappedClasses.zW)) continue;
            EntityItem entityItem = new EntityItem(entity);
            if (this.whitelistOnly.getEffectiveValue().booleanValue() && !this.allowedItems.matches(entityItem.getItemStack()) || groupedEntities.contains(entityItem.getObject())) continue;
            double playerDistance = player.getDistanceToEntity(entityItem);
            double groupRadius = Math.max(1.5, playerDistance / 5.0);
            boolean addedToGroup = false;
            double itemX = entityItem.z();
            double itemY = entityItem.N();
            double itemZ = entityItem.h();
            for (ItemESPGroup group : this.groups) {
                double deltaX = itemX - group.currentX;
                double deltaY = itemY - group.currentY;
                double deltaZ = itemZ - group.currentZ;
                double groupDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                if (!(groupDistance <= groupRadius)) continue;
                group.addItem(entityItem);
                groupedEntities.add(entityItem.getObject());
                addedToGroup = true;
                break;
            }
            if (addedToGroup) continue;
            ItemESPGroup newGroup = new ItemESPGroup(this, entityItem);
            this.groups.add(newGroup);
            groupedEntities.add(entityItem.getObject());
        }
    }

    private void onGroupItemsChanged(BooleanValue value) {
        this.groups.clear();
    }
}
