package gg.vape.friend.ui;

import com.google.gson.JsonObject;
import gg.vape.event.EventBus;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.friend.ui.OnlineCombatStatComparisonComponent;
import gg.vape.friend.ui.OnlineCombatStatsTargetLabelComponent;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.HudSettingsFrameBase;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntityPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class OnlineCombatStatsSettingsFrame
extends HudSettingsFrameBase
implements EventListener {
    private int localPotionCount;
    private World previousWorld;
    private int targetHitCount;
    private int targetPotionCount;
    private int pendingLocalPotionEntities;
    private OnlineCombatStatComparisonComponent potionComparison;
    private EntityPlayer targetPlayer;
    private double previousPlayerY;
    private int localHitCount;
    private final OnlineCombatStatsTargetLabelComponent targetLabelComponent = new OnlineCombatStatsTargetLabelComponent(this);
    private OnlineCombatStatComparisonComponent hitComparison;
    private double previousPlayerX;
    private String targetLabel;
    private double previousPlayerZ;

    @Override
    public void v() {
        double headerHeight = this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null && this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().V$src$Z$1xhop3l() ? this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() : 0.0;
        Color color = new Color(OnlineCombatStatsSettingsFrame.J.m.getRed(), OnlineCombatStatsSettingsFrame.J.m.getGreen(), OnlineCombatStatsSettingsFrame.J.m.getBlue(), 240);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n() + headerHeight, this.A(), this.L() - headerHeight, color);
    }

    @Override
    public void Y() {
    }

    @Override
    public String getName() {
        return "Duel Info";
    }


    @Override
    public void V() {
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (!this.y$src$Z$1f55jvh() || Minecraft.thePlayer().isNull()) {
            this.resetTargetSearch();
            return;
        }
        boolean movedTooFar = Math.abs(localPlayer.z() - this.previousPlayerX) > 120.0 || Math.abs(localPlayer.N() - this.previousPlayerY) > 120.0 || Math.abs(localPlayer.h() - this.previousPlayerZ) > 120.0;
        this.previousPlayerX = localPlayer.z();
        this.previousPlayerY = localPlayer.N();
        this.previousPlayerZ = localPlayer.h();
        if (this.hasWorldChanged() || movedTooFar) {
            this.resetTargetSearch();
            return;
        }
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        if (this.targetPlayer == null || this.targetPlayer.isNull()) {
            this.findNearbyTarget();
        } else {
            if (localPlayer.M$src$Z$ff28xj() || this.targetPlayer.M$src$Z$ff28xj()) {
                this.resetTargetSearch();
                return;
            }
            boolean targetStillPresent = false;
            for (Object entityObject : Minecraft.theWorld().X()) {
                if (this.targetPlayer.getObject().equals(entityObject)) {
                    targetStillPresent = true;
                    break;
                }
                EntityPlayer player = new EntityPlayer(entityObject);
                if (this.targetPlayer.getObject().equals(player.getObject()) || !this.targetPlayer.getName().equalsIgnoreCase(player.getName())) continue;
                this.targetPlayer = player;
            }
            if (!targetStillPresent) {
                this.targetPlayer = null;
                this.findNearbyTarget();
            }
        }
    }

    public EntityPlayer getTargetPlayer() {
        return this.targetPlayer;
    }

    @EventHandler
    public void onEntityJoinWorld(EventEntityJoinWorld event) {
        if (this.targetPlayer == null || this.targetPlayer.isNull() || Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!event.getEntity().isInstance(MappedClasses.Zf)) {
            return;
        }
        EntityPotion potionEntity = new EntityPotion(event.getEntity());
        if (potionEntity.getPotion().isNull() || !ItemStackScoreUtil.i(potionEntity.getPotion())) {
            return;
        }
        if (this.pendingLocalPotionEntities > 0) {
            ++this.localPotionCount;
            --this.pendingLocalPotionEntities;
        } else {
            ++this.targetPotionCount;
        }
        this.updateComparisons();
    }

    @Override
    protected void renderHudModeBorder() {
        int n = HudModuleConfigFrameBase.isHudEditorContext() ? 200 : 102;
        Color color = new Color(OnlineCombatStatsSettingsFrame.J.i.getRed(), OnlineCombatStatsSettingsFrame.J.i.getGreen(), OnlineCombatStatsSettingsFrame.J.i.getBlue(), n);
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.applyDefaultEditorAlpha(color));
    }

    private void updateComparisons() {
        this.hitComparison.setTargetCount(this.targetHitCount);
        this.hitComparison.setLocalCount(this.localHitCount);
        this.potionComparison.setTargetCount(this.targetPotionCount);
        this.potionComparison.setLocalCount(this.localPotionCount);
    }

    public OnlineCombatStatsSettingsFrame() {
        super("newduelinfo", "Duel Info");
        this.potionComparison = new OnlineCombatStatComparisonComponent("Potions", this);
        this.hitComparison = new OnlineCombatStatComparisonComponent("Sword Hits", this);
        if (this.q()) {
            this.w();
        }
        this.addSettings(this.targetLabelComponent, this.potionComparison, this.hitComparison);
        this.resetTargetSearch();
        EventBus.getInstance().registerListener(this, new Predicate[0]);
    }

    @Override
    public void t(JsonObject jsonObject) {
        super.t(jsonObject);
        ClientSettings.getFrame(QuickActionsFrame.class).Y$src$Lgg_vape_ui_click_frame_impl_quickactions_QuickA$p4ezt5().setValue(this.V$src$Z$1xhop3l());
    }

    @EventHandler
    public void onPlayerUseItem(EventPlayerUseItem event) {
        if (this.targetPlayer == null || this.targetPlayer.isNull()) {
            return;
        }
        ItemStack itemStack = event.getItemStack();
        if (itemStack.isNotNull() && MappedClasses.Di.isInstance(itemStack.getItem().getObject()) && ItemStackScoreUtil.i(itemStack)) {
            ++this.pendingLocalPotionEntities;
        }
    }

    private void resetTargetSearch() {
        this.targetLabel = "Searching...";
        this.targetPlayer = null;
        this.localPotionCount = 0;
        this.targetPotionCount = 0;
        this.pendingLocalPotionEntities = 0;
        this.targetHitCount = 0;
        this.localHitCount = 0;
        this.updateComparisons();
    }

    private void findNearbyTarget() {
        WorldClient worldClient = Minecraft.theWorld();
        EntityPlayerSP localPlayer = Minecraft.thePlayer();
        if (worldClient.isNull() || localPlayer.isNull()) {
            return;
        }
        this.targetLabel = "Searching...";
        CopyOnWriteArrayList worldEntities = new CopyOnWriteArrayList(worldClient.X());
        ArrayList<EntityPlayer> nearbyPlayers = new ArrayList<EntityPlayer>();
        for (Object entityObject : worldEntities) {
            EntityPlayer player;
            if (!MappedClasses.Yl.isInstance(entityObject) || (player = new EntityPlayer(entityObject)).J$src$Z$fdev5g() || player.getObject().equals(localPlayer.getObject()) || player.M$src$Z$ff28xj() || player.S() == -420 || !(localPlayer.getDistanceToEntity(player) < 32.0f)) continue;
            nearbyPlayers.add(player);
        }
        if (nearbyPlayers.size() > 1) {
            this.targetLabel = "More than one target";
        } else if (nearbyPlayers.size() == 1) {
            this.targetPlayer = nearbyPlayers.get(0);
            this.targetLabel = this.targetPlayer.getName();
            this.updateComparisons();
        }
    }

    public String getTargetLabel() {
        return this.targetLabel;
    }

    @EventHandler
    public void onLivingUpdate(EventLivingUpdate event) {
        if (this.targetPlayer == null || this.targetLabel == null) {
            return;
        }
        if (Minecraft.thePlayer().getDistanceToEntity(this.targetPlayer) > 6.0f) {
            return;
        }
        if (event.getEntity().getObject().equals(Minecraft.thePlayer().getObject())) {
            ++this.localHitCount;
        }
        if (event.getEntity().getObject().equals(this.targetPlayer.getObject())) {
            ++this.targetHitCount;
        }
        this.updateComparisons();
    }

    private boolean hasWorldChanged() {
        WorldClient currentWorld = Minecraft.theWorld();
        if (this.previousWorld == null) {
            this.previousWorld = currentWorld;
            return true;
        }
        boolean changed = currentWorld.isNotNull() && !currentWorld.getObject().equals(this.previousWorld.getObject());
        this.previousWorld = currentWorld;
        return changed;
    }
}

