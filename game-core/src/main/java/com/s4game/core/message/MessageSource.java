package com.s4game.core.message;

/**
 * 消息来源
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月11日 下午5:47:19
 */
public enum MessageSource {

    /**
     * 客户端请求
     */
    CLIENT(1),

    /**
     * 业务模块
     */
    BUS(2),

    /**
     * 场景
     */
    STAGE(3),

    /**
     * 场景控制
     */
    STAGE_CONTROL(4),

    ;

    private final int value;

    private MessageSource(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MessageSource findType(int type) {
        switch (type) {
        case 1:
            return CLIENT;
        case 2:
            return BUS;
        case 3:
            return STAGE;
        case 4:
            return STAGE_CONTROL;
        default:
            throw new IllegalArgumentException("invalid source type: " + type);
        }
    }
}
