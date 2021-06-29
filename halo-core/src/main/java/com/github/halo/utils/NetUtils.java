package com.github.halo.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络相关的工具
 * @author mason.lu 2021/6/20
 */
public class NetUtils {
    // (0,65535]
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;

    public static String getLocalAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


}
