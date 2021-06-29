package com.github.halo.utils;

/**
 * @author mason.lu 2021/6/29
 */
public class RpcServiceHelper {
    public static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join("#", serviceName, serviceVersion);
    }
}
