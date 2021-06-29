package com.github.halo.remoting.netty.handler;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author mason.lu 2021/6/28
 */
public class HaloRpcServerHandler extends SimpleChannelInboundHandler<HaloRpcPacket<HaloRpcRequest>> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<HaloRpcRequest> haloRpcRequestHaloRpcPacket) throws Exception {

    }
}
