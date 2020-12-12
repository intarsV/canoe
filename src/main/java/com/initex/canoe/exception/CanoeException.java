package com.initex.canoe.exception;

public class CanoeException extends RuntimeException {

    public CanoeException(String message, Exception e) {
        super(message, e);
    }

    public CanoeException(String message) {
        super(message);
    }
}
