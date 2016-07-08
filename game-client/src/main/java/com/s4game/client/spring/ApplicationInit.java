package com.s4game.client.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @Author zeusgooogle@gmail.com
 * @sine 2016年7月7日 上午10:31:03
 * 
 */

public class ApplicationInit {

    private static ApplicationContext ctx;

    public static void init() {
        initSpringContext();
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    private static void initSpringContext() {
        ctx = new ClassPathXmlApplicationContext("config/spring/applicationContext.xml");
    }

}
