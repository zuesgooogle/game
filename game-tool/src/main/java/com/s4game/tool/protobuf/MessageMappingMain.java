package com.s4game.tool.protobuf;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s4game.core.exception.MessageRepeatException;
import com.s4game.core.message.MessageConfig;
import com.s4game.core.message.MessageConfig.MessageMapper;

/**
 * 生成 messageId 与 Message 对应关系的配置文件(json)
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月8日 上午10:56:36
 */
public class MessageMappingMain {

    public static Logger LOG = LoggerFactory.getLogger(MessageMappingMain.class);
    
    public static final String MESSAGE_PROTO_FILE = "proto";
    
    public static final String MESSAGE_MAPPING_FILE = "json/message-mapping.json";
    
    private String javaPackage;
    
    private String javaOuterClassname;
    
    private Map<Integer, MessageMapper> map = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
        MessageMappingMain main = new MessageMappingMain();
        
        main.parse();
    }
    
    public void parse() throws Exception {
        MessageConfig config = new MessageConfig();
        List<MessageMapper> mappers = new ArrayList<>();
        
        // parse message-xxxxx.proto
        for (File file : listFiles()) {
            MessageMapper mapper = new MessageMapper();
            
            for (String line : loadProto(file)) {
                LOG.info("prepare parse line: {}", line);
                
                parseOption(line);
                parseMessageMapping(mapper, line);
                
                if (mapper.getId() > 0 && mapper.getName() != null) {
                    if (map.containsKey(mapper.getId())) {
                        throw new MessageRepeatException("id: %s repeat, at line: %s", mapper.getId(), line);
                    }

                    mapper.setClazz(javaOuterClassname);
                    mappers.add(mapper);
                    map.put(mapper.getId(), mapper);

                    //new mapper
                    mapper = new MessageMapper();
                }
            }
        }
        
        config.setJavaPackage(javaPackage);
        config.setMappers(mappers);
        
        //write json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(config);
        writeJson(json);
        
        LOG.info("parse done. message mapping json: {}", json);
    }
    
    /**
     * Parse proto option 
     * 
     */
    public void parseOption(String line) {
        if (!line.startsWith("option")) {
            return;
        }
        
        if (line.indexOf("java_package") > 0) {
            int start = line.indexOf("\"");
            int end = line.lastIndexOf("\"");
            
            javaPackage = line.substring(start + 1, end);
        }
        
        if (line.indexOf("java_outer_classname") > 0) {
            int start = line.indexOf("\"");
            int end = line.lastIndexOf("\"");
            
            javaOuterClassname = line.substring(start + 1, end);
        }
    }
    
    /**
     *  解析ID，并且找到下一行的 message 与之对应
     */
    public void parseMessageMapping(MessageMapper mapper, String line) {
        if (line.indexOf("@Id") > 0) {
            int start = line.indexOf("(");
            int end = line.indexOf(")");
            
            int id = Integer.parseInt(line.substring(start + 1, end));
            mapper.setId(id);
        }
        
        if (line.startsWith("message")) {
            String messageName = StringUtils.split(line, " ")[1];
            mapper.setName(messageName);
        }
    }
    
    public Collection<File> listFiles() throws Exception {
        URL url = MessageMappingMain.class.getClassLoader().getResource(MESSAGE_PROTO_FILE);
        
        return FileUtils.listFiles(new File(url.getPath()), new PrefixFileFilter("message"), TrueFileFilter.INSTANCE);
    }
    
    public List<String> loadProto(File file) throws Exception {
        return FileUtils.readLines(file, "UTF-8");
    }
    
    public void writeJson(String json) throws Exception {
        URL url = MessageMappingMain.class.getClassLoader().getResource("");
        
        FileUtils.writeStringToFile(new File(url.getPath() + MESSAGE_MAPPING_FILE), json, "UTF-8");
    }
}
