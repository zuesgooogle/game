package com.s4game.core.message.mapping;

/**
 * MessageId 与 Message Prototype 的对应关系
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月6日 下午3:40:25
 */
public interface MessageMapping<T> {

    /**
     * 初始化消息映射
     */
    void init();
    
    /**
     *  根据 messageId 获取 Message 类型
     */
    T getPrototype(int messageId);
    
    /**
     * 获取 messageId 
     */
    int getMessageId(Class<?> clazz);
}
