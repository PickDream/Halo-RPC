package com.github.halo.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author mason.lu 2021/6/28
 */
public final class RpcRequestHolder {

    public final static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static final Map<Long,HaloRpcFuture> REQUEST_MAP = new ConcurrentHashMap<>();
}
