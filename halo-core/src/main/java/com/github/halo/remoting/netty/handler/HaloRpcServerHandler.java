package com.github.halo.remoting.netty.handler;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * 服务端处理器
 * @author mason.lu 2021/6/28
 */
public class HaloRpcServerHandler extends SimpleChannelInboundHandler<HaloRpcPacket<HaloRpcRequest>> {

    private final Map<String,Object> rpcServiceMap;

    public HaloRpcServerHandler(Map<String,Object> rpcServiceMap){
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<HaloRpcRequest> haloRpcRequestHaloRpcPacket) throws Exception {
        //todo 完善服务端调用ChannelRead，来执行处理器并且封装结果
    }
}
