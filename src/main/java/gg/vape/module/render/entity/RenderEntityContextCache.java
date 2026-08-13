package gg.vape.module.render.entity;

import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ITextComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class RenderEntityContextCache {
    private static final HashMap<Integer, ITextComponent> customNames;
    private static final Object lock;
    private static final Map<Integer, RenderEntityContext> contexts;
    private static final HashMap<Integer, ITextComponent> displayNameCache;

    static {
        lock = new Object();
        contexts = new LinkedHashMap<Integer, RenderEntityContext>();
        displayNameCache = new HashMap();
        customNames = new HashMap();
    }

    @Nullable
    public static void setCustomName(EntityPlayer player, ITextComponent customName) {
        customNames.put(player.S(), customName);
    }

    public static ITextComponent getCustomName(EntityPlayer player) {
        if (customNames.containsKey(player.S())) {
            return customNames.get(player.S());
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clear() {
        if (contexts.isEmpty()) {
            return;
        }
        synchronized (lock) {
            contexts.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void remove(int ... entityIds) {
        synchronized (lock) {
            for (int entityId : entityIds) {
                contexts.remove(entityId);
            }
        }
    }


    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Collection<RenderEntityContext> getContexts() {
        synchronized (lock) {
            return new ArrayList<>(contexts.values());
        }
    }

    public static void clearNameCaches() {
        displayNameCache.clear();
        customNames.clear();
    }

    private static RenderEntityContext createContext(EntityLivingBase entity, EntityPlayerSP viewer, Integer entityId) {
        RenderEntityContext context = new RenderEntityContext(entityId, entity, viewer);
        context.update(entity, viewer);
        return context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public static ITextComponent getDisplayName(EntityPlayer player) {
        if (displayNameCache.containsKey(player.S())) {
            return displayNameCache.get(player.S());
        }
        ITextComponent displayName = player.Q();
        displayNameCache.put(player.S(), displayName);
        return displayName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RenderEntityContext getOrCreate(EntityLivingBase entity, EntityPlayerSP viewer) {
        synchronized (lock) {
            return contexts.computeIfAbsent(entity.S(), entityId -> RenderEntityContextCache.createContext(entity, viewer, entityId));
        }
    }

}

