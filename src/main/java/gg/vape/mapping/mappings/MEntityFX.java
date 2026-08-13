package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntityFX
extends Mapping {
    private static final String PACKETS_FIELD_NAME;
    private static int controlFlowState;
    private final MappingField packetsField;

    static {
        MEntityFX.setPacketQueueControlFlowState(0);
        PACKETS_FIELD_NAME = "packets";
    }

    public Iterable<?> getPacketHandles(Object packetQueueEntry) {
        return (Iterable<?>)this.packetsField.getObject(packetQueueEntry);
    }

    public static int getControlFlowSentinel() {
        return 45;
    }

    public static int getPacketQueueControlFlowState() {
        return controlFlowState;
    }

    public static void setPacketQueueControlFlowState(int state) {
        controlFlowState = state;
    }

    public MEntityFX() {
        this(MEntityFX.getPacketQueueControlFlowState());
    }

    private MEntityFX(int controlFlowState) {
        super(MappedClasses.ue);
        this.packetsField = this.J(PACKETS_FIELD_NAME, true, Iterable.class);
    }
}

