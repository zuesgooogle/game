package com.s4game.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.s4game.client.spring.ApplicationInit;
import com.s4game.core.net.ClientListener;
import com.s4game.protocol.Message.Request;

public class ClientMain {

    private static Logger LOG = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) throws Exception {
        ApplicationInit.init();

        LOG.info("client starting success.");

        ClientListener client = ApplicationInit.getApplicationContext().getBean(ClientListener.class);

        while (true) {
            Request request = Request.newBuilder()
                                .setCommand(System.currentTimeMillis() + "")
                                .setData("bbbbb000000000000000000000000000")
                                .build();

            client.sendMessage(request);

            TimeUnit.MILLISECONDS.sleep(1);
        }
    }

}
