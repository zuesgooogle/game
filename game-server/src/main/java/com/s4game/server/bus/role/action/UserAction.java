package com.s4game.server.bus.role.action;

import org.springframework.stereotype.Component;

import com.s4game.core.action.annotation.ActionMapping;
import com.s4game.core.action.annotation.ActionWorker;
import com.s4game.protocol.Message.Request;

@ActionWorker
@Component
public class UserAction {

    @ActionMapping(message = Request.class)
    public void request(Request request) {
        System.out.println(request.toString());
    }
    
}
