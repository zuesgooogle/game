package com.s4game.server.message.handler;

import com.s4game.core.message.Message;
import com.s4game.server.share.moduleinit.Group;

/**
 * 消息分发器
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月11日 下午6:04:20
 */
public interface MessageHandler {

    Group getGroup();
    
    void handle(Message message);
    
}
