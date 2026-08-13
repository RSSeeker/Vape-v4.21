package gg.vape.protocol.packet;

public enum PingTargetKind {
    POSITION,
    BLOCK,
    ENTITY;

    private static final PingTargetKind[] T;

    static {
        String[] stringArray = new String[]{"BLOCK", "POSITION", "ENTITY"};



        T = new PingTargetKind[]{POSITION, BLOCK, ENTITY};
    }
}

