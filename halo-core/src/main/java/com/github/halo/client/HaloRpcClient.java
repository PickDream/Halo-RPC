package com.github.halo.client;

import com.github.halo.remoting.manager.RpcConnectionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason.lu 2021/6/27
 */
public class HaloRpcClient {

    //远程连接处理器
    private RpcConnectionManager connectionManager;

    //代理对象Map
    private final Map<Class<?>,Object> proxyInstanceMap = new ConcurrentHashMap<>();



    public <T> T getReference(Class<T> clazz){
        return getReference(clazz,false);
    }

    public <T> T getReference(Class<T> clazz,boolean async){
        return null;
    }

}
