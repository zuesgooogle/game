package com.s4game.server.message;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.s4game.core.message.Message;
import com.s4game.core.spring.SpringApplicationContext;
import com.s4game.server.message.handler.MessageHandler;
import com.s4game.server.share.moduleinit.Group;
import com.s4game.server.share.moduleinit.MessageRegister;

@Component
public class MessageDispatcher {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SpringApplicationContext applicationContext;
    
    @Autowired
    private MessageRegister messageRegister;
    
    private Map<Group, MessageHandler> handlers;
    
    @PostConstruct
    public void init() {
        handlers = new EnumMap<>(Group.class);
        
        Map<String, MessageHandler> beans = applicationContext.getBeansOfType(MessageHandler.class);
        for (MessageHandler handler : beans.values()) {
            handlers.put(handler.getGroup(), handler);
        }
    }
    
    public void in(Message message) {
        LOG.debug("source: {}, dest: {}, message: {}", message.getSource(), message.getDestination(), message.getBody().toString());
        
        Group group = messageRegister.getGroup(message.getBody().getClass());
        
        MessageHandler handler = handlers.get(group);
        
        handler.handle(message);
    }

}
