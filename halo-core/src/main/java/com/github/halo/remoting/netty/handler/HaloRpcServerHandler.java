package com.github.halo.remoting.netty.handler;

import com.github.halo.common.constant.PacketStatus;
import com.github.halo.common.packet.*;
import com.github.halo.utils.RpcServiceHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务端处理器
 * @author mason.lu 2021/6/28
 */
public class HaloRpcServerHandler extends SimpleChannelInboundHandler<HaloRpcPacket<HaloRpcRequest>> {

    private final Map<String,Object> rpcServiceMap;

    //worker线程池
    private final ThreadPoolExecutor executor;

    public HaloRpcServerHandler(Map<String,Object> rpcServiceMap,ThreadPoolExecutor executor){
        this.rpcServiceMap = rpcServiceMap;
        this.executor = executor;

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("有连接注册");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("有连接活跃");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<HaloRpcRequest> requestPacket) throws Exception {
        executor.submit(()->{
            //创建ResponsePacket
            HaloRpcPacket<HaloRpcResponse> responsePacket = new HaloRpcPacket<>();
            HaloRpcResponse response = new HaloRpcResponse();
            PacketHeader header = requestPacket.getHeader();
            header.setMsgType((byte)MsgType.RESPONSE.getType());
            try{
                Object result = handle(requestPacket.getBody());
                response.setData(result);
                header.setStatus((byte) PacketStatus.SUCCESS.getCode());
                responsePacket.setBody(response);
                responsePacket.setHeader(header);
            }catch (Throwable throwable){
                header.setStatus((byte) PacketStatus.FAILURE.getCode());
                response.setMessage(throwable.toString());
                System.err.println("process request error ,request id="+header.getRequestId());
            }
            channelHandlerContext.writeAndFlush(responsePacket);
        });
    }

    private Object handle(HaloRpcRequest request) throws Throwable{
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion());
        Object serviceBean = rpcServiceMap.get(serviceKey);
        if (serviceBean == null){
            throw new RuntimeException(String.format("service not exist: %s:%s",request.getClassName(),request.getMethodName()));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterType = request.getParameterType();
        Object[] params = request.getParams();
        FastClass fastClass = FastClass.create(serviceClass);
        int index = fastClass.getIndex(methodName, parameterType);
        return fastClass.invoke(index,serviceBean,params);
    }
}
