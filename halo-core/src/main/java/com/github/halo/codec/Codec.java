package com.github.halo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * 编解码接口
 * @author mason.lu 2021/6/21
 */
public interface Codec {
    void encode(Channel channel, ByteBuf buf,Object message);
    void decode(Channel channel);
}
