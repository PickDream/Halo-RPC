package com.github.halo.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author mason.lu 2021/6/28
 */
public class RpcReferenceBean implements FactoryBean<Object> {

    public static final String INIT_METHOD_NAME = "init";

    private Class<?> interfaceClass;

    private String serviceVersion;

    private long timeout;

    private Object result;

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    public void init(){

    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
