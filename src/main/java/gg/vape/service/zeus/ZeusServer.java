package gg.vape.service.zeus;

import gg.vape.service.store.FileStore;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.net.InetSocketAddress;

public final class ZeusServer implements AutoCloseable {
    private final String bindAddress;
    private final int port;
    private final FileStore store;
    private final ZeusSessions sessions = new ZeusSessions();
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    public ZeusServer(String bindAddress, int port, FileStore store) {
        this.bindAddress = bindAddress;
        this.port = port;
        this.store = store;
    }

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        serverChannel = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) {
                        channel.pipeline()
                                .addLast(new ReadTimeoutHandler(45))
                                .addLast(new ZeusFrameDecoder())
                                .addLast(new ZeusClientHandler(store, sessions));
                    }
                })
                .bind(new InetSocketAddress(bindAddress, port))
                .sync()
                .channel();
    }

    public int port() {
        if (serverChannel == null) {
            throw new IllegalStateException("Zeus server has not started");
        }
        return ((InetSocketAddress)serverChannel.localAddress()).getPort();
    }

    @Override
    public void close() {
        if (serverChannel != null) {
            serverChannel.close().syncUninterruptibly();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully().syncUninterruptibly();
        }
    }
}
