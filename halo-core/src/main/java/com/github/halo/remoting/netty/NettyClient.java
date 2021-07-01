package com.github.halo.remoting.netty;

import com.github.halo.codec.Codec;
import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.codec.HaloCodecAdapter;
import com.github.halo.remoting.netty.handler.HaloRpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author mason.lu 2021/6/24
 */
public class NettyClient {

    private final Bootstrap bootstrap;

    //多条连接使用该EventLoop去处理
    private final EventLoopGroup eventLoopGroup;

    public NettyClient(EventLoopGroup eventLoopGroup){
        this.eventLoopGroup = eventLoopGroup;
        bootstrap = new Bootstrap();
    }

    public void connect(InetSocketAddress address, CodecTypeEnum typeEnum, Consumer<HaloRpcClientHandler> callback){
        HaloCodecAdapter adapter = new HaloCodecAdapter(typeEnum.getType());
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(adapter.getEncodeHandler())
                                .addLast(adapter.getDecodeHandler())
                                .addLast(new HaloRpcClientHandler());
                    }
                });
        //连接之后的回调
        bootstrap.connect(address.getHostString(),address.getPort())
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            callback.accept(channelFuture.channel()
                                    .pipeline()
                                    .get(HaloRpcClientHandler.class));
                        }
                    }
                });
    }

}
