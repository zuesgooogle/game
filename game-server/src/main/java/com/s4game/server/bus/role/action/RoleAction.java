package com.s4game.server.bus.role.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.s4game.core.action.annotation.ActionMapping;
import com.s4game.core.action.annotation.ActionWorker;
import com.s4game.protocol.Message.Request;

@ActionWorker
@Component
public class RoleAction {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    
    @ActionMapping(message = Request.class)
    public void request(Request request) {
        LOG.info("role action: " + request.toString());
    }
    
}
