package com.s4game.server.share.executor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.s4game.core.thread.executor.BusinessExecutor;
import com.s4game.server.bus.share.rule.BusRuleChecker;
import com.s4game.server.public_.share.rule.PublicRuleChecker;
import com.s4game.server.share.moduleinit.Group;

@Configuration
public class ExecutorConfiguration {

    @Bean(name = "busExecutor")
    public BusinessExecutor getBusExecutor() {
        BalanceBusinessExecutor executor = new BalanceBusinessExecutor();
        
        Map<String, Integer> config = new HashMap<>();
        config.put("bus", 6);
        config.put("system", 1);

        BusRuleChecker ruleChecker = new BusRuleChecker();
        
        executor.setName(Group.BUS.name);
        executor.setConfig(config);
        executor.setRuleChecker(ruleChecker);
        
        executor.init();
        return executor;
    }
    
    @Bean(name = "publicExecutor")
    public BusinessExecutor getPublicExecutor() {
        BalanceBusinessExecutor executor = new BalanceBusinessExecutor();
        
        Map<String, Integer> config = new HashMap<>();
        config.put("login", 6);
        config.put("public", 3);
        config.put("system", 1);

        PublicRuleChecker ruleChecker = new PublicRuleChecker();
        
        executor.setName(Group.PUBLIC.name);
        executor.setConfig(config);
        executor.setRuleChecker(ruleChecker);
        
        executor.init();
        return executor;
    }
}
