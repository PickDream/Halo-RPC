package com.github.halo.remoting.netty;

import com.github.halo.codec.Codec;
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

   public InetSocketAddress getBindAddress(){
       //todo
       return null;
   }

   public byte getCodec(){
       this.getURL();
       //todo
       return 0;
   }



}
