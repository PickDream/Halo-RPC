package com.github.halo.common.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC请求定义
 * @author mason.lu 2021/6/22
 */
@Data
public class HaloRpcRequest implements Serializable {
    private String version;
    private String className;
    private String methodName;
    private Object[] params;
    private Class<?>[] parameterType;
}
