package com.github.halo.common;

import java.util.Map;

/**
 * 携带了所有的对外暴露或者对外引用的基本信息 <br>
 *
 * @author mason.lu 2021/6/20
 */
public class URL {
    private final String protocol;
    private final String username;
    private final String password;

    // by default, host to registry
    private final String host;

    // by default, port to registry
    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    private final Map<String, Map<String, String>> methodParameters;

    private transient String address;


    protected URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.address = null;
        this.path = null;
        this.parameters = null;
        this.methodParameters = null;
    }

}
