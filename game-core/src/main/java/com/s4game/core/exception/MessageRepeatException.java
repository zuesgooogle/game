package com.s4game.core.exception;

public class MessageRepeatException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -7232964547451547516L;

    public MessageRepeatException() {
        super();
    }

    public MessageRepeatException(String s) {
        super(s);
    }

    public MessageRepeatException(String format, Object... args) {
        super(String.format(format, args));
    }    
    
    public MessageRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRepeatException(Throwable cause) {
        super(cause);
    }
}
