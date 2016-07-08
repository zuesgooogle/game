package com.s4game.core.action.manager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import com.s4game.core.action.annotation.ActionMapping;
import com.s4game.core.action.annotation.ActionWorker;
import com.s4game.core.action.resolver.ActionResolver;
import com.s4game.core.action.resolver.DefaultActionResolver;
import com.s4game.core.spring.SpringApplicationContext;

public class DefaultActionManager implements ActionManager {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private Map<Class<?>, ActionResolver> resolvers = new HashMap<>();
    
    @Autowired
    private SpringApplicationContext applicationContext;
    
    @PostConstruct
    public void init() {
        Map<String, Object> workers = applicationContext.getBeansWithAnnotation(ActionWorker.class);
        for (Object clazz : workers.values()) {
            analyzeClass( clazz.getClass() );
        }
    }
    
    private void analyzeClass(Class<?> clazz) {
        ActionWorker actionWorker = AnnotationUtils.findAnnotation(clazz, ActionWorker.class);
        if (null != actionWorker) {
            try {
                Method[] methods = clazz.getDeclaredMethods();

                for (Method m : methods) {
                    ActionMapping actionMapping = AnnotationUtils.findAnnotation(m, ActionMapping.class);
                    if (null != actionMapping) {
                        resolvers.put(actionMapping.message(), new DefaultActionResolver(m, applicationContext.getBean(clazz)));
                        
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("resolver action mapping. message: {}, method: {}", actionMapping.message(), m.getName());
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("error in analyzeClass", e);
            }
        }
    }
    
    @Override
    public ActionResolver getResolver(Class<?> clazz) {
        return resolvers.get(clazz);
    }

    @Override
    public void execute(Object message) {
        ActionResolver resolver = getResolver(message.getClass());
        resolver.execute(message);
    }
}
