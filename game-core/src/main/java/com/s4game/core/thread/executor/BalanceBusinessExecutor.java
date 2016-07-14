package com.s4game.core.thread.executor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 获取最少任务的 Executor
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月14日 下午3:34:25
 */
public class BalanceBusinessExecutor implements BusinessExecutor {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private String name;
    
    /**
     * clean 线程轮训周期
     */
    private int cleanPeroid = 600000; // ms
    
    private RuleChecker ruleChecker;

    private Map<String, Integer> config;

    private HashMap<String, BalanceExecutorGroup> groups = new HashMap<>();
    
    public void init() {
        Assert.notNull(config);

        for (Map.Entry<String, Integer> entry : config.entrySet()) {
            addExecutorGroup(entry.getKey(), entry.getValue());
        }
        
        Thread cleaner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (;;) {
                        TimeUnit.MILLISECONDS.sleep(cleanPeroid);

                        for (BalanceExecutorGroup executorGroup : groups.values()) {
                            executorGroup.clean();
                        }
                    }
                } catch (Exception e) {
                    LOG.error("", e);
                }
            }
        }, "BalanceExecutor-Cleaner-" + name);

        cleaner.setDaemon(true);
        cleaner.start();
    }
    
    @Override
    public void execute(Runnable task, Route route) {
        ExecutorService executor = groups.get(route.getGroup()).getExecutorService(route.getData());
        executor.execute(task);
    }

    @Override
    public void addExecutorGroup(String groupName, int size) {
        if (!groups.containsKey(groupName)) {
            groups.put(groupName, new BalanceExecutorGroup(groupName, size));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCleanPeroid() {
        return cleanPeroid;
    }

    public void setCleanPeroid(int cleanPeroid) {
        this.cleanPeroid = cleanPeroid;
    }

    public Map<String, Integer> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Integer> config) {
        this.config = config;
    }

    public RuleChecker getRuleChecker() {
        return ruleChecker;
    }

    public void setRuleChecker(RuleChecker ruleChecker) {
        this.ruleChecker = ruleChecker;
    }

    public class BalanceExecutorGroup {

        private String name;

        private int size;

        private BalanceExecutor[] executors;

        private ConcurrentMap<String, RuleExecutorRelation> ruleMap;

        public BalanceExecutorGroup(String name, int size) {
            this.name = name;
            this.size = size;
            this.ruleMap = new ConcurrentHashMap<>();

            this.executors = new BalanceExecutor[size];
            for (int i = 0; i < size; i++) {
                this.executors[i] = new BalanceExecutor(name + "-" + i);
            }
        }

        public ExecutorService getExecutorService(String rule) {
            RuleExecutorRelation relation = ruleMap.get(rule);
            if (relation == null) {
                relation = new RuleExecutorRelation(rule, getLowestBalanceExecutor());
                ruleMap.put(rule, relation);
            }

            return relation.getBalanceExecutor().getExecutorService();
        }

        /**
         * 获取最少使用的 Executor
         */
        public BalanceExecutor getLowestBalanceExecutor() {
            BalanceExecutor executor = executors[0];

            int min = Integer.MAX_VALUE;
            for (BalanceExecutor e : executors) {
                int loadFactor = e.loadFactor();

                if (min > loadFactor) {
                    min = loadFactor;
                    executor = e;
                }
            }

            return executor;
        }

        /**
         * clean relation
         * reset loadFactor
         */
        public void clean() {
            Iterator<RuleExecutorRelation> iter = ruleMap.values().iterator();
            while(iter.hasNext()) {
                RuleExecutorRelation relation = iter.next();
                if (relation.canClean()) {
                    iter.remove();
                }
            }
            
            for(BalanceExecutor executor : executors) {
                executor.clean();
            }
        }
        
        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }

    public class BalanceExecutor {

        private AtomicInteger loadFactor;

        private String name;

        private ExecutorService executorService;

        public BalanceExecutor(String name) {
            this.name = name;
            this.loadFactor = new AtomicInteger();
            this.executorService = Executors.newSingleThreadExecutor();
        }

        public void clean() {
            loadFactor.set(0);
        }

        public int loadFactor() {
            return loadFactor.get();
        }

        public ExecutorService getExecutorService() {
            loadFactor.incrementAndGet();

            return executorService;
        }

        public String getName() {
            return name;
        }
    }

    public class RuleExecutorRelation {

        private String rule;

        private BalanceExecutor balanceExecutor;

        public RuleExecutorRelation(String rule, BalanceExecutor balanceExecutor) {
            this.rule = rule;
            this.balanceExecutor = balanceExecutor;
        }

        public boolean canClean() {
            return ruleChecker.valid(rule);
        }

        public BalanceExecutor getBalanceExecutor() {
            return this.balanceExecutor;
        }
    }
}
