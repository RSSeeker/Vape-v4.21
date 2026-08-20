package gg.vape.service.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class FileStore {
    private static final long CHALLENGE_TTL_MILLIS = 5 * 60 * 1000L;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final SecureRandom random = new SecureRandom();
    private final Path file;
    private ServiceState state;

    public FileStore(Path file) throws IOException {
        this.file = file.toAbsolutePath().normalize();
        this.state = load();
        normalizeLoadedState();
        save();
    }

    public synchronized Optional<AccountRecord> account(String token) {
        return Optional.ofNullable(state.accountsByToken.get(token));
    }

    public synchronized Optional<AccountRecord> account(long userId) {
        return state.accountsByToken.values().stream()
                .filter(account -> account.userId == userId)
                .findFirst();
    }

    public synchronized Optional<AccountRecord> accountByName(String username) {
        return state.accountsByToken.values().stream()
                .filter(account -> account.username.equalsIgnoreCase(username))
                .findFirst();
    }

    public synchronized LoaderLoginResult loginByUsername(String username) throws IOException {
        String normalized = username == null ? "" : username.trim();
        if (normalized.isEmpty() || normalized.length() > 16) {
            throw new IllegalArgumentException("Username must contain 1 to 16 characters");
        }
        for (Map.Entry<String, AccountRecord> entry : state.accountsByToken.entrySet()) {
            if (entry.getValue().username.equalsIgnoreCase(normalized)) {
                return new LoaderLoginResult(entry.getKey(), entry.getValue());
            }
        }

        String token;
        do {
            token = randomToken();
        } while (state.accountsByToken.containsKey(token));
        AccountRecord account = new AccountRecord();
        account.userId = state.nextUserId++;
        account.username = normalized;
        account.accountCreation = AccountRecord.nowTimestamp();
        account.registered = true;
        state.accountsByToken.put(token, account);
        save();
        return new LoaderLoginResult(token, account);
    }

    public synchronized boolean areFriends(long firstUserId, long secondUserId) {
        return account(firstUserId).map(account -> account.onlineFriends.contains(secondUserId)).orElse(false);
    }

    public synchronized Set<Long> friendIds(long userId) {
        return account(userId).map(account -> Collections.unmodifiableSet(new LinkedHashSet<>(account.onlineFriends)))
                .orElse(Collections.<Long>emptySet());
    }

    public synchronized FriendRequestCreation createFriendRequest(long senderId, String targetName)
            throws IOException {
        AccountRecord sender = account(senderId)
                .orElseThrow(() -> new IllegalStateException("Sender account not found"));
        AccountRecord target = accountByName(targetName).orElse(null);
        if (target == null) {
            return new FriendRequestCreation(4, null, null);
        }
        if (senderId == target.userId) {
            return new FriendRequestCreation(1, null, target);
        }
        if (sender.onlineFriends.contains(target.userId)) {
            return new FriendRequestCreation(2, null, target);
        }
        FriendRequestRecord existing = state.friendRequests.values().stream()
                .filter(request -> request.senderId == senderId && request.receiverId == target.userId)
                .findFirst().orElse(null);
        if (existing != null) {
            return new FriendRequestCreation(3, existing, target);
        }
        FriendRequestRecord request = new FriendRequestRecord();
        request.requestId = state.nextFriendRequestId++;
        request.senderId = senderId;
        request.receiverId = target.userId;
        state.friendRequests.put(request.requestId, request);
        save();
        return new FriendRequestCreation(0, request, target);
    }

    public synchronized FriendRequestUpdate updateFriendRequest(long actorId, long requestId, boolean accepted)
            throws IOException {
        FriendRequestRecord request = state.friendRequests.get(requestId);
        if (request == null || request.receiverId != actorId) {
            return new FriendRequestUpdate(2, null, null);
        }
        state.friendRequests.remove(requestId);
        AccountRecord other = account(request.senderId).orElse(null);
        if (accepted && other != null) {
            AccountRecord actor = account(actorId)
                    .orElseThrow(() -> new IllegalStateException("Actor account not found"));
            actor.onlineFriends.add(other.userId);
            other.onlineFriends.add(actor.userId);
        }
        save();
        return new FriendRequestUpdate(accepted ? 0 : 1, request, other);
    }

    public synchronized boolean deleteFriend(long actorId, long otherId) throws IOException {
        AccountRecord actor = account(actorId)
                .orElseThrow(() -> new IllegalStateException("Actor account not found"));
        AccountRecord other = account(otherId).orElse(null);
        boolean removed = actor.onlineFriends.remove(otherId);
        if (other != null) {
            other.onlineFriends.remove(actorId);
        }
        if (removed) {
            save();
        }
        return removed;
    }

    public synchronized java.util.List<FriendRequestRecord> incomingRequests(long userId) {
        return state.friendRequests.values().stream()
                .filter(request -> request.receiverId == userId).collect(java.util.stream.Collectors.toList());
    }

    public synchronized java.util.List<FriendRequestRecord> outgoingRequests(long userId) {
        return state.friendRequests.values().stream()
                .filter(request -> request.senderId == userId).collect(java.util.stream.Collectors.toList());
    }

    public synchronized PartyRecord createParty(long leaderId) throws IOException {
        PartyRecord existing = partyFor(leaderId).orElse(null);
        if (existing != null) {
            return null;
        }
        PartyRecord party = new PartyRecord();
        party.partyId = state.nextPartyId++;
        party.leaderId = leaderId;
        party.members.add(leaderId);
        state.parties.put(party.partyId, party);
        save();
        return party;
    }

    public synchronized Optional<PartyRecord> partyFor(long userId) {
        return state.parties.values().stream()
                .filter(party -> party.members.contains(userId))
                .findFirst();
    }

    public synchronized PartyInviteResult inviteToParty(long actorId, long targetId) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null) {
            party = createParty(actorId);
        }
        if (party.members.contains(targetId) || party.invitedUsers.contains(targetId)) {
            return new PartyInviteResult(1, party);
        }
        if (party.invitedUsers.size() >= 20) {
            return new PartyInviteResult(2, party);
        }
        party.invitedUsers.add(targetId);
        save();
        return new PartyInviteResult(0, party);
    }

    public synchronized PartyInviteDecision decidePartyInvite(long actorId, long inviterId, boolean accepted)
            throws IOException {
        PartyRecord party = partyFor(inviterId).orElse(null);
        if (party == null || !party.invitedUsers.remove(actorId)) {
            return new PartyInviteDecision(3, null);
        }
        if (accepted) {
            if (party.members.size() >= 20) {
                return new PartyInviteDecision(2, party);
            }
            party.members.add(actorId);
        }
        save();
        return new PartyInviteDecision(accepted ? 0 : 1, party);
    }

    public synchronized boolean uninvite(long actorId, long targetId) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null || (party.leaderId != actorId && !party.openInvites)) {
            return false;
        }
        boolean removed = party.invitedUsers.remove(targetId);
        if (removed) {
            save();
        }
        return removed;
    }

    public synchronized PartyLeaveResult leaveParty(long userId) throws IOException {
        PartyRecord party = partyFor(userId).orElse(null);
        if (party == null) {
            return new PartyLeaveResult(false, null, -1L);
        }
        party.members.remove(userId);
        long newLeaderId = party.leaderId;
        if (party.members.isEmpty()) {
            state.parties.remove(party.partyId);
            newLeaderId = -1L;
        } else if (party.leaderId == userId) {
            party.leaderId = party.members.iterator().next();
            newLeaderId = party.leaderId;
        }
        save();
        return new PartyLeaveResult(true, party, newLeaderId);
    }

    public synchronized PartyRecord deleteParty(long actorId) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null || party.leaderId != actorId) {
            return null;
        }
        state.parties.remove(party.partyId);
        save();
        return party;
    }

    public synchronized boolean kickPartyMember(long actorId, long targetId) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null || party.leaderId != actorId || targetId == actorId) {
            return false;
        }
        boolean removed = party.members.remove(targetId);
        if (removed) {
            save();
        }
        return removed;
    }

    public synchronized PartyRecord promotePartyMember(long actorId, long targetId) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null || party.leaderId != actorId || !party.members.contains(targetId)) {
            return null;
        }
        party.leaderId = targetId;
        save();
        return party;
    }

    public synchronized PartyRecord updatePartyOpenInvites(long actorId, boolean openInvites) throws IOException {
        PartyRecord party = partyFor(actorId).orElse(null);
        if (party == null || party.leaderId != actorId) {
            return null;
        }
        party.openInvites = openInvites;
        save();
        return party;
    }

    public synchronized void updatePresence(long userId, int presence) throws IOException {
        AccountRecord account = require(userId);
        account.presence = presence;
        save();
    }

    public synchronized void updateServerAddress(long userId, String serverAddress) throws IOException {
        require(userId).serverAddress = serverAddress;
        save();
    }

    public synchronized void updateShowUsername(long userId, boolean showUsername) throws IOException {
        require(userId).showUsername = showUsername;
        save();
    }

    public synchronized void updateActiveProfile(long userId, long profileId) throws IOException {
        require(userId).activeProfileId = profileId;
        save();
    }

    private AccountRecord require(long userId) {
        return account(userId).orElseThrow(() -> new IllegalStateException("Account not found: " + userId));
    }

    public static final class FriendRequestCreation {
        private final int status;
        private final FriendRequestRecord request;
        private final AccountRecord target;

        public FriendRequestCreation(int status, FriendRequestRecord request, AccountRecord target) {
            this.status = status;
            this.request = request;
            this.target = target;
        }

        public int status() {
            return status;
        }

        public FriendRequestRecord request() {
            return request;
        }

        public AccountRecord target() {
            return target;
        }
    }

    public static final class FriendRequestUpdate {
        private final int status;
        private final FriendRequestRecord request;
        private final AccountRecord other;

        public FriendRequestUpdate(int status, FriendRequestRecord request, AccountRecord other) {
            this.status = status;
            this.request = request;
            this.other = other;
        }

        public int status() {
            return status;
        }

        public FriendRequestRecord request() {
            return request;
        }

        public AccountRecord other() {
            return other;
        }
    }

    public static final class PartyInviteResult {
        private final int status;
        private final PartyRecord party;

        public PartyInviteResult(int status, PartyRecord party) {
            this.status = status;
            this.party = party;
        }

        public int status() {
            return status;
        }

        public PartyRecord party() {
            return party;
        }
    }

    public static final class PartyInviteDecision {
        private final int status;
        private final PartyRecord party;

        public PartyInviteDecision(int status, PartyRecord party) {
            this.status = status;
            this.party = party;
        }

        public int status() {
            return status;
        }

        public PartyRecord party() {
            return party;
        }
    }

    public static final class PartyLeaveResult {
        private final boolean successful;
        private final PartyRecord party;
        private final long newLeaderId;

        public PartyLeaveResult(boolean successful, PartyRecord party, long newLeaderId) {
            this.successful = successful;
            this.party = party;
            this.newLeaderId = newLeaderId;
        }

        public boolean successful() {
            return successful;
        }

        public PartyRecord party() {
            return party;
        }

        public long newLeaderId() {
            return newLeaderId;
        }
    }

    public static final class LoaderLoginResult {
        private final String token;
        private final AccountRecord account;

        public LoaderLoginResult(String token, AccountRecord account) {
            this.token = token;
            this.account = account;
        }

        public String token() {
            return token;
        }

        public AccountRecord account() {
            return account;
        }
    }

    public synchronized AuthChallengeRecord createChallenge(String edition, String hwid) throws IOException {
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        AuthChallengeRecord challenge = new AuthChallengeRecord();
        challenge.challenge = toHex(bytes);
        challenge.edition = edition;
        challenge.hwid = hwid;
        challenge.expiresAt = System.currentTimeMillis() + CHALLENGE_TTL_MILLIS;
        state.challenges.put(challenge.challenge, challenge);
        save();
        return challenge;
    }

    public synchronized boolean approveChallenge(String challengeValue) throws IOException {
        AuthChallengeRecord challenge = state.challenges.get(challengeValue);
        if (challenge == null || challenge.expiresAt < System.currentTimeMillis()) {
            return false;
        }
        if (!challenge.approved) {
            challenge.approved = true;
            challenge.accessToken = randomToken();
            AccountRecord account = new AccountRecord();
            account.userId = state.nextUserId++;
            account.username = "User" + account.userId;
            account.accountCreation = AccountRecord.nowTimestamp();
            state.accountsByToken.put(challenge.accessToken, account);
            save();
        }
        return true;
    }

    public synchronized Optional<AuthChallengeRecord> challenge(String challengeValue) {
        return Optional.ofNullable(state.challenges.get(challengeValue));
    }

    public synchronized void register(String token, String username) throws IOException {
        AccountRecord account = requireAccount(token);
        account.registered = true;
        if (username != null && !username.trim().isEmpty()) {
            account.username = username.substring(0, Math.min(username.length(), 16));
        }
        save();
    }

    public synchronized void updateMinecraftIdentity(String token, UUID uuid, String username) throws IOException {
        AccountRecord account = requireAccount(token);
        account.minecraftUuid = uuid.toString();
        account.minecraftUsername = username;
        save();
    }

    public synchronized boolean updateDisplayName(String token, String displayName) throws IOException {
        for (Map.Entry<String, AccountRecord> entry : state.accountsByToken.entrySet()) {
            if (!entry.getKey().equals(token) && entry.getValue().username.equalsIgnoreCase(displayName)) {
                return false;
            }
        }
        requireAccount(token).username = displayName;
        save();
        return true;
    }

    public synchronized JsonObject globalSettings(String token) {
        return ConfigJsonUtils.deepCopy(requireAccount(token).globalSettings);
    }

    public synchronized void saveGlobalSettings(String token, JsonObject settings) throws IOException {
        requireAccount(token).globalSettings = ConfigJsonUtils.deepCopy(settings);
        save();
    }

    public synchronized JsonObject onlineSettings(String token) {
        return ConfigJsonUtils.deepCopy(requireAccount(token).onlineSettings);
    }

    public synchronized void saveOnlineSettings(String token, JsonObject settings) throws IOException {
        requireAccount(token).onlineSettings = ConfigJsonUtils.deepCopy(settings);
        save();
    }

    public synchronized JsonObject privateData(String token) {
        AccountRecord account = requireAccount(token);
        JsonObject data = new JsonObject();
        data.add("friends", ConfigJsonUtils.deepCopy(account.localFriends));
        JsonObject profiles = new JsonObject();
        account.privateProfiles.forEach((key, value) -> profiles.add(key, ConfigJsonUtils.deepCopy(value)));
        data.add("profiles", profiles);
        data.add("publicProfiles", new JsonObject());
        data.add("otherData", ConfigJsonUtils.deepCopy(account.otherData));
        return data;
    }

    public synchronized void savePrivateUserData(String token, JsonElement userData) throws IOException {
        if (!userData.isJsonObject()) {
            throw new IllegalArgumentException("Private user data must be a JSON object");
        }
        JsonObject source = userData.getAsJsonObject();
        AccountRecord account = requireAccount(token);
        if (source.has("friends") && source.get("friends").isJsonArray()) {
            account.localFriends = ConfigJsonUtils.deepCopy(source.getAsJsonArray("friends"));
        }
        JsonElement otherData = source.has("otherData") ? source.get("otherData") : source.get("otherdata");
        if (otherData != null && otherData.isJsonArray()) {
            account.otherData = ConfigJsonUtils.deepCopy(otherData.getAsJsonArray());
        }
        save();
    }

    public synchronized JsonObject savePrivateProfiles(String token, JsonObject profiles) throws IOException {
        AccountRecord account = requireAccount(token);
        JsonArray deletedProfiles = profiles.getAsJsonArray("deletedProfiles");
        if (deletedProfiles != null) {
            for (JsonElement deletedProfile : deletedProfiles) {
                if (deletedProfile.isJsonPrimitive()) {
                    account.privateProfiles.remove(deletedProfile.getAsString());
                }
            }
        }

        JsonArray updatedProfiles = profiles.getAsJsonArray("updatedProfiles");
        if (updatedProfiles != null) {
            for (JsonElement updatedProfile : updatedProfiles) {
                if (!updatedProfile.isJsonObject()) {
                    continue;
                }
                JsonObject storedProfile = ConfigJsonUtils.deepCopy(updatedProfile.getAsJsonObject());
                String profileId = storedProfile.has("profileId")
                        ? storedProfile.get("profileId").getAsString()
                        : UUID.randomUUID().toString();
                UUID.fromString(profileId);
                storedProfile.addProperty("profileId", profileId);
                account.privateProfiles.put(profileId, storedProfile);
            }
        }
        save();
        JsonObject result = new JsonObject();
        account.privateProfiles.forEach((key, value) -> result.add(key, ConfigJsonUtils.deepCopy(value)));
        return result;
    }

    public synchronized String reserveProfileId(String token) throws IOException {
        requireAccount(token);
        String id = UUID.randomUUID().toString();
        save();
        return id;
    }

    public synchronized JsonObject publicProfiles() {
        JsonObject profiles = new JsonObject();
        state.publicProfiles.forEach((key, value) -> profiles.add(key, ConfigJsonUtils.deepCopy(value)));
        return profiles;
    }

    public synchronized JsonObject savePublicProfile(String token, JsonObject profile) throws IOException {
        AccountRecord account = requireAccount(token);
        JsonObject stored = ConfigJsonUtils.deepCopy(profile);
        String id = stored.has("id") ? stored.get("id").getAsString()
                : Long.toString(state.nextPublicProfileId++);
        stored.addProperty("id", id);
        stored.addProperty("userId", account.userId);
        state.publicProfiles.put(id, stored);
        save();
        return ConfigJsonUtils.deepCopy(stored);
    }

    public synchronized Optional<JsonObject> publicProfile(String id) {
        JsonObject profile = state.publicProfiles.get(id);
        return profile == null ? Optional.empty()
                : Optional.of(ConfigJsonUtils.deepCopy(profile));
    }

    public synchronized boolean deletePublicProfile(String token, String id) throws IOException {
        AccountRecord account = requireAccount(token);
        JsonObject profile = state.publicProfiles.get(id);
        if (profile == null || !profile.has("userId") || profile.get("userId").getAsLong() != account.userId) {
            return false;
        }
        state.publicProfiles.remove(id);
        save();
        return true;
    }

    private AccountRecord requireAccount(String token) {
        AccountRecord account = state.accountsByToken.get(token);
        if (account == null) {
            throw new IllegalArgumentException("Invalid access token");
        }
        return account;
    }

    private String randomToken() {
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return toHex(bytes);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            builder.append(Character.forDigit((value >> 4) & 0xf, 16));
            builder.append(Character.forDigit(value & 0xf, 16));
        }
        return builder.toString();
    }

    private ServiceState load() throws IOException {
        if (!Files.exists(file)) {
            return new ServiceState();
        }
        String json = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        ServiceState loaded = gson.fromJson(json, ServiceState.class);
        return loaded == null ? new ServiceState() : loaded;
    }

    private void normalizeLoadedState() {
        if (state.accountsByToken == null) {
            state.accountsByToken = new LinkedHashMap<>();
        }
        if (state.challenges == null) {
            state.challenges = new LinkedHashMap<>();
        }
        if (state.publicProfiles == null) {
            state.publicProfiles = new LinkedHashMap<>();
        }
        if (state.friendRequests == null) {
            state.friendRequests = new LinkedHashMap<>();
        }
        if (state.parties == null) {
            state.parties = new LinkedHashMap<>();
        }
        for (AccountRecord account : state.accountsByToken.values()) {
            account.accountCreation = AccountRecord.normalizeTimestamp(account.accountCreation);
            if (account.onlineFriends == null) {
                account.onlineFriends = new java.util.LinkedHashSet<>();
            }
            if (account.localFriends == null) {
                account.localFriends = new JsonArray();
            }
            if (account.otherData == null) {
                account.otherData = new JsonArray();
            }
            if (account.privateProfiles == null) {
                account.privateProfiles = new LinkedHashMap<>();
            }
        }
    }

    private void save() throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        Files.write(temporary, gson.toJson(state).getBytes(StandardCharsets.UTF_8));
        try {
            Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ignored) {
            Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
