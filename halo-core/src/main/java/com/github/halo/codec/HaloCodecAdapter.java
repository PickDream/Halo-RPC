package com.github.halo.codec;

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

    private class InternalEncoder extends MessageToByteEncoder{
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
            Channel channel = channelHandlerContext.channel();
//            test
            // BUFFER

        }
    }

    private class InternalDecoder extends ByteToMessageDecoder{

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        }
    }

}
