package com.github.halo.common.constant;

import lombok.Getter;

/**
 * 包类型
 * @author mason.lu 2021/6/23
 */
public enum PacketStatus {
    SUCCESS(0),
    FAILURE(1);

    @Getter
    private final int code;

    PacketStatus(int code) {
        this.code = code;
    }
}
