package com.github.halo.codec;

import com.github.halo.common.constant.ProtocolConstant;
import com.github.halo.common.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * 编解码适配器
 * @author mason.lu 2021/6/21
 */
public class HaloCodecAdapter {

    private final ChannelHandler encodeChannelHandler;
    private final ChannelHandler decodeChannelHandler;

    private final Codec codec;


    public ChannelHandler getEncodeHandler(){
        return this.encodeChannelHandler;
    }

    public ChannelHandler getDecodeHandler(){
        return this.decodeChannelHandler;
    }

    public HaloCodecAdapter(byte serializationCode){
        this.codec = CodecFactory.getRpcCodec(serializationCode);
        encodeChannelHandler = new InternalEncoder();
        decodeChannelHandler = new InternalDecoder();

    }

    private class InternalEncoder extends MessageToByteEncoder<HaloRpcPacket<Object>>{
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, HaloRpcPacket<Object> packet, ByteBuf byteBuf) throws Exception {
            PacketHeader header = packet.getHeader();
            byteBuf.writeShort(header.getMagic());
            byteBuf.writeByte(header.getVersion());
            byteBuf.writeByte(header.getSerialType());
            byteBuf.writeByte(header.getMsgType());
            byteBuf.writeByte(header.getStatus());
            byteBuf.writeLong(header.getRequestId());
            byte[] encode = codec.encode(packet.getBody());
            byteBuf.writeInt(encode.length);
            byteBuf.writeBytes(encode);
        }
    }

    private class InternalDecoder extends ByteToMessageDecoder{

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            if (byteBuf.readableBytes() < ProtocolConstant.HEADER_LENGTH){
                return;
            }
            byteBuf.markReaderIndex();
            short magic = byteBuf.readShort();
            if (magic!=ProtocolConstant.MAGIC){
                throw new IllegalStateException("magic number is illegal!" + magic);
            }
            byte version = byteBuf.readByte();
            byte serializeType = byteBuf.readByte();
            byte msgType = byteBuf.readByte();
            byte status = byteBuf.readByte();
            long requestId = byteBuf.readLong();
            int dataLength = byteBuf.readInt();
            if (byteBuf.readableBytes() < dataLength){
                byteBuf.resetReaderIndex();
                return;
            }
            byte[] data = new byte[dataLength];
            byteBuf.readBytes(data);

            MsgType msgTypeEnum = MsgType.findByType(msgType);

            if (msgTypeEnum == null){
                throw new IllegalStateException("msg type not support!");
            }

            PacketHeader header = new PacketHeader();
            header.setMagic(magic);
            header.setVersion(version);
            header.setSerialType(serializeType);
            header.setStatus(status);
            header.setRequestId(requestId);
            header.setMsgType(msgType);
            header.setLength(dataLength);

            Codec rpcCodec = CodecFactory.getRpcCodec(serializeType);
            switch (msgTypeEnum){
                case REQUEST:
                    HaloRpcRequest request = rpcCodec.decode(data,HaloRpcRequest.class);
                    if (request != null) {
                        HaloRpcPacket<HaloRpcRequest> packet = new HaloRpcPacket<>();
                        packet.setHeader(header);
                        packet.setBody(request);
                        list.add(packet);
                    }
                    break;
                case RESPONSE:
                    HaloRpcResponse response = rpcCodec.decode(data,HaloRpcResponse.class);
                    if (response!=null){
                        HaloRpcPacket<HaloRpcResponse> packet = new HaloRpcPacket<>();
                        packet.setHeader(header);
                        packet.setBody(response);
                        list.add(packet);
                    }
                    break;
                case HEARTBEAT:
                    //TODO 增加对心跳的支持

                    break;
            }
        }
    }

}
