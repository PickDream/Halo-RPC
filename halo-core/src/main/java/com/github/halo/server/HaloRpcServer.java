package com.github.halo.server;

import com.github.halo.config.HaloServerConfig;
import com.github.halo.remoting.netty.NettyServer;

/**
 * 暂时无用，一个最外层封装NettyServer的类
 * @author mason.lu 2021/6/27
 */
public class HaloRpcServer {
    public final NettyServer nettyServer;

    public HaloRpcServer(HaloServerConfig haloServerConfig){
        nettyServer = new NettyServer(haloServerConfig);
        try {
            nettyServer.startServer();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
