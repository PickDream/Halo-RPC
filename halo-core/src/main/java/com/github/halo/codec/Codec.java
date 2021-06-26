package com.github.halo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.IOException;

/**
 * 编解码接口
 * @author mason.lu 2021/6/21
 */
public interface Codec {
    /**
     * 编码
     * */
    <T> byte[] encode(T obj) throws IOException;

    /**
     * 解码
     * */
    <T> T decode(byte[] data,Class<T> clz) throws IOException;

}
