package com.github.halo.common.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * RPC 响应定义
 * @author mason.lu 2021/6/25
 */
@Data
public class HaloRpcResponse implements Serializable {
    private Object data;
    private String message;
    private Throwable throwable;
}
