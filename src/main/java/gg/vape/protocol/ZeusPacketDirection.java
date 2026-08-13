package gg.vape.protocol;

public enum ZeusPacketDirection {
    CLIENT,
    SERVER;

    private static final /* synthetic */ ZeusPacketDirection[] P;
    private static String[] Q;

    public static void U(String[] stringArray) {
        Q = stringArray;
    }

    public static String[] F() {
        return Q;
    }

    static {
        if (ZeusPacketDirection.F() != null) {
            ZeusPacketDirection.U(new String[5]);
        }
        String[] stringArray = new String[]{"CLIENT", "SERVER"};


        P = new ZeusPacketDirection[]{CLIENT, SERVER};
    }
}

