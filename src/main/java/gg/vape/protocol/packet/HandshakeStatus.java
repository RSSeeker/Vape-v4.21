package gg.vape.protocol.packet;

public enum HandshakeStatus {
    MATCHING,
    OUTDATED_CLIENT,
    OUTDATED_SERVER;

    private static final HandshakeStatus[] q;

    static {
        String[] stringArray = new String[]{"OUTDATED_SERVER", "MATCHING", "OUTDATED_CLIENT"};



        q = new HandshakeStatus[]{MATCHING, OUTDATED_CLIENT, OUTDATED_SERVER};
    }
}

