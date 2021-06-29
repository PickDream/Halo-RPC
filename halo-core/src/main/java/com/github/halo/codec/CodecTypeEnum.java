package com.github.halo.codec;

/**
 * 序列化方式枚举
 * @author mason.lu 2021/6/22
 */
public enum CodecTypeEnum {
    HESSIAN((byte) 0x10),
    PROTOSTUFF((byte) 0x20),
    JSON((byte) 0x30);

    private final byte type;
    CodecTypeEnum(byte type){
        this.type = type;
    }

    public byte getType(){
        return type;
    }

    public static CodecTypeEnum findByType(byte serializationType) {
        for (CodecTypeEnum typeEnum : CodecTypeEnum.values()) {
            if (typeEnum.getType() == serializationType) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }
}
