package com.github.halo.common.packet;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mason.lu 2021/6/22
 */
@Data
public class HaloRpcPacket<T> implements Serializable {
    PacketHeader header;
    T body;
}
