package com.github.halo.client;

import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.config.HaloClientConfig;
import com.github.halo.proxy.ReferenceProxyFactory;
import com.github.halo.remoting.manager.RpcConnectionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason.lu 2021/6/27
 */
public class HaloRpcClient {

    //远程连接处理器
    private final RpcConnectionManager connectionManager;

    //代理对象Map
    private final Map<Class<?>,Object> proxyInstanceMap = new ConcurrentHashMap<>();



    public <T> T getReference(Class<T> clazz){
        return getReference(clazz,false);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T getReference(Class<T> clazz,boolean async){
        if (proxyInstanceMap.containsKey(clazz)){
            return (T) proxyInstanceMap.get(clazz);
        }
        T proxy = ReferenceProxyFactory.getProxy(clazz, "1.0", CodecTypeEnum.HESSIAN, 10000L, connectionManager,async);
        proxyInstanceMap.put(clazz,proxy);
        return proxy;
    }

    public HaloRpcClient(HaloClientConfig config){
        connectionManager = new RpcConnectionManager(config);
        connectionManager.connect(config.getServerAddress());
    }

}
