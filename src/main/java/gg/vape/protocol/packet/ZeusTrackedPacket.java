package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import gg.vape.ui.click.component.GuiComponent;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public abstract class ZeusTrackedPacket<R extends ZeusTrackedPacket>
implements ZeusSerializablePacket {
    private UUID A;
    private static GuiComponent[] p;

    @Override
    public final void S(ZeusPacketBuffer gx_12) {
        this.A = gx_12.readUuid();
        this.x(gx_12);
    }

    public abstract void T(ZeusPacketBuffer var1);

    public static void B(GuiComponent[] upArray) {
        p = upArray;
    }

    public static GuiComponent[] q$src$ALgg_vape_ui_click_component_GuiComponent_$1592yx4() {
        return p;
    }

    public abstract void x(ZeusPacketBuffer var1);

    @Override
    public final void o(ZeusPacketBuffer gx_12) {
        gx_12.writeUuid(this.A);
        this.T(gx_12);
    }

    public ZeusTrackedPacket(@Nullable R r) {
        this.A = r != null ? ((ZeusTrackedPacket)r).o$src$Ljava_util_UUID_$1pm4r8s() : UUID.randomUUID();
    }

    public ZeusTrackedPacket() {
        this.A = UUID.randomUUID();
    }

    public UUID o$src$Ljava_util_UUID_$1pm4r8s() {
        return this.A;
    }

    static {
        if (ZeusTrackedPacket.q$src$ALgg_vape_ui_click_component_GuiComponent_$1592yx4() == null) {
            ZeusTrackedPacket.B(new GuiComponent[3]);
        }
    }
}

