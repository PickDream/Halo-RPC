package com.github.halo.remoting.netty.handler;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.SocketAddress;

/**
 *
 * @author mason.lu 2021/6/28
 */
@ChannelHandler.Sharable
public class HaloRpcClientHandler extends SimpleChannelInboundHandler<HaloRpcPacket<HaloRpcResponse>> {

    private Channel channel;

    private SocketAddress remoteAddress;

    private SocketAddress localAddress;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<HaloRpcResponse> haloRpcResponseHaloRpcPacket) throws Exception {
        //TODO channel read

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddress = ctx.channel().remoteAddress();
        this.localAddress = ctx.channel().localAddress();
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public SocketAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * 关闭当前的Channel
     * */
    public void closeChannel(){
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
