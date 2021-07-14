package com.github.halo.spring;

import com.github.halo.remoting.manager.RpcConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mason.lu 2021/7/14
 */
@Configuration
@EnableConfigurationProperties({HaloRpcClientProperties.class,HaloRpcServerProperties.class})
public class HaloRpcAutoConfiguration {

    private HaloRpcClientProperties clientProperties;

    private HaloRpcServerProperties serverProperties;

    public HaloRpcAutoConfiguration(HaloRpcClientProperties clientProperties,HaloRpcServerProperties serverProperties){
        this.clientProperties = clientProperties;
        this.serverProperties = serverProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcConnectionManager connectionManager(){
        //todo
        return null;
    }

    public static class AutoConfigHaloRpcReferenceRegistrar{

    }
}
