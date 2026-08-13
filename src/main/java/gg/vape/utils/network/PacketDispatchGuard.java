package gg.vape.utils.network;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.concurrent.ReadWriteLockHelper;
import gg.vape.utils.network.PacketDispatchMarkerRegistry;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.NetworkPacketHandle;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.PlayerControllerMP;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PacketDispatchGuard
implements EventListener {
    private final ReadWriteLockHelper K;
    private final Set<Object> s = ConcurrentHashMap.newKeySet();
    private boolean B = false;
    public static PacketDispatchGuard b = new PacketDispatchGuard();

    public boolean o(EventPacketSend eventPacketSend) {
        if (PacketDispatchMarkerRegistry.J(eventPacketSend.getPacket())) {
            return false;
        }
        if (PacketDispatchMarkerRegistry.S(eventPacketSend.getPacket())) {
            return false;
        }
        NetworkManager networkManager = eventPacketSend.getNetworkManager();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            NetworkPacketHandle networkPacketHandle = networkManager.c();
            if (!networkPacketHandle.isInstance(MappedClasses.F1)) {
                return false;
            }
            this.R(eventPacketSend.getPacket());
            try {
                networkManager.G(eventPacketSend.getPacket());
                return true;
            }
            catch (Exception exception) {}
        } else {
            networkManager.getChannel().eventLoop().execute(() -> this.lambda$invokeWrite$1(eventPacketSend));
        }
        return false;
    }

    private void lambda$invokeRead$0(Packet packet, NetHandlerPlayClientImpl netHandlerPlayClientImpl) {
        this.l(packet, netHandlerPlayClientImpl);
    }

    public static void B(Runnable runnable) {
        PlayerControllerMP playerControllerMP = Minecraft.playerController();
        if (playerControllerMP.isNull()) {
            return;
        }
        NetHandlerPlayClientImpl netHandlerPlayClientImpl = playerControllerMP.n();
        NetworkManager networkManager = netHandlerPlayClientImpl.a();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            runnable.run();
        } else {
            networkManager.getChannel().eventLoop().execute(runnable);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPacketReceive(EventPacketReceive eventPacketReceive) {
        this.J(eventPacketReceive.getPacket());
    }

    public void J(Packet packet) {
        this.s.remove(packet.getObject());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPacketSend(EventPacketSend eventPacketSend) {
        this.J(eventPacketSend.getPacket());
    }

    public boolean o(Packet packet) {
        boolean bl = this.s.contains(packet.getObject());
        return bl;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public boolean R(Packet packet) {
        boolean bl = this.s.add(packet.getObject());
        return !bl;
    }

    public static void b(NetworkManager networkManager, Runnable runnable) {
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            runnable.run();
        } else {
            networkManager.getChannel().eventLoop().execute(runnable);
        }
    }

    public PacketDispatchGuard() {
        this.K = new ReadWriteLockHelper();
    }

    private void lambda$invokeWrite$1(EventPacketSend eventPacketSend) {
        this.o(eventPacketSend);
    }

    public void l(Packet packet, NetHandlerPlayClientImpl netHandlerPlayClientImpl) {
        if (netHandlerPlayClientImpl.isNull()) {
            return;
        }
        NetworkManager networkManager = netHandlerPlayClientImpl.a();
        if (networkManager.getChannel().eventLoop().inEventLoop()) {
            this.R(packet);
            try {
                packet.processPacket(netHandlerPlayClientImpl);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.J(packet);
        } else {
            networkManager.getChannel().eventLoop().execute(() -> this.lambda$invokeRead$0(packet, netHandlerPlayClientImpl));
        }
    }
}
