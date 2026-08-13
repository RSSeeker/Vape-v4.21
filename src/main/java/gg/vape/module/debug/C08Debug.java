package gg.vape.module.debug;

import gg.vape.utils.MutableFloatTriple;
import gg.vape.utils.datas.DirectionalPosition;

public class C08Debug {
    private int sequence = -1;
    private Boolean insideBlock;
    private final DirectionalPosition blockPosition;
    private final int packetId;
    private final MutableFloatTriple facingVector;

    public void setInsideBlock(Boolean insideBlock) {
        this.insideBlock = insideBlock;
    }

    @Override
    public String toString() {
        String template = "[C08 | %d], Block: %s, FacingVec: %s";
        if (this.sequence != -1) {
            template = template + ", Seq: " + this.sequence;
        }
        if (this.insideBlock != null) {
            template = template + ", Inside: " + this.insideBlock;
        }
        return String.format(template, this.packetId, this.blockPosition.toString(), this.facingVector);
    }

    public C08Debug(int packetId, DirectionalPosition blockPosition, MutableFloatTriple facingVector) {
        this.packetId = packetId;
        this.blockPosition = blockPosition;
        this.facingVector = facingVector;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
