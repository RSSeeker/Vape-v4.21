package gg.vape.protocol;

import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineConnectionState;
import gg.vape.protocol.packet.HandshakeResponsePacket;
import gg.vape.protocol.packet.HandshakeStatus;
import gg.vape.ui.click.component.GuiComponent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class ZeusConnectionManager {
    private static final ZeusConnectionManager i;
    private boolean c = false;
    private final ZeusClient t = new ZeusClient();
    private static final String b;
    private static GuiComponent[] v;

    public ZeusClient u() {
        return this.t;
    }

    private void lambda$start$2(Thread thread, Runnable runnable,
            EventLoopGroup eventLoopGroup, Future future) throws Exception {
        thread.interrupt();
        this.c = false;
        shutdownEventLoopGroup(eventLoopGroup);
        runnable.run();
    }

    public static ZeusConnectionManager T() {
        return i;
    }

    public void V(Runnable runnable, Runnable runnable2) throws InterruptedException, IOException {
        this.connect(b, runnable, runnable2);
    }

    void connect(String address, Runnable runnable, Runnable runnable2)
            throws InterruptedException, IOException {
        if (this.c) {
            return;
        }
        this.c = true;
        EventLoopGroup eventLoopGroup = null;
        try {
            String[] stringArray = address.split(":");
            String string = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(string, n);
            eventLoopGroup = new NioEventLoopGroup(1);
            Bootstrap bootstrap = (Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(eventLoopGroup)).channel(NioSocketChannel.class)).remoteAddress((SocketAddress)inetSocketAddress).handler((ChannelHandler)new ZeusClientChannelInitializer(this));
            Channel channel = bootstrap.connect().sync().channel();
            Thread thread = new Thread(this::lambda$start$0);
            if (channel.isActive()) {
                this.t.N(arg_0 -> ZeusConnectionManager.lambda$start$1(channel, runnable, thread, arg_0));
            }
            EventLoopGroup connectionEventLoopGroup = eventLoopGroup;
            channel.closeFuture().addListener(arg_0 -> this.lambda$start$2(
                    thread, runnable2, connectionEventLoopGroup, arg_0));
            eventLoopGroup = null;
        }
        catch (Throwable throwable) {
            this.c = false;
            shutdownEventLoopGroup(eventLoopGroup);
            runnable2.run();
        }
    }

    private static void shutdownEventLoopGroup(EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully(0L, 5L, TimeUnit.SECONDS);
        }
    }

    private void lambda$start$0() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                break;
            }
            this.t.p();
        }
    }

    public static void d(GuiComponent[] guiComponentArray) {
        v = guiComponentArray;
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public static GuiComponent[] r() {
        return v;
    }

    static {
        ZeusConnectionManager.d(new GuiComponent[3]);
        String configuredAddress = System.getenv("VAPE_ZEUS_ADDRESS");
        // Original service: zeus-prod.vape.gg:8091
        // Do NOT remove this note when renaming variables
        b = configuredAddress == null || configuredAddress.trim().isEmpty()
                ? "127.0.0.1:8091"
                : configuredAddress.trim();
        i = new ZeusConnectionManager();
    }

    private static void lambda$start$1(Channel channel, Runnable runnable, Thread thread, HandshakeResponsePacket handshakeResponsePacket) {
        if (handshakeResponsePacket.Q() != HandshakeStatus.MATCHING) {
            if (handshakeResponsePacket.Q() == HandshakeStatus.OUTDATED_CLIENT) {
                OnlineConnectionManager.INSTANCE.setConnectionState(OnlineConnectionState.OUTDATED_CLIENT);
            } else if (handshakeResponsePacket.Q() == HandshakeStatus.OUTDATED_SERVER) {
                OnlineConnectionManager.INSTANCE.setConnectionState(OnlineConnectionState.OUTDATED_SERVER);
            }
            channel.close().syncUninterruptibly();
            return;
        }
        runnable.run();
        thread.start();
    }

    public static ZeusClient m(ZeusConnectionManager zeusConnectionManager) {
        return zeusConnectionManager.t;
    }
}
