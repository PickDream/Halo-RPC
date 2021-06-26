package com.github.halo.exception;

import java.io.Serializable;

/**
 * @author mason.lu 2021/6/25
 */
public class SerializationException extends RuntimeException implements Serializable {
    public SerializationException() {
        super();
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
