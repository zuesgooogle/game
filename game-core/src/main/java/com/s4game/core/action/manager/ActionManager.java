package com.s4game.core.action.manager;

import com.s4game.core.action.resolver.ActionResolver;

public interface ActionManager {

    ActionResolver getResolver(Class<?> clazz);
 
    void execute(Object message);
}
