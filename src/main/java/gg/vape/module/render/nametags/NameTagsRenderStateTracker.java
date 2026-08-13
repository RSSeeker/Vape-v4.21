package gg.vape.module.render.nametags;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventRenderTickBase;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.RenderThreadTaskQueue;
import gg.vape.utils.ItemStackFingerprint;
import gg.vape.utils.TimerUtil;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class NameTagsRenderStateTracker
implements EventListener {
    private final HashSet<Integer> pendingEntityIds;
    private final TimerUtil cleanupTimer;
    private final HashMap<Long, NameTagsNameState> stateByKey = new HashMap();
    public static final NameTagsRenderStateTracker INSTANCE = new NameTagsRenderStateTracker();
    private final HashMap<Long, Long> lastSeenByKey;

    @EventHandler
    public void onWorldChange(EventWorldChange event) {
        this.clear();
    }

    @EventHandler
    public void onPostRenderTick(EventPostRenderTick event) {
        if (ForgeVersion.MC_1_21_4.v()) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        this.tick(event);
    }

    @EventHandler
    public void onPreRenderTick(EventPreRenderTick event) {
        if (ForgeVersion.MC_1_21_4.d()) {
            return;
        }
        if (event.getWorld().isNull()) {
            return;
        }
        this.tick(event);
    }

    @Nullable
    public NameTagsNameState getOrSchedule(EntityPlayer player) {
        long key = ItemStackFingerprint.T(player);
        this.lastSeenByKey.put(key, System.currentTimeMillis());
        NameTagsNameState nameTagsNameState = this.stateByKey.get(key);
        if (nameTagsNameState != null) {
            if (nameTagsNameState.getFramebufferState() == null || !nameTagsNameState.getFramebufferState().isValid()) {
                try {
                    if (nameTagsNameState.getFramebufferState() != null) {
                        nameTagsNameState.getFramebufferState().dispose();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.stateByKey.remove(key);
                this.pendingEntityIds.add(player.S());
                return null;
            }
            return nameTagsNameState;
        }
        this.pendingEntityIds.add(player.S());
        return null;
    }

    private void tick(EventRenderTickBase event) {
        if (this.cleanupTimer.hasTimeElapsed(1000L)) {
            ArrayList<Long> staleKeys = new ArrayList<Long>();
            for (Long key : this.lastSeenByKey.keySet()) {
                if (System.currentTimeMillis() - this.lastSeenByKey.get(key) <= 10000L || this.stateByKey.get(key) == null) continue;
                staleKeys.add(key);
            }
            for (Long key : staleKeys) {
                NameTagsNameState state = this.stateByKey.get(key);
                if (state != null) {
                    state.getFramebufferState().dispose();
                }
                this.stateByKey.remove(key);
                this.lastSeenByKey.remove(key);
            }
        }
        for (Integer entityId : this.pendingEntityIds) {
            try {
                WorldClient world = event.getWorld();
                Wrapper entity = world.isNull() ? null : world.V(entityId);
                if (entity == null || entity.isNull() || !entity.isNotNull() || !entity.isInstance(MappedClasses.Yl) || !world.z().contains(entity.getObject())) continue;
                EntityPlayer player = new EntityPlayer(entity);
                long key = ItemStackFingerprint.T(player);
                this.stateByKey.put(key, NameTagsNameState.create(player));
            }
            catch (Exception exception) {}
        }
        this.pendingEntityIds.clear();
    }

    public void clear() {
        if (!this.stateByKey.isEmpty()) {
            ArrayList<NameTagsNameState> states = new ArrayList<NameTagsNameState>(this.stateByKey.values());
            RenderThreadTaskQueue.enqueue(() -> NameTagsRenderStateTracker.disposeStates(states));
        }
        this.stateByKey.clear();
        this.pendingEntityIds.clear();
        this.lastSeenByKey.clear();
    }

    public NameTagsRenderStateTracker() {
        this.pendingEntityIds = new HashSet();
        this.lastSeenByKey = new HashMap();
        this.cleanupTimer = new TimerUtil();
    }

    private static void disposeStates(List<NameTagsNameState> states) {
        for (NameTagsNameState state : states) {
            if (state == null || state.getFramebufferState() == null) continue;
            try {
                state.getFramebufferState().dispose();
            }
            catch (Exception exception) {}
        }
    }
}
