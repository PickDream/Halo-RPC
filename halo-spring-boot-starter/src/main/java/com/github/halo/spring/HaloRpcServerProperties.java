package com.github.halo.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mason.lu 2021/7/14
 */
@ConfigurationProperties(
        prefix = "halo.server"
)
public class HaloRpcServerProperties {
}
