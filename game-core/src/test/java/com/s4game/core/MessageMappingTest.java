package com.s4game.core;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.MessageLite;
import com.s4game.core.message.MessageConfig;
import com.s4game.core.message.MessageConfig.MessageMapper;

public class MessageMappingTest {

    String filename = "message-mapping.json";

    @Test
    public void loadConfig() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);
        MessageConfig config = objectMapper.readValue(input, MessageConfig.class);
        Assert.assertNotNull(config);

        config.getMappers().forEach(m -> {
            MessageLite clazz = getClass(config.getJavaPackage(), m);
            
            Assert.assertNotNull(clazz);
        });
        
        IOUtils.closeQuietly(input);
    }

    private MessageLite getClass(String basePackage, MessageMapper mapper) {
        try {
            Class<?> clazz = Class.forName(basePackage + "." + mapper.getClazz() + "$" + mapper.getName());
            
            Method method = clazz.getDeclaredMethod("getDefaultInstance", new Class[]{});
            
            return (MessageLite) method.invoke(clazz, new Object[]{});
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
