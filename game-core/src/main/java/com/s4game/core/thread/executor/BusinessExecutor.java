package com.s4game.core.thread.executor;

public interface BusinessExecutor {

    void execute(Runnable task, Route route);
 
    void addExecutorGroup(String groupName, int size);
}
