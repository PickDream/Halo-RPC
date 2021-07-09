package com.github.halo.common;

import java.util.Objects;
import java.util.Stack;

/**
 * 用于在异步调用的时候RPCFuture便于后续操作
 * @author mason.lu 2021/6/28
 */
public class RpcContext {

    private static final ThreadLocal<Stack<HaloRpcFuture>>
        RPC_CONTENT_HOLDER = ThreadLocal.withInitial(Stack::new);

    public static HaloRpcFuture getFuture(){
        Stack<HaloRpcFuture> haloRpcFutures = RPC_CONTENT_HOLDER.get();
        if (Objects.isNull(haloRpcFutures) || haloRpcFutures.isEmpty()){
            throw new IllegalStateException("no avaliable async handler!");
        }
        return haloRpcFutures.pop();
    }

    public static void putFuture(HaloRpcFuture rpcFuture){
        if (Objects.isNull(rpcFuture)){
            throw new NullPointerException("rpcFuture must not be null!");
        }
        RPC_CONTENT_HOLDER.get().push(rpcFuture);
    }
}
