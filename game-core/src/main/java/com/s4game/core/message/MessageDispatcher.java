package com.s4game.core.message;

/**
 * 消息分发器
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月11日 下午6:04:20
 */
public interface MessageDispatcher {

    void in(Message message);
    
}
