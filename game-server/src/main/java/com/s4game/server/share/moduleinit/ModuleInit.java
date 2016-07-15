package com.s4game.server.share.moduleinit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.s4game.server.share.moduleinit.MessageRegister.ModuleMessage;

public abstract class ModuleInit {

    @Autowired
    private MessageRegister messageRegister;
    
    @PostConstruct
    public void init() {
        
        //register message
        ModuleMessage mm = getModuleMessage();
        if (mm != null) {
            messageRegister.register(mm.getGroup(), mm.getModule(), mm.getMessages());
        }
    }
    
    public int getOrder() {
        return 200;
    }
 
    /**
     * <pre>
     * 模块与消息的对应关系
     * </pre> 
     */
    protected abstract ModuleMessage getModuleMessage();
}
