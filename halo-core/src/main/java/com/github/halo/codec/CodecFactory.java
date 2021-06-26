package com.github.halo.codec;

import com.github.halo.codec.hession.HessionCodec;
import com.github.halo.codec.json.JsonCodec;
import com.github.halo.codec.protostuff.ProtostuffCodec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mason.lu 2021/6/23
 */
public final class CodecFactory {

    public static Map<CodecTypeEnum,Codec> cachedCodecHandler =
            new ConcurrentHashMap<>();

    public static Codec getRpcCodec(byte serializationType) {
        CodecTypeEnum codecType = CodecTypeEnum.findByType(serializationType);
        switch (codecType){
            case JSON:
                return cachedCodecHandler.compute(codecType,(key,handler)-> handler == null ? new JsonCodec() : handler);
            case PROTOSTUFF:
                return cachedCodecHandler.compute(codecType,(key,handler)-> handler == null ? new ProtostuffCodec() : handler);
            case HESSIAN:
                return cachedCodecHandler.compute(codecType,(key,handler)-> handler == null ? new HessionCodec() : handler);
            default:
                throw new IllegalArgumentException("no suitable codec handler found, " + serializationType);
        }
    }
}
