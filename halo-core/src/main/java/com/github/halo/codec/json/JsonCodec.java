package com.github.halo.codec.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.halo.codec.Codec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author mason.lu 2021/6/26
 */
public class JsonCodec implements Codec {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = configMapper();
    }

    @Override
    public <T> byte[] encode(T obj) throws IOException {
        return obj instanceof String ?
                ((String) obj).getBytes() :
                OBJECT_MAPPER.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T decode(byte[] data, Class<T> clz) throws IOException {
         return OBJECT_MAPPER.readValue(Arrays.toString(data), clz);
    }

    private static ObjectMapper configMapper(){
        ObjectMapper customMapper = new ObjectMapper();

        customMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }
}
