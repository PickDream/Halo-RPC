package com.github.halo.remoting.manager;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 用于客户端的连接管理器,对多个RPCClient的连接进行统一管理
 * @author mason.lu 2021/6/24
 */
public class RpcConnectionManager {

    //连接管理器的状态
    //WAITING -> RUNNING -> STOP
    private volatile int STATUS;

}


