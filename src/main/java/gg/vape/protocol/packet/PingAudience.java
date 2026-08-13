package gg.vape.protocol.packet;

public enum PingAudience {
    FRIENDS,
    GROUP;

    private static final PingAudience[] K;

    static {
        String[] stringArray = new String[]{"GROUP", "FRIENDS"};


        K = new PingAudience[]{FRIENDS, GROUP};
    }

}

