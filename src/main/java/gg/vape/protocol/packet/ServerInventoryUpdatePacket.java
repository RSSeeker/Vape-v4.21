package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class ServerInventoryUpdatePacket
implements ZeusSerializablePacket {
    private Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems;
    private long userId;
    private static int[] J;
    private static final String b;

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        int n = zeusPacketBuffer.readVarInt();
        if (n > 40) {
            throw new RuntimeException(b);
        }
        this.inventoryItems = new HashMap<Integer, ActivityItemStackPayload>();
        for (int i = 0; i < n; ++i) {
            int n2 = zeusPacketBuffer.readVarInt();
            boolean bl = zeusPacketBuffer.readBoolean();
            this.inventoryItems.put(n2, bl ? new ActivityItemStackPayload(zeusPacketBuffer) : null);
        }
    }

    public Map<Integer, @Nullable ActivityItemStackPayload> getInventoryItems() {
        return this.inventoryItems;
    }

    public long getUserId() {
        return this.userId;
    }

    static {
        ServerInventoryUpdatePacket.N(new int[1]);
        b = "Too many items in inventory";
    }

    public ServerInventoryUpdatePacket(UserModel userModel, Map<Integer, @Nullable ActivityItemStackPayload> inventoryItems) {
        this.userId = userModel.getId();
        this.inventoryItems = inventoryItems;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeVarInt(this.inventoryItems.size());
        for (Map.Entry<Integer, ActivityItemStackPayload> entry : this.inventoryItems.entrySet()) {
            zeusPacketBuffer.writeVarInt(entry.getKey());
            zeusPacketBuffer.writeBoolean(entry.getValue() != null);
            if (entry.getValue() == null) continue;
            entry.getValue().writeTo(zeusPacketBuffer);
        }
    }

    public static int[] R() {
        return J;
    }

    public static void N(int[] nArray) {
        J = nArray;
    }

    public ServerInventoryUpdatePacket() {
    }

    private static RuntimeException a(RuntimeException runtimeException) {
        return runtimeException;
    }
}
