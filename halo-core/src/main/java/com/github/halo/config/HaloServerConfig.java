package com.github.halo.config;

import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.server.HaloRpcServer;
import com.github.halo.utils.RpcServiceHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason.lu 2021/6/27
 */
public final class HaloServerConfig {

    private int port;

    private final Map<String,Object> serviceMap = new ConcurrentHashMap<>();

    private CodecTypeEnum codecTypeEnum;

    private int ioThreadNum;

    private int workerThreadNum;

    private HaloServerConfig(){}

    public static HaloServerConfig builder(){
        return new HaloServerConfig();
    }

    public HaloServerConfig port(int portNumber){
        this.port = portNumber;
        return this;
    }

    public HaloServerConfig workerThreadNum(int workerThreadNum){
        this.workerThreadNum = workerThreadNum;
        return this;
    }

    public HaloServerConfig ioThreadNum(int ioThreadNum){
        this.ioThreadNum = ioThreadNum;
        return this;
    }


    public HaloServerConfig codec(CodecTypeEnum typeEnum){
        this.codecTypeEnum = typeEnum;
        return this;
    }

    public <T,E extends T> HaloServerConfig export(Class<T> intr,E impl){
        return export(intr,impl,"1.0");
    }

    public <T,E extends T> HaloServerConfig export(Class<T> intr,E impl,String version){
        if (Objects.isNull(intr) || Objects.isNull(impl) || Objects.isNull(version)){
            throw new IllegalArgumentException("please check argument!");
        }
        if (!intr.isAssignableFrom(impl.getClass())){
            throw new IllegalArgumentException("impl is could't assign from interface");
        }
        if (StringUtils.isNumeric(version)){
            throw new IllegalArgumentException("version is illegal!");
        }
        serviceMap.put(RpcServiceHelper.buildServiceKey(intr.getName(),version),impl);
        return this;
    }
    public HaloServerConfig export(String serviceName,Object service,String version){
        if (StringUtils.isBlank(serviceName) || Objects.isNull(service) || Objects.isNull(version)){
            throw new IllegalArgumentException("please check argument!");
        }
        if (!StringUtils.isNumeric(version)){
            throw new IllegalArgumentException("version is illegal!");
        }
        serviceMap.put(RpcServiceHelper.buildServiceKey(serviceName,version),service);
        return this;
    }

    public HaloServerConfig export(String serviceName,Object service){
        return export(serviceName,service,"1.0");
    }
    /**
     * 检验配置
     * */
    private void validateConfig(){
        if (serviceMap.isEmpty()){
            throw new IllegalArgumentException("no beans will be export! ");
        }
        //todo check info
    }

    public HaloRpcServer startServer(){
        validateConfig();
        return new HaloRpcServer(this);
    }

    public CodecTypeEnum getCodecTypeEnum() {
        return codecTypeEnum;
    }

    public Map<String, Object> getServiceMap() {
        return serviceMap;
    }

    public int getPort() {
        return port;
    }

    public int getIoThreadNum() {
        return ioThreadNum;
    }

    public int getWorkerThreadNum() {
        return workerThreadNum;
    }
}
