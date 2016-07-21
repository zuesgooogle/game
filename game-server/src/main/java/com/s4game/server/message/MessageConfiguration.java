package com.s4game.server.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.s4game.core.action.manager.ActionManager;
import com.s4game.core.action.manager.DefaultActionManager;
import com.s4game.server.message.mapping.ProtobufMessageMapping;

@Configuration
public class MessageConfiguration {

    @Bean(name = "messageMapping", initMethod = "init")
    public ProtobufMessageMapping getMessageMapping() {
        ProtobufMessageMapping mapping = new ProtobufMessageMapping();
        
        return mapping;
    }
    
    @Bean(initMethod = "init")
    public ActionManager getActionManager() {
        ActionManager actionManager = new DefaultActionManager();
        
        return actionManager;
    }
    
}
