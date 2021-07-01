package com.github.halo.remoting.manager;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.config.HaloClientConfig;
import com.github.halo.remoting.netty.NettyClient;
import com.github.halo.remoting.netty.handler.HaloRpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于客户端的连接管理器,对多个RPCClient的连接进行统一管理
 * @author mason.lu 2021/6/24
 */
public class RpcConnectionManager {

    //连接管理器的状态
    //WAITING -> RUNNING -> STOP
    private volatile int STATUS;

    //异步建立连接的线程池
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(64));

    private final Map<InetSocketAddress, HaloRpcClientHandler> clientHandlerMap =
            new ConcurrentHashMap<>();

    public final EventLoopGroup eventLoopGroup;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition waitConnection = lock.newCondition();

    private final HaloClientConfig clientConfig;

    public RpcConnectionManager(HaloClientConfig config){
        eventLoopGroup = new NioEventLoopGroup(4);
        clientConfig = config;
    }


    public void connect(Set<InetSocketAddress> serverAddress){
        this.updateConnectionServer(serverAddress);
    }

    /**
     * 更新连接的服务器
     * */
    public void updateConnectionServer(Set<InetSocketAddress> serverAddress){
        Set<InetSocketAddress> connectedAddress = clientHandlerMap.keySet();
        //1. 建立连接
        for (InetSocketAddress address : serverAddress) {
            if (!connectedAddress.contains(address)) {
                //调用连接方法
                doConnect(address);
            }
        }
        //2. 清除连接
        for (InetSocketAddress address:connectedAddress){
            if (!serverAddress.contains(address)){
                HaloRpcClientHandler haloRpcClientHandler = clientHandlerMap.get(address);
                if (haloRpcClientHandler!=null){
                    haloRpcClientHandler.closeChannel();
                    clientHandlerMap.remove(address);
                }
            }
        }
    }

    private void doConnect(InetSocketAddress remote){
        new NettyClient(eventLoopGroup).connect(remote,clientConfig.getCodecTypeEnum(),(handler)->{
                    clientHandlerMap.put(remote,handler);
                });
    }














}


