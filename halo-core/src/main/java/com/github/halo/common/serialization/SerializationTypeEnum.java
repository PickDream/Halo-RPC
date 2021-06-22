package com.github.halo.common.serialization;

/**
 * 序列化方式枚举
 * @author mason.lu 2021/6/22
 */
public enum SerializationTypeEnum {
    HESSIAN(0x10),
    PROTOBUF(0x20),
    JSON(0x30);

    private final int type;
    SerializationTypeEnum(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public static SerializationTypeEnum findByType(byte serializationType) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.getType() == serializationType) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }
}
