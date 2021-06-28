package com.github.halo.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason.lu 2021/6/27
 */
public class HaloRpcClient {

    //代理对象Map
    private final Map<Class<?>,Object> proxyInstanceMap = new ConcurrentHashMap<>();

    public <T> T getReference(Class<T> clazz){
        //TODO
        return null;
    }

}
