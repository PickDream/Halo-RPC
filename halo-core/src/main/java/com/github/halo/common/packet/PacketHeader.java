package com.github.halo.common.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mason.lu 2021/6/22
 */
@Data
public class PacketHeader implements Serializable {
    /**
     * 魔数
     *  ((h | a) << 8 | ( l | o))
     * */
    private short magic;
    //版本
    private byte version;
    //序列化方式
    private byte serialType;
    //包类型
    private byte msgType;
    //状态
    private byte status;
    //请求Id
    private long requestId;
    //长度
    private int length;
}
