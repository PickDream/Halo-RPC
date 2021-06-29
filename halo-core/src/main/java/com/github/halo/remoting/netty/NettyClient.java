package com.github.halo.remoting.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;

/**
 * @author mason.lu 2021/6/24
 */
public class NettyClient {
    private Bootstrap bootstrap;

    private EventLoopGroup eventLoopGroup;

    public NettyClient(){
        bootstrap = new Bootstrap();

    }

}
