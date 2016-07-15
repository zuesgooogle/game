package com.s4game.server.message.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s4game.core.action.manager.ActionManager;
import com.s4game.core.message.Message;
import com.s4game.core.thread.executor.BusinessExecutor;
import com.s4game.core.thread.executor.Route;
import com.s4game.server.share.executor.RunnableTask;
import com.s4game.server.share.moduleinit.Group;

@Service
public class BusMessageHandler implements MessageHandler {

    @Autowired
    private BusinessExecutor busExecutor;

    @Autowired
    private ActionManager actionManager;
    
    @Override
    public void handle(Message message) {
        Route route = new Route("bus", "roleId");
        
        busExecutor.execute(new RunnableTask(actionManager, message), route);
    }

    @Override
    public Group getGroup() {
        return Group.BUS;
    }
}
