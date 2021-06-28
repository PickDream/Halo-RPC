package com.github.halo.config;

import com.github.halo.client.HaloRpcClient;

/**
 * @author mason.lu 2021/6/27
 */
public final class HaloClientConfig {
    private HaloClientConfig(){};

    public HaloRpcClient start(){
        return null;
    };

    public HaloClientConfig registryType(){
        return this;
    }
    public HaloClientConfig registryAddress(){
        return this;
    }
    public HaloClientConfig serverAddress(String addr){
        return this;
    }
    public static HaloClientConfig builder(){
        return new HaloClientConfig();
    }
}
