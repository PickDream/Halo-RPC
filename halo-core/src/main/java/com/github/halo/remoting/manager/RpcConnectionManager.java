package com.github.halo.remoting.manager;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcRequest;
import com.github.halo.config.HaloClientConfig;
import com.github.halo.remoting.netty.NettyClient;
import com.github.halo.remoting.netty.handler.HaloRpcClientHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于客户端的连接管理器,对多个RPCClient的连接进行统一管理
 * @author mason.lu 2021/6/24
 */
public class RpcConnectionManager {


    //异步建立连接的线程池
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(64));

    private final Map<InetSocketAddress, HaloRpcClientHandler> clientHandlerMap =
            new ConcurrentHashMap<>();

    public final EventLoopGroup eventLoopGroup;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition waitConnection = lock.newCondition();

    private final HaloClientConfig clientConfig;

    private final long connectTimeoutMills;

    public RpcConnectionManager(HaloClientConfig config){
        eventLoopGroup = new NioEventLoopGroup(4);
        clientConfig = config;
        this.connectTimeoutMills = config.getConnectTimeout();
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

    public void sendRequest(HaloRpcPacket<HaloRpcRequest> packet) {
        while (clientHandlerMap.isEmpty()){
            try {
                boolean available = waitForHandlerAvailable();
                if (available){
                    //get snapshoot

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean waitForHandlerAvailable() throws InterruptedException{
        lock.lock();
        try{
            return waitConnection.await(connectTimeoutMills,TimeUnit.MILLISECONDS);
        }finally {
            lock.unlock();;
        }
    }

    public void signalForHandlerAvailable(){
        lock.lock();
        try{
            waitConnection.signalAll();
        }finally {
            lock.unlock();
        }
    }
}


