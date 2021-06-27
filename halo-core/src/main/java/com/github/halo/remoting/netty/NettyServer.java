package com.github.halo.remoting.netty;

import com.github.halo.codec.HaloCodecAdapter;
import com.github.halo.common.URL;
import com.github.halo.common.constant.ProtocolConstant;
import com.github.halo.remoting.netty.AbstractServer;
import com.github.halo.utils.NettyEventLoopFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author mason.lu 2021/6/19
 */
public class NettyServer extends AbstractServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(URL url) {
        super(url);
    }


    protected void doOpen() throws Throwable {
        URL url = getURL();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        bossGroup = NettyEventLoopFactory.eventLoopGroup(1,"NettyServerBoss");
        // todo getEventLoopSize from config url
        workerGroup = NettyEventLoopFactory.eventLoopGroup(10,"NettyServerWorker");
        ThreadFactory threadFactory;
//        bossGroup = new NioEventLoopGroup(, threadFactory);

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NettyEventLoopFactory.serverSocketChannelClass())
                //处于半连接的队列长度设置
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.SO_REUSEADDR,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        HaloCodecAdapter adapter = new HaloCodecAdapter(getCodec());
                        pipeline.addLast(ProtocolConstant.DECODER_NAME,adapter.getDecodeHandler())
                                .addLast(ProtocolConstant.ENCODER_NAME,adapter.getEncodeHandler());

                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(getBindAddress());
        channelFuture.syncUninterruptibly();
        channelFuture.channel();
    }

    protected void doClose() throws Throwable {

    }
}
