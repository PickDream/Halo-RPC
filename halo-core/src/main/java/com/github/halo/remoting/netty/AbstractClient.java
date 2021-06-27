package com.github.halo.remoting.netty;

import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @author mason.lu 2021/6/24
 */
public abstract class AbstractClient {

    private InetSocketAddress localAddress;

    private InetSocketAddress serverAddress;

    private URL url;

    protected abstract void start() throws Throwable;

    protected abstract void close() throws Throwable;

    public AbstractClient(URL url){

    }

    protected byte getCodec(){
        //TODO
        return 0;
    }

}
