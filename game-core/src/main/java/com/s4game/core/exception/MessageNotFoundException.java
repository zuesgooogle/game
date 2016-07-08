package com.s4game.core.exception;

public class MessageNotFoundException extends RuntimeException {


    /**
     * 
     */
    private static final long serialVersionUID = -7506422405520831350L;

    public MessageNotFoundException() {
        super();
    }

    public MessageNotFoundException(String s) {
        super(s);
    }

    public MessageNotFoundException(String format, Object... args) {
        super(String.format(format, args));
    }    
    
    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }
}
