package gg.vape.service.store;

import java.util.LinkedHashSet;
import java.util.Set;

public final class PartyRecord {
    public long partyId;
    public long leaderId;
    public Set<Long> members = new LinkedHashSet<>();
    public Set<Long> invitedUsers = new LinkedHashSet<>();
    public boolean openInvites;
}
