package com.s4game.server.share.executor;

import com.s4game.core.action.manager.ActionManager;
import com.s4game.core.message.Message;

public class RunnableTask implements Runnable {

    private ActionManager actionManager;
    
    private Message message;
    
    public RunnableTask(ActionManager actionManager, Message message) {
        this.actionManager = actionManager;
        this.message = message;
    }
    
    @Override
    public void run() {
        actionManager.execute(message.getBody());
    }

}
