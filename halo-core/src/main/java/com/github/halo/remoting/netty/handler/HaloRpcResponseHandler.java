package com.github.halo.remoting.netty.handler;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author mason.lu 2021/6/28
 */
@ChannelHandler.Sharable
public class HaloRpcResponseHandler extends SimpleChannelInboundHandler<HaloRpcPacket<HaloRpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<HaloRpcResponse> haloRpcResponseHaloRpcPacket) throws Exception {

    }
}
