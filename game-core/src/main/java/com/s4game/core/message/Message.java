package com.s4game.core.message;

/**
 * Net message
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月11日 下午5:35:51
 */
public class Message {

    private MessageSource source;

    private MessageDestination destination;

    private Object body;

    public Message() {

    }

    public MessageSource getSource() {
        return source;
    }

    public void setSource(MessageSource source) {
        this.source = source;
    }

    public MessageDestination getDestination() {
        return destination;
    }

    public void setDestination(MessageDestination destination) {
        this.destination = destination;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
