package com.s4game.core.action.resolver;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultActionResolver implements ActionResolver {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private Method m;
    
    private Object target;

    public DefaultActionResolver(Method m, Object target) {
        this.m = m;
        this.target = target;
    }

    @Override
    public void execute(Object message) {
        try {

            m.invoke(target, new Object[] { message });

        } catch (Exception e) {
            LOG.error("", e);
            e.printStackTrace();
        }
    }
}
