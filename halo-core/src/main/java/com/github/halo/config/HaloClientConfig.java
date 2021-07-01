package com.github.halo.config;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.codec.CodecTypeEnum;

/**
 * @author mason.lu 2021/6/27
 */
public final class HaloClientConfig {

    private CodecTypeEnum codecTypeEnum;

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
}
