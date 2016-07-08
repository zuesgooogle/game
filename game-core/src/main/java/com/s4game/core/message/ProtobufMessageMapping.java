package com.s4game.core.message;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.MessageLite;
import com.s4game.core.message.MessageConfig.MessageMapper;

public class ProtobufMessageMapping implements MessageMapping<MessageLite> {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * Message 与 messageId 的映射文件名称
     */
    private String mappingConfig = "message-mapping.json";

    private Map<Integer, MessageLite> idMap;

    private Map<Class<?>, Integer> classMap;

    @Override
    public void init() {
        idMap = new HashMap<>();
        classMap = new HashMap<>();

        InputStream input = null;
        try {
            input = this.getClass().getClassLoader().getResourceAsStream(mappingConfig);

            ObjectMapper objectMapper = new ObjectMapper();
            MessageConfig config = objectMapper.readValue(input, MessageConfig.class);

            for (MessageMapper m : config.getMappers()) {
                MessageLite lite = getClass(config.getJavaPackage(), m);
                
                idMap.put(m.getId(), lite);
                classMap.put(lite.getClass(), m.getId());
            }

        } catch (Exception e) {
            LOG.error("Load message mapping config: {} error.", mappingConfig, e);
        } finally {
            IOUtils.closeQuietly(input);
        }
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

    public String getMappingConfig() {
        return mappingConfig;
    }

    public void setMappingConfig(String mappingConfig) {
        this.mappingConfig = mappingConfig;
    }

    @Override
    public MessageLite getPrototype(int messageId) {
        return idMap.get(messageId);
    }

    @Override
    public int getMessageId(Class<?> clazz) {
        return classMap.get(clazz);
    }

}
