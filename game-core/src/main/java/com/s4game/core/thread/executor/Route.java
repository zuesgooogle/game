package com.s4game.core.thread.executor;

public class Route {

    private String group;

    private String data;

    public Route() {

    }

    public Route(String group, String data) {
        this.group = group;
        this.data = data;
    }
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
