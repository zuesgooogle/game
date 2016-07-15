package com.s4game.server.share.moduleinit;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MessageRegister {

    private Map<String, Group> groupMap = new HashMap<>();
    
    private Map<Class<?>, Group> messageGroupMap = new HashMap<>();
    
    private Map<Class<?>, String> messageModuleMap = new HashMap<>();
    
    @PostConstruct
    public void init() {
        for (Group g : Group.values()) {
            groupMap.put(g.name, g);
        }
    }
    
    public void register(Group group, String module, Class<?>[] messages) {
        Assert.notNull(messages);
        
        for (Class<?> clazz : messages) {
            messageGroupMap.put(clazz, group);
            messageModuleMap.put(clazz, module);
        }
    }
    
    public String getModule(Class<?> message) {
        return messageModuleMap.get(message);
    }
    
    public Group getGroup(Class<?> message) {
        return messageGroupMap.get(message);
    }
    
    public static class ModuleMessage {

        private Group group;

        private String module;

        private Class<?>[] messages;

        public ModuleMessage(Group group, String module, Class<?>[] messages) {
            this.group = group;
            this.module = module;
            this.messages = messages;
        }

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public Class<?>[] getMessages() {
            return messages;
        }

        public void setMessages(Class<?>[] messages) {
            this.messages = messages;
        }
    }
}
