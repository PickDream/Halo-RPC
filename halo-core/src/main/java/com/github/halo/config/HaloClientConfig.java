package com.github.halo.config;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.codec.CodecTypeEnum;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mason.lu 2021/6/27
 */
public final class HaloClientConfig {

    private CodecTypeEnum codecTypeEnum;

    private final Set<InetSocketAddress> serverAddress = new HashSet<>();

    private HaloClientConfig(){}

    private long connectTimeout;

    public HaloRpcClient start(){
        return null;
    };

    public HaloClientConfig registryType(){
        return this;
    }
    public HaloClientConfig registryAddress(InetSocketAddress socketAddress){
        return this;
    }
    public HaloClientConfig serverAddress(InetSocketAddress socketAddress){
        if (socketAddress!=null){
            serverAddress.add(socketAddress);
        }
        return this;
    }
    public HaloClientConfig codec(CodecTypeEnum typeEnum){
        this.codecTypeEnum = typeEnum;
        return this;
    }
    public static HaloClientConfig builder(){
        return new HaloClientConfig();
    }

    public CodecTypeEnum getCodecTypeEnum() {
        return codecTypeEnum;
    }

    public HaloClientConfig connectTimeout(long timeout){
        this.connectTimeout = timeout;
        return this;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }
}
