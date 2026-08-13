package gg.vape.protocol.packet;

import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ActivitySnapshotsPacket
implements ZeusSerializablePacket {
    private ActivitySnapshotPayload[] snapshots;
    private long[] userIds;

    public ActivitySnapshotsPacket(long[] userIds, ActivitySnapshotPayload[] snapshots) {
        this.userIds = userIds;
        this.snapshots = snapshots;
    }

    public long[] getUserIds() {
        return this.userIds;
    }


    public ActivitySnapshotsPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        int n = zeusPacketBuffer.readVarInt();
        this.userIds = new long[n];
        this.snapshots = new ActivitySnapshotPayload[n];
        for (int i = 0; i < n; ++i) {
            this.userIds[i] = zeusPacketBuffer.readLong();
            this.snapshots[i] = new ActivitySnapshotPayload(zeusPacketBuffer);
        }
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeVarInt(this.userIds.length);
        for (int i = 0; i < this.userIds.length; ++i) {
            zeusPacketBuffer.writeLong(this.userIds[i]);
            this.snapshots[i].writeTo(zeusPacketBuffer);
        }
    }

    public ActivitySnapshotPayload[] getSnapshots() {
        return this.snapshots;
    }
}
