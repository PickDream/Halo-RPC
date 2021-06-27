package com.github.halo.common;

/**
 * RPC响应处理器
 * @author mason.lu 2021/6/27
 */
public interface RpcCallback {
    void handleSuccess(Object result);
    void handleFailure(Throwable cause);
}
