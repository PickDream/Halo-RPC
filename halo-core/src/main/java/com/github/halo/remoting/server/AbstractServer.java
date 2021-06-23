package com.github.halo.remoting.server;

import com.github.halo.common.URL;
import com.github.halo.remoting.EndPoint;

import java.net.InetSocketAddress;

/**
 * abstract server
 * @author mason.lu 2021/6/20
 */
public abstract class AbstractServer implements EndPoint {

    private InetSocketAddress localAddress;

    private InetSocketAddress bindAddress;

    public AbstractServer(URL url){

    }

    public URL getURL() {
        return null;
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

//  getBindAddress



}
