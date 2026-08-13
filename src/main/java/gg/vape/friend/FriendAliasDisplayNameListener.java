package gg.vape.friend;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPreTick;
import gg.vape.friend.FriendEntry;
import gg.vape.manager.client.FriendManager;
import gg.vape.utils.MutablePair;
import gg.vape.utils.StringUtils;
import gg.vape.utils.TimerUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public class FriendAliasDisplayNameListener
implements EventListener {
    private final Map<String, MutablePair<String, TimerUtil>> replacementCache = new HashMap<String, MutablePair<String, TimerUtil>>();
    private Set<FriendEntry> targetedFriends = new HashSet<FriendEntry>();

    private FriendManager getFriendManager() {
        return Vape.INSTANCE.getFriendManager();
    }

    Set<FriendEntry> getFriends() {
        return this.getFriendManager().getFriends();
    }

    public boolean hasFriends() {
        return !this.getFriends().isEmpty();
    }

    public boolean isAliasSpoofEnabled() {
        return this.getFriendManager().spoofAlias.getEffectiveValue();
    }

    private Set<FriendEntry> collectTargetedFriends() {
        return this.getFriendManager().getFriends().stream().filter(FriendEntry::isTargeted).collect(Collectors.toSet());
    }

    @EventHandler
    public void onTick(EventPreTick eventPreTick) {
        this.targetedFriends = this.collectTargetedFriends();
    }

    private String replaceAliases(String text, Iterable<FriendEntry> friends) {
        String replacedText = text;
        for (FriendEntry friendEntry : friends) {
            String displayName;
            String normalizedName = friendEntry.getName().toLowerCase();
            if (normalizedName.equalsIgnoreCase(displayName = friendEntry.getDisplayName()) || !StringUtils.K(replacedText, normalizedName)) continue;
            replacedText = StringUtils.U(replacedText, normalizedName, displayName);
        }
        return replacedText;
    }

    @Nullable
    public String getReplacedDisplayName(String text, Iterable<FriendEntry> friends) {
        return text == null ? null : (String)this.replacementCache.compute(text, (cachedText, cachedValue) -> this.updateCachedReplacement(text, friends, cachedText, cachedValue)).getFirst();
    }

    public Set<FriendEntry> getTargetedFriends() {
        if (this.targetedFriends == null) {
            this.targetedFriends = this.collectTargetedFriends();
        }
        return this.targetedFriends;
    }


    private MutablePair updateCachedReplacement(String text, Iterable iterable, String cachedText, MutablePair mutablePair) {
        if (mutablePair == null) {
            return new MutablePair<String, TimerUtil>(this.replaceAliases(text, iterable), new TimerUtil());
        }
        TimerUtil timerUtil = (TimerUtil)mutablePair.getSecond();
        if (timerUtil.hasTimeElapsed(1000L)) {
            timerUtil.reset();
            return mutablePair.setFirst(this.replaceAliases(text, iterable));
        }
        return mutablePair;
    }

    public boolean isAliasEnabled() {
        return this.getFriendManager().useAlias.getEffectiveValue();
    }
}

