package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.LocalOnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.OnlineFriendColorUtil;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.module.Mod;
import gg.vape.ui.click.component.AnimatedPanelComponent;
import gg.vape.ui.click.component.ItemStackSlotComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextLabelComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RemoteImageTextureManager;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class OnlineFriendActivityPanel
extends AnimatedPanelComponent {
    private float displayedHealth = 0.0f;
    int pendingSwingAnimations = 0;
    TimerUtil swingAnimationTimer;
    TimerUtil clickTimer;
    private OnlineActivityHeldItemSlotComponent heldItemSlot;
    private static final List<Integer> INVENTORY_ROW_ORDER = Arrays.asList(1, 2, 3, 0);
    private boolean inventoryVisible = false;
    private ItemStackSlotComponent[] armorSlots;
    private final TextLabelComponent usernameLabel;
    private EntityPlayer trackedPlayer = null;
    private final TextLabelComponent displayNameLabel;
    private ArrayList<ItemStackSlotComponent> inventorySlots;
    private PanelComponent inventoryPanel;
    float swingAnimationProgress;
    private final OnlineActivityPanelOptions options;
    private PanelComponent summaryPanel;
    @NotNull
    private final OnlineFriendActivityState activityState;
    private final Color absorptionColor = new Color(-2130728448);
    private boolean localActivity;

    private void updateEquipmentSlots(boolean showOverlay) {
        this.summaryPanel.setShowDisabledOverlay(false);
        this.summaryPanel.setDisabledOverlayColor(OnlineFriendActivityPanel.J.d);
        this.queueSwingAnimations();
        int overlayAlpha = (int)(this.updateSwingAnimationProgress() * 255.0f);
        if (this.shouldUseLivePlayerData()) {
            ItemStack heldItem = this.trackedPlayer != null && this.trackedPlayer.isNotNull()
                    && this.trackedPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().isNotNull()
                    ? this.trackedPlayer.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt() : null;
            this.heldItemSlot.setItemStack(heldItem);
            this.heldItemSlot.setSelected(heldItem != null && EnchantmentUtil.A(heldItem).size() > 0);
            this.heldItemSlot.setDisabledOverlayAlpha(overlayAlpha);
            this.heldItemSlot.setShowDisabledOverlay(showOverlay);
            for (int index = 0; index < this.armorSlots.length; ++index) {
                ItemStackSlotComponent armorSlot = this.armorSlots[index];
                ItemStack armorItem = new ItemStack(Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()[3 - index]);
                armorSlot.setItemStack(armorItem.isNotNull() ? armorItem : null);
                armorSlot.setSelected(armorItem.isNotNull() && EnchantmentUtil.A(armorItem).size() > 0);
                armorSlot.setShowDisabledOverlay(showOverlay);
            }
            return;
        }
        ActivityItemStack heldItem = this.activityState.getInventory()[this.activityState.getHeldItemSlot()];
        this.heldItemSlot.setItemStack(heldItem != null ? heldItem.toItemStack() : null);
        this.heldItemSlot.setSelected(heldItem != null && heldItem.hasEnchantments());
        this.heldItemSlot.setDisabledOverlayAlpha(overlayAlpha);
        this.heldItemSlot.setShowDisabledOverlay(showOverlay);
        for (int i = 0; i < this.armorSlots.length; ++i) {
            ItemStackSlotComponent armorSlot = this.armorSlots[i];
            ActivityItemStack armorItem = this.activityState.getArmor()[3 - i];
            if (armorItem != null && armorItem.getItemId() != 0) {
                armorSlot.setItemStack(armorItem.toItemStack());
                armorSlot.setSelected(armorItem.hasEnchantments());
            } else {
                armorSlot.setItemStack(null);
            }
            armorSlot.setShowDisabledOverlay(showOverlay);
        }
    }

    public void drawDirectionArrow(double x, double y, double size, double angleDegrees) {
        double radius = size / 2.0;
        double centerX = x + radius;
        double centerY = y + radius;
        double tipAngle = Math.toRadians(angleDegrees);
        double leftAngle = Math.toRadians(angleDegrees - 12.0);
        double rightAngle = Math.toRadians(angleDegrees + 12.0);
        double tipX = centerX + (radius + 4.0) * Math.sin(tipAngle);
        double tipY = centerY - (radius + 4.0) * Math.cos(tipAngle);
        double leftX = centerX + (radius + 2.0) * Math.sin(leftAngle);
        double leftY = centerY - (radius + 2.0) * Math.cos(leftAngle);
        double rightX = centerX + (radius + 2.0) * Math.sin(rightAngle);
        double rightY = centerY - (radius + 2.0) * Math.cos(rightAngle);
        GuiRenderPrimitives.U(leftX, leftY, tipX, tipY, rightX, rightY, OnlineFriendActivityPanel.J.f);
    }

    private double getRelativeDirection(double sourceX, double sourceZ, double targetX, double targetZ) {
        double direction = 0.0;
        double deltaX = targetX - sourceX;
        double deltaZ = targetZ - sourceZ;
        if (deltaZ > 0.0 && deltaX > 0.0) {
            direction = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        } else if (deltaZ > 0.0 && deltaX < 0.0) {
            direction = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        } else if (deltaZ < 0.0 && deltaX > 0.0) {
            direction = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0.0 && deltaX < 0.0) {
            direction = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        double wrappedDirection = MathUtil.wrapAngleTo180((float)direction);
        return MathUtil.wrapAngleTo180((float)(wrappedDirection -= (double)MathUtil.wrapAngleTo180(Minecraft.thePlayer().J())));
    }

    private void renderClicksPerSecond(double centerX, double centerY) {
        SmoothFontRenderer fontRenderer = this.getFontRenderer(0.75);
        String clicksPerSecond = Integer.toString(this.activityState.getClicksPerSecond());
        fontRenderer.v(clicksPerSecond, centerX - fontRenderer.N(clicksPerSecond) / 2.0, centerY - fontRenderer.d(clicksPerSecond) / 2.0, Color.white);
    }

    public OnlineFriendActivityPanel(LocalOnlineFriend localOnlineFriend) {
        this(localOnlineFriend.getActivityState());
        this.localActivity = true;
    }

    public void renderSummary(OnlineActivitySettingsFrame settingsFrame) {
        Entity localPlayer;
        if (this.trackedPlayer != null && !this.trackedPlayer.equals(Minecraft.thePlayer())) {
            Entity refreshedPlayer = Minecraft.theWorld().V(this.trackedPlayer.S());
            this.trackedPlayer = refreshedPlayer.isNotNull() ? new EntityPlayer(refreshedPlayer) : null;
        }
        localPlayer = Minecraft.thePlayer();
        double playerX = this.activityState.getPositionX(this.trackedPlayer);
        double playerY = this.activityState.getPositionY(this.trackedPlayer);
        double playerZ = this.activityState.getPositionZ(this.trackedPlayer);
        float health = this.activityState.getHealth(this.trackedPlayer);
        float maxHealth = this.activityState.getMaxHealth(this.trackedPlayer);
        float absorption = this.activityState.getAbsorptionAmount(this.trackedPlayer);
        if (this.shouldRenderBackground()) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), new Color(26, 25, 26, 150));
        }
        double avatarX = this.G$src$D$1b2f02a() + 6.0;
        double avatarY = this.n() + 10.0;
        double avatarSize = 22.0;
        GlImageTexture avatarTexture = RemoteImageTextureManager.getInstance().getTexture(this.activityState.getFriend().getMinecraftUsername(), 32);
        if (avatarTexture != null) {
            GuiRenderPrimitives.V((float)(avatarX - 0.5), (float)(avatarY - 0.5), (float)(avatarSize + 1.0), 1.0, new Color(50, 50, 50, 255));
            GuiRenderPrimitives.u((float)avatarX, (float)avatarY, (float)avatarSize, 1.0f, Color.WHITE, avatarTexture);
        }
        double textX = avatarX + avatarSize + 6.0;
        this.usernameLabel.setText(this.activityState != null ? this.activityState.getFriend().getMinecraftUsername() : "N/A");
        double nameCenterY = avatarY + 4.0;
        this.usernameLabel.renderAt(textX, nameCenterY - this.usernameLabel.getTextHeight() / 2.0);
        this.displayNameLabel.setText(this.activityState != null ? this.activityState.getFriend().getDisplayName() : "N/A");
        this.displayNameLabel.renderAt(textX, nameCenterY + 10.0 - this.displayNameLabel.getTextHeight() / 2.0);
        double healthBarY = avatarY + 18.0;
        this.renderHealthBar(textX, healthBarY, health, maxHealth, absorption);
        this.renderHurtOverlay();
        this.renderDeathIndicator(health);
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() - 8.0, this.n() + 4.0, 4.0, 1.0, new Color(0, 0, 0, 255));
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + this.A() - 8.0, this.n() + 4.0, 4.0, 1.0, OnlineFriendColorUtil.getGroupRoleColor(this.activityState.getFriend().getGroupRole()));
        if (!this.localActivity) {
            this.drawDirectionArrow(avatarX, avatarY, avatarSize, this.getDirectionFromEntity(Minecraft.thePlayer(), playerX, playerZ));
            int distance = (int)RotationUtil.y(localPlayer.c(), localPlayer.A(), localPlayer.Z(), playerX, playerY, playerZ);
            String distanceText = distance > 1000000000 ? "very far away" : distance + "m";
            SmoothFontRenderer distanceFont = this.getDistanceFontRenderer(distance);
            double distanceX = Math.max(avatarX + avatarSize / 2.0 - distanceFont.N(distanceText) / 2.0, this.G$src$D$1b2f02a() + 2.0);
            double distanceY = avatarY + avatarSize + 6.0 + (this.n() + this.L() - 2.0 - (avatarY + 4.0 + avatarSize + 4.0) - distanceFont.d(distanceText)) / 2.0;
            distanceFont.v(distanceText, distanceX, distanceY, OnlineFriendActivityPanel.J.A);
        }
    }

    private void refreshInventorySlots() {
        boolean useLivePlayerData = this.shouldUseLivePlayerData();
        int displayIndex = 0;
        for (int inventoryRow : INVENTORY_ROW_ORDER) {
            for (int column = 0; column < 9; ++column) {
                int inventoryIndex = inventoryRow * 9 + column;
                ItemStackSlotComponent slot = this.inventorySlots.get(displayIndex++);
                if (useLivePlayerData) {
                    ItemStack item = this.trackedPlayer != null && this.trackedPlayer.isNotNull()
                            ? this.trackedPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(inventoryIndex) : null;
                    boolean present = item != null && item.isNotNull();
                    slot.setItemStack(present ? item : null);
                    slot.setSelected(present && EnchantmentUtil.A(item).size() > 0);
                } else {
                    ActivityItemStack item = this.activityState.getInventory()[inventoryIndex];
                    slot.setItemStack(item != null ? item.toItemStack() : null);
                    slot.setSelected(item != null && item.hasEnchantments());
                }
            }
        }
    }

    private boolean shouldRenderBackground() {
        return this.options.getRenderBackground().getEffectiveValue();
    }

    public OnlineFriendActivityState getActivityState() {
        return this.activityState;
    }

    private boolean shouldDisplayClicksPerSecond() {
        return this.options.getCpsDisplay().getEffectiveValue();
    }


    public void setInventoryVisible(boolean visible) {
        if (visible == this.inventoryVisible) {
            if (this.inventoryVisible) {
                this.refreshInventorySlots();
            }
            return;
        }
        this.inventoryVisible = visible;
        if (visible) {
            this.refreshInventorySlots();
            this.summaryPanel.setVisible(false);
            this.inventoryPanel.setVisible(true);
        } else {
            this.inventoryPanel.setVisible(false);
            this.summaryPanel.setVisible(true);
            this.summaryPanel.l$src$V$1mibm4x();
        }
    }

    private SmoothFontRenderer getDistanceFontRenderer(double distance) {
        int textLength = (distance + "m").length();
        if (textLength < 10) {
            return this.getFontRenderer(0.8);
        }
        return this.getFontRenderer(0.7);
    }

    private void queueSwingAnimations() {
        int clicksPerSecond = this.activityState.getClicksPerSecond();
        int swingProgressTicks = this.activityState.getSwingProgressTicks(this.trackedPlayer);
        boolean synthesizeFromCps = this.trackedPlayer == null || clicksPerSecond > 4 && swingProgressTicks != 0;
        if (synthesizeFromCps) {
            if (clicksPerSecond != 0 && this.clickTimer.hasTimeElapsed(1000 / clicksPerSecond)) {
                this.clickTimer.reset();
                ++this.pendingSwingAnimations;
            }
        } else if (swingProgressTicks == 1 && this.activityState.getBuildingTicks() > 1) {
            this.clickTimer.reset();
            ++this.pendingSwingAnimations;
        }
    }

    private double getDirectionFromEntity(Entity entity, double targetX, double targetZ) {
        return this.getRelativeDirection(entity.c(), entity.Z(), targetX, targetZ);
    }

    private void buildInventoryPanel() {
        this.inventorySlots.clear();
        this.inventoryPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.inventoryPanel.setShowDisabledOverlay(false);
        this.inventoryPanel.t$src$V$zbu1jn();
        this.inventoryPanel.h(new SpacerComponent(110.0, 1.5), new Object[0]);
        PanelComponent panelComponent = new PanelComponent(110.0, 6.0);
        panelComponent.addChildren(new SpacerComponent(110.0, 1.0), new SpacerComponent(93.0, 6.0));
        this.inventoryPanel.h(panelComponent, new Object[0]);
        panelComponent.setShowDisabledOverlay(false);
        for (int inventoryRow : INVENTORY_ROW_ORDER) {
            PanelComponent rowPanel = new PanelComponent(110.0, 11.0);
            rowPanel.setShowDisabledOverlay(false);
            rowPanel.h(new SpacerComponent(8.0, 10.0), new Object[0]);
            for (int column = 0; column < 9; ++column) {
                ItemStackSlotComponent slot = new ItemStackSlotComponent(10.0, 10.0, 8);
                ActivityItemStack item = this.activityState.getInventory()[inventoryRow * 9 + column];
                slot.setItemStack(item != null ? item.toItemStack() : null);
                this.inventorySlots.add(slot);
                if (column != 0) {
                    rowPanel.h(new SpacerComponent(1.0, 11.0), new Object[0]);
                }
                rowPanel.h(slot, new Object[0]);
            }
            this.inventoryPanel.h(rowPanel, new Object[0]);
        }
    }

    public OnlineFriendActivityPanel(@NotNull OnlineFriendActivityState onlineFriendActivityState) {
        super(114.0, 52.0);
        this.heldItemSlot = new OnlineActivityHeldItemSlotComponent();
        this.armorSlots = new ItemStackSlotComponent[]{new ItemStackSlotComponent(), new ItemStackSlotComponent(), new ItemStackSlotComponent(), new ItemStackSlotComponent()};
        this.inventorySlots = new ArrayList();
        this.inventoryPanel = new PanelComponent(110.0, 45.0);
        this.summaryPanel = new PanelComponent(110.0, 58.0);
        this.clickTimer = new TimerUtil();
        this.swingAnimationTimer = new TimerUtil();
        this.activityState = onlineFriendActivityState;
        this.setShowDisabledOverlay(false);
        this.usernameLabel = new TextLabelComponent(onlineFriendActivityState.getFriend().getMinecraftUsername(), 0.7, 1.0, 0.1, 74.0, false, true, Color.white);
        this.displayNameLabel = new TextLabelComponent(onlineFriendActivityState.getFriend().getDisplayName(), 0.6, 0.9, 0.1, 74.0, false, true, OnlineFriendActivityPanel.J.A);
        this.summaryPanel.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
        this.summaryPanel.addChildren(new SpacerComponent(110.0, 36.0));
        PanelComponent panelComponent = new PanelComponent(110.0, 23.0);
        panelComponent.setShowDisabledOverlay(false);
        panelComponent.h(new SpacerComponent(34.0, 1.0), new Object[0]);
        panelComponent.h(this.heldItemSlot, new Object[0]);
        for (ItemStackSlotComponent itemStackSlotComponent : this.armorSlots) {
            panelComponent.addChildren(new SpacerComponent(1.0, 0.0), itemStackSlotComponent);
        }
        this.summaryPanel.h(panelComponent, new Object[0]);
        this.summaryPanel.setShowDisabledOverlay(false);
        this.h(this.summaryPanel, new Object[0]);
        this.inventoryPanel.setShowDisabledOverlay(false);
        this.buildInventoryPanel();
        this.inventoryPanel.setVisible(false);
        this.h(this.inventoryPanel, new Object[0]);
        this.options = OnlineActivityPanelOptions.INSTANCE;
    }

    @Override
    public void u() {
        super.u();
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        this.updateEquipmentSlots(this.shouldRenderBackground());
    }

    private void renderHealthBar(double x, double y, float health, float maxHealth, float absorption) {
        float interpolationDelta;
        float healthRatio;
        float interpolationSpeed = 0.5f;
        if (this.displayedHealth < health) {
            healthRatio = this.displayedHealth / health;
            interpolationDelta = 1.0f - healthRatio;
            this.displayedHealth += interpolationSpeed * interpolationDelta;
        }
        if (this.displayedHealth > health) {
            healthRatio = health / this.displayedHealth;
            if (this.displayedHealth == 0.0f) {
                healthRatio = 0.0f;
            }
            interpolationDelta = 1.0f - healthRatio;
            this.displayedHealth -= interpolationSpeed * interpolationDelta;
        }
        if (Float.isNaN(this.displayedHealth) || !Float.isFinite(this.displayedHealth)) {
            this.displayedHealth = health;
        }
        health = Math.max(health, 0.0f);
        double barWidth = 75.0;
        double barHeight = 2.0;
        float cornerRadius = 0.6f;
        float barFillRatio = health / Math.max(maxHealth, 1.0f);
        float colorRatio = health / Math.max(maxHealth, 1.0f);
        GuiRenderPrimitives.I(x, y, barWidth, barHeight, new Color(54, 54, 54, 255), true, cornerRadius, 1.0f, 4.0f, new Color(0, 0, 0, 152));
        if (this.trackedPlayer == null) {
            colorRatio = 1.0f;
        }
        Color healthColor = health > 0.0f ? RenderUtils.q(colorRatio, true) : Color.RED;
        GuiRenderPrimitives.e(x, y, Math.min(barWidth * (double)barFillRatio, barWidth), barHeight, healthColor, false, cornerRadius, 1.0f);
        if (absorption > 0.0f) {
            absorption = Math.min(10.0f, absorption);
            double absorptionX = Math.max(x, x + barWidth * (double)barFillRatio - 2.0);
            double barRight = x + barWidth;
            double healthRight = x + barWidth * (double)barFillRatio;
            double absorptionWidth = 10.0f * (absorption / 2.0f);
            double overflow = barRight - (healthRight - 2.0 + absorptionWidth);
            if (overflow < 0.0) {
                absorptionX -= Math.abs(overflow);
            }
            GuiRenderPrimitives.e(absorptionX, y, absorptionWidth, barHeight, this.absorptionColor, true, cornerRadius, 1.0f);
        }
    }

    private void renderDeathIndicator(float health) {
        if (health <= 0.0f) {
            GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + 5.0, this.n() + 7.0, 24.0, 1.0, new Color(0, 0, 0, 200));
            ImageRenderer.drawImage(new Color(197, 49, 49, 255), (float)this.G$src$D$1b2f02a() + 5.0f + 12.0f - 4.0f, (float)this.n() + 9.0f + 13.0f - 6.0f, "newblatant", 8.0f, 8.0f, true);
        }
    }

    @Override
    public void c() {
        OnlineActivitySettingsFrame onlineActivitySettingsFrame = (OnlineActivitySettingsFrame)this.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
        this.l$src$V$1mibm4x();
        if (this.inventoryVisible) {
            ItemStackSlotComponent itemStackSlotComponent = this.renderInventory(onlineActivitySettingsFrame);
            super.c();
            GuiRenderPrimitives.P(itemStackSlotComponent.G$src$D$1b2f02a(), itemStackSlotComponent.n(), itemStackSlotComponent.A(), itemStackSlotComponent.L(), Color.white, 1.6f, 0.8f, 1.0f);
            return;
        }
        this.renderSummary(onlineActivitySettingsFrame);
        super.c();
        if (this.shouldDisplayClicksPerSecond()) {
            this.renderClicksPerSecond(this.G$src$D$1b2f02a() + 30.0, this.n() + this.L() - 10.0);
        }
        if (this.swingAnimationTimer.hasTimeElapsed(50L)) {
            this.swingAnimationTimer.reset();
        }
        this.updateSwingAnimationProgress();
    }

    public void resolveTrackedPlayer() {
        WorldClient worldClient = Minecraft.theWorld();
        if (worldClient.isNull()) {
            return;
        }
        if (this.activityState.getFriend() instanceof LocalOnlineFriend) {
            this.trackedPlayer = Minecraft.thePlayer();
            return;
        }
        if (this.trackedPlayer != null && ((World)worldClient).V(this.trackedPlayer.S()).isNull()) {
            this.trackedPlayer = null;
        }
        if (this.trackedPlayer != null) {
            return;
        }
        for (Object playerObject : worldClient.X()) {
            EntityPlayer player = new EntityPlayer(playerObject);
            if (!player.getName().equalsIgnoreCase(this.activityState.getFriend().getMinecraftUsername())) continue;
            this.trackedPlayer = player;
            break;
        }
    }

    private void renderHurtOverlay() {
        int hurtTime = this.activityState.getHurtTime(this.trackedPlayer);
        if (hurtTime > 0) {
            double hurtProgress = (double)hurtTime / 20.0;
            int alpha = (int)(255.0 * hurtProgress);
            GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + 6.0, this.n() + 10.0, 22.0, 1.0, new Color(255, 0, 0, alpha));
        }
    }

    private boolean shouldUseLivePlayerData() {
        List<OnlineFriendActivityState> partyActivities = this.options.getPartyActivities();
        return partyActivities.size() == 0;
    }

    private void renderTargetAvatar(double x, double y) {
        if (!this.activityState.hasTarget()) {
            return;
        }
        GlImageTexture targetTexture = RemoteImageTextureManager.getInstance().getTexture(this.activityState.getTargetUuid() + "", 32);
        if (targetTexture != null) {
            GuiRenderPrimitives.V((float)(x - 0.5), (float)(y - 0.5), 11.0, 1.0, OnlineFriendActivityPanel.J.d);
            GuiRenderPrimitives.u((float)x, (float)y, 10.0f, 1.0f, Color.WHITE, targetTexture);
        }
    }

    private ItemStackSlotComponent renderInventory(OnlineActivitySettingsFrame settingsFrame) {
        boolean useLivePlayerData = this.shouldUseLivePlayerData();
        this.inventoryPanel.K(this.G$src$D$1b2f02a());
        this.inventoryPanel.S(this.n());
        this.inventoryPanel.l$src$V$1mibm4x();
        String enabledModules = "";
        for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
            if (!mod.isEnabled() || mod.getGuiColor() == 0) continue;
            enabledModules = enabledModules + mod.getName() + "\n";
        }
        for (ItemStackSlotComponent itemStackSlotComponent : this.inventorySlots) {
            itemStackSlotComponent.setDisabledOverlayColor(this.shouldRenderBackground() ? OnlineFriendActivityPanel.J.i : new Color(26, 25, 26, 150));
        }
        if (this.shouldRenderBackground()) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), new Color(26, 25, 26, 150));
        }
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.75);
        smoothFontRenderer.v(this.activityState.getFriend().getMinecraftUsername(), this.G$src$D$1b2f02a() + 8.0, this.n() + 4.0 - smoothFontRenderer.d(this.activityState.getFriend().getMinecraftUsername()) / 2.0, this.shouldRenderBackground() ? OnlineFriendActivityPanel.J.A : Color.white);
        return this.inventorySlots.get(useLivePlayerData ? this.trackedPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() + 27 : this.activityState.getHeldItemSlot() + 27);
    }

    private float updateSwingAnimationProgress() {
        if (this.swingAnimationProgress > 0.0f) {
            this.swingAnimationProgress = (float)this.swingAnimationTimer.getLastMS() / 50.0f;
            this.swingAnimationProgress = Math.max(this.swingAnimationProgress, 0.0f);
        }
        if (this.pendingSwingAnimations > 0 && this.swingAnimationProgress <= 0.0f) {
            this.swingAnimationProgress = 1.0f;
            --this.pendingSwingAnimations;
        }
        return this.swingAnimationProgress;
    }

    public EntityPlayer getTrackedPlayer() {
        return this.trackedPlayer;
    }
}

