package com.s4game.core.message;

/**
 * 消息目的地
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月11日 下午5:48:08
 */
public enum MessageDestination {

    CLIENT(0),

    BUS(1),

    STAGE_CONTROL(2),

    STAGE(3),

    INOUT(4),

    BUS_INIT(5),

    PUBLIC(6),

    INNER_SYSTEM(7),

    ;

    private final int value;

    private MessageDestination(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MessageDestination findType(int type) {
        switch (type) {
        case 0:
            return CLIENT;
        case 1:
            return BUS;
        case 2:
            return STAGE_CONTROL;
        case 3:
            return STAGE;
        case 4:
            return INOUT;
        case 5:
            return BUS_INIT;
        case 6:
            return PUBLIC;
        case 7:
            return INNER_SYSTEM;
        default:
            throw new IllegalArgumentException("invalid frome type.");
        }

    }

}
