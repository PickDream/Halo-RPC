package com.github.halo.common.packet;

import lombok.Getter;

/**
 * 消息类型
 * @author mason.lu 2021/6/28
 */
public enum MsgType {
    REQUEST(1),
    RESPONSE(2),
    HEARTBEAT(3);

    @Getter
    private final int type;

    MsgType(int type){
        this.type = type;
    }

    public static MsgType findByType(int type) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }
}
