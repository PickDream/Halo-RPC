package com.github.halo.remoting.netty.handler;

import com.github.halo.common.HaloRpcFuture;
import com.github.halo.common.RpcRequestHolder;
import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcRequest;
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
        System.out.println("log:收到响应");
        long requestId = haloRpcResponseHaloRpcPacket.getHeader().getRequestId();
        HaloRpcFuture remove = RpcRequestHolder.REQUEST_MAP.remove(requestId);
        remove.done(haloRpcResponseHaloRpcPacket.getBody());


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

    public void sendPacket(HaloRpcPacket<HaloRpcRequest> packet) {
        channel.writeAndFlush(packet);
    }
}
