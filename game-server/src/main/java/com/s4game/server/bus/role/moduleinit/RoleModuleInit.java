package com.s4game.server.bus.role.moduleinit;

import org.springframework.stereotype.Component;

import com.s4game.protocol.Message.Request;
import com.s4game.server.bus.share.moduleinit.BusModuleInit;
import com.s4game.server.share.moduleinit.Group;
import com.s4game.server.share.moduleinit.MessageRegister.ModuleMessage;

@Component
public class RoleModuleInit extends BusModuleInit {

    @Override
    protected ModuleMessage getModuleMessage() {
        Class<?>[] messages = new Class<?>[] {Request.class};
        
        return new ModuleMessage(Group.BUS, RoleModule.MODULE_NAME, messages);
    }

}
