package com.github.halo.remoting.manager;

/**
 * 抽象连接管理器，管理多个RPC的连接
 * @author mason.lu 2021/6/25
 */
public abstract class AbstractConnectionManager {

    protected static final int WAITING = 1;
    protected static final int RUNNING = 2;
    protected static final int CLOSE = 3;

    abstract public void connect();

    abstract public void updateConnectServer();

    abstract public void connectAsync();

    //从 注册中心获取远端的配置
    abstract public void getConfigByRegistry();
    





}
