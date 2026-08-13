package gg.vape.account;

import gg.vape.mapping.mappings.MSession;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.SessionType;
import java.util.Optional;
import java.util.UUID;

public class MinecraftSessionWrapper
extends Wrapper {

    public static MinecraftSessionWrapper createModern(String username, UUID profileId, String accessToken,
                                                       Optional<String> xuid, Optional<String> clientId,
                                                       SessionType accountType) {
        return new MinecraftSessionWrapper(MSession.B(MinecraftSessionWrapper.vapeInstance.getMappings().hw, username,
                profileId, accessToken, xuid, clientId, accountType.getObject()));
    }

    public MinecraftSessionWrapper(Object session) {
        super(session);
    }

    public String getUsername() {
        return MinecraftSessionWrapper.vapeInstance.getMappings().hw.s(this.I);
    }

    public UUID getProfileId() {
        if (ForgeVersion.MC_1_20_6.d()) {
            return (UUID)MinecraftSessionWrapper.vapeInstance.getMappings().hw.V(this.I);
        }
        String rawProfileId = (String)MinecraftSessionWrapper.vapeInstance.getMappings().hw.V(this.I);
        if (rawProfileId == null || rawProfileId.isEmpty()) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
        return UUID.fromString(rawProfileId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

    public static MinecraftSessionWrapper createLegacy(String username, String profileId, String accessToken,
                                                       String sessionType) {
        return new MinecraftSessionWrapper(MSession.t(MinecraftSessionWrapper.vapeInstance.getMappings().hw, username,
                profileId, accessToken, sessionType));
    }
}
