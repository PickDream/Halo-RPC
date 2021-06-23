package com.github.halo.codec;

/**
 * 序列化方式枚举
 * @author mason.lu 2021/6/22
 */
public enum CodecTypeEnum {
    HESSIAN(0x10),
    PROTOBUF(0x20),
    JSON(0x30);

    private final int type;
    CodecTypeEnum(int type){
        this.type = type;
    }

    public int getType(){
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
