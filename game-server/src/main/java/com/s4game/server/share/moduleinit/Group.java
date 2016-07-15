package com.s4game.server.share.moduleinit;

/**
 *
 * Module 分组
 *
 * @Author zeusgooogle@gmail.com
 * @sine   2016年7月10日 下午5:14:07
 *
 */
public enum Group {

    IO("io", 0),

    LOGIN("login", 1),
    
    BUS("bus", 2),

    STAGE_CONTROL("stage_control", 3),

    STAGE("stage", 4),

    PUBLIC("public", 5),
    
    SYSTEM("system", 6),
    
    ;

    public final String name;
    
    public final int value;

    private Group(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }
    
    public int getValue() {
        return this.value;
    }

    public static Group find(int group) {
        switch (group) {
        case 0: return IO;
        case 1: return LOGIN;
        case 2: return BUS;
        case 3: return STAGE_CONTROL;
        case 4: return STAGE;
        case 5: return PUBLIC;
        case 6: return SYSTEM;
        default:
            throw new IllegalArgumentException("invalid group.");
        }
    }
}
