package com.s4game.core.message;

import java.util.List;

public class MessageConfig {

    private String javaPackage;

    private List<MessageMapper> mappers;

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public List<MessageMapper> getMappers() {
        return mappers;
    }

    public void setMappers(List<MessageMapper> mappers) {
        this.mappers = mappers;
    }

    public static class MessageMapper {

        private String clazz;

        private int id;

        private String name;

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
