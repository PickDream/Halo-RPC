package com.github.halo.proxy;

import com.github.halo.codec.Codec;
import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.common.RpcRequestHolder;
import com.github.halo.common.constant.PacketStatus;
import com.github.halo.common.constant.ProtocolConstant;
import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.HaloRpcRequest;
import com.github.halo.common.packet.MsgType;
import com.github.halo.common.packet.PacketHeader;
import com.github.halo.remoting.manager.RpcConnectionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 引用类型代理工厂
 * @author mason.lu 2021/6/27
 */
public final class ReferenceProxyFactory {

    public class RpcInvockerProxy implements InvocationHandler{
        //
        private final String serviceVersion;
        //超时时间
        private final long timeout;

        private final RpcConnectionManager rpcConnectionManager;

        private final CodecTypeEnum codecTypeEnum;

        public RpcInvockerProxy(String serviceVersion,long timeout,
                                CodecTypeEnum codecTypeEnum,
                                RpcConnectionManager connectionManager){
            this.codecTypeEnum = codecTypeEnum;
            this.serviceVersion = serviceVersion;
            this.timeout = timeout;
            this.rpcConnectionManager = connectionManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            HaloRpcPacket<HaloRpcRequest> packet = new HaloRpcPacket<>();
            PacketHeader packetHeader = new PacketHeader();
            //设置请求头
            packetHeader.setRequestId(RpcRequestHolder.REQUEST_ID_GEN.incrementAndGet());
            packetHeader.setMagic(ProtocolConstant.MAGIC);
            packetHeader.setVersion(ProtocolConstant.VERSION);
            packetHeader.setSerialType(codecTypeEnum.getType());
            packetHeader.setMsgType((byte) MsgType.REQUEST.getType());
            packetHeader.setStatus((byte)0x1);
            packet.setHeader(packetHeader);
            //设置请求体
            HaloRpcRequest request = new HaloRpcRequest();
            request.setVersion(this.serviceVersion);
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParameterType(method.getParameterTypes());
            request.setParams(args);
            packet.setBody(request);

            //
            return null;
        }



    }
}
