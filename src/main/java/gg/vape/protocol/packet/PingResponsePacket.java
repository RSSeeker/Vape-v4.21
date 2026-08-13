package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingPacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.Nullable;

public class PingResponsePacket
extends ZeusTrackedPacket<PingPacket> {
    private long cooldownEndNanos;
    private boolean requestAccepted;
    private int remainingPingAllowance;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.requestAccepted = zeusPacketBuffer.readBoolean();
        this.remainingPingAllowance = zeusPacketBuffer.readVarInt();
        this.cooldownEndNanos = zeusPacketBuffer.readLong();
    }

    public PingResponsePacket(@Nullable PingPacket pingPacket, boolean requestAccepted, int remainingPingAllowance, long cooldownEndNanos) {
        super(pingPacket);
        this.requestAccepted = requestAccepted;
        this.remainingPingAllowance = remainingPingAllowance;
        this.cooldownEndNanos = cooldownEndNanos;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeBoolean(this.requestAccepted);
        zeusPacketBuffer.writeVarInt(this.remainingPingAllowance);
        zeusPacketBuffer.writeLong(this.cooldownEndNanos);
    }

    public long getCooldownEndNanos() {
        return this.cooldownEndNanos;
    }

    public boolean isRequestAccepted() {
        return this.requestAccepted;
    }

    public int getRemainingPingAllowance() {
        return this.remainingPingAllowance;
    }

    public PingResponsePacket() {
    }
}
