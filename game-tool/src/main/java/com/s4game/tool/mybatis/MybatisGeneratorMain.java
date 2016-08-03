package com.s4game.tool.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据 数据库表反向生成 JavaBean，Mybatis XML，Dao mapper
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月28日 下午6:22:27
 */
public class MybatisGeneratorMain {

    public static Logger LOG = LoggerFactory.getLogger(MybatisGeneratorMain.class);
    
    public static final String CONFIG_XML = "mybatis/generator.xml";
    
    public static void main(String[] args) throws Exception {
        String path = MybatisGeneratorMain.class.getClassLoader().getResource("").getPath() + CONFIG_XML;
        
        List<String> warnings = new ArrayList<String>();
        
        //已存在文件进行覆盖
        boolean overwrite = true;
        File configFile = new File(path);
        
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        
        myBatisGenerator.generate(null);
        
        for (String warn : warnings) {
            LOG.warn(warn);
        }
    }
}
