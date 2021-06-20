package com.github.halo.utils;


import java.util.Objects;

/**
 * 获取JVM虚拟机运行时的变量
 * @author mason.lu 2021/6/20
 */
public class EnvironmentUtil {

    public static String getString(String key){
        String result = null;
        result = System.getProperty(key);
        if (Objects.isNull(result)){
            result = System.getenv(key);
        }
        return result;
    }

    public static String getString(String key,String defaultValue){
        String result = getString(key);
        if (Objects.isNull(result)){
            return defaultValue;
        }
        return result;
    }

    public static boolean getBoolean(String key,boolean defaultValue){
        String result = getString(key);
        if (Objects.isNull(result)){
            return defaultValue;
        }
        return Boolean.getBoolean(result);
    }


}
