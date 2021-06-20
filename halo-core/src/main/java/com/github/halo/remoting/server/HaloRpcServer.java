package com.github.halo.remoting.server;

import com.github.halo.common.URL;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author mason.lu 2021/6/19
 */
public class HaloRpcServer extends AbstractServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public HaloRpcServer(URL url) {
        super(url);
    }


    protected void doOpen() throws Throwable {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ThreadFactory threadFactory;
//        bossGroup = new NioEventLoopGroup(, threadFactory);
        URL url = getURL();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //处于半连接的队列长度设置
                .option(ChannelOption.SO_BACKLOG,1024)
//                .childHandler(new ServerChannelInitializer())
                .childOption(ChannelOption.TCP_NODELAY,true);
    }

    protected void doClose() throws Throwable {

    }
}
