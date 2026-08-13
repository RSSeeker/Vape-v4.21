package gg.vape.protocol.packet;

import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientActivitySnapshotPacket
implements ZeusSerializablePacket {
    private ActivitySnapshotPayload activitySnapshot;

    public ClientActivitySnapshotPacket() {
    }

    public ActivitySnapshotPayload getActivitySnapshot() {
        return this.activitySnapshot;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.activitySnapshot = new ActivitySnapshotPayload(zeusPacketBuffer);
    }

    public ClientActivitySnapshotPacket(ActivitySnapshotPayload activitySnapshot) {
        this.activitySnapshot = activitySnapshot;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.activitySnapshot.writeTo(zeusPacketBuffer);
    }
}
