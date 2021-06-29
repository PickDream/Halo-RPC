package com.github.halo.common;

import lombok.Data;

/**
 * 用于负载均衡服务的元信息
 * @author mason.lu 2021/6/29
 */
@Data
public class ServiceMeta {
    private String serviceName;
    private String serviceVersion;
    private String serviceAddress;
    private int servicePort;
}