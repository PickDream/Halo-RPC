package com.github.halo.codec;

import com.github.halo.common.packet.HaloRpcPacket;
import com.github.halo.common.packet.PacketHeader;
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

    private ChannelHandler encodeChannelHandler;
    private ChannelHandler decodeChannelHandler;

    private Codec codec;


    public ChannelHandler getEncodeHandler(){
        return this.encodeChannelHandler;
    }

    public ChannelHandler getDecodeHandler(){
        return this.decodeChannelHandler;
    }

    public HaloCodecAdapter(String codecType){
        this.codec = CodecFactory.getRpcCodec(codecType);
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

        }
    }

    private class InternalDecoder extends ByteToMessageDecoder{

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        }
    }

}
