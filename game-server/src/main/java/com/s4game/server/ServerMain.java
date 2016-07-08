package com.s4game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.s4game.server.spring.ApplicationInit;

public class ServerMain {

    private static Logger LOG = LoggerFactory.getLogger(ServerMain.class);
    
    public static void main(String[] args) {
        
        ApplicationInit.init();
        
        LOG.info("game server starting success.");
    }

}
