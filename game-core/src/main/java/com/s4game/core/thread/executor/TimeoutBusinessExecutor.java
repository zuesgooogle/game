package com.s4game.core.thread.executor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * 控制任务数量
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月12日 下午5:43:08
 */
public class TimeoutBusinessExecutor implements BusinessExecutor {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private int aliveTime = 3000; // ms

    /**
     * clean 线程轮训周期
     */
    private int cleanPeroid = 120000; // ms

    /**
     * 积压 task 数量 > triggerSize 后，进行clean
     */
    private int triggerSize = 300000;

    private Map<String, Integer> groups;

    private Map<String, TimeoutExecutorGroup> groupMap = new HashMap<>();

    public void init() {
        Assert.notNull(groups);

        for (Map.Entry<String, Integer> entry : groups.entrySet()) {
            addExecutorGroup(entry.getKey(), entry.getValue());
        }

        // clean thread
        Thread cleaner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (;;) {
                        TimeUnit.MILLISECONDS.sleep(cleanPeroid);
                        
                        for (TimeoutExecutorGroup executorGroup : groupMap.values()) {
                            executorGroup.clean();
                        }
                    }
                } catch (Exception e) {
                    LOG.error("", e);
                }
            }
        }, "TimeoutExecutor-Cleaner");

        cleaner.setDaemon(true);
        cleaner.start();
    }

    @Override
    public void execute(Runnable task, Route route) {
        groupMap.get(route.getGroup()).getExecutor(route.getData()).execute(task);
    }

    @Override
    public void addExecutorGroup(String groupName, int size) {
        if (!groupMap.containsKey(groupName)) {
            groupMap.put(groupName, new TimeoutExecutorGroup(groupName, size));
        }
    }

    public int getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(int aliveTime) {
        this.aliveTime = aliveTime;
    }

    public int getCleanPeroid() {
        return cleanPeroid;
    }

    public void setCleanPeroid(int cleanPeroid) {
        this.cleanPeroid = cleanPeroid;
    }

    public int getTriggerSize() {
        return triggerSize;
    }

    public void setTriggerSize(int triggerSize) {
        this.triggerSize = triggerSize;
    }

    public Map<String, Integer> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Integer> groups) {
        this.groups = groups;
    }

    public class TimeoutExecutorGroup {

        private int size;

        private int assignCount;

        private TimeoutExecutor[] executors;

        private Map<String, TimeoutExecutor> ruleMap = new HashMap<>();

        public TimeoutExecutorGroup(String groupName, int size) {
            this.size = size;

            this.executors = new TimeoutExecutor[size];
            for (int i = 0; i < size; i++) {
                this.executors[i] = new TimeoutExecutor(groupName + "-" + i);
            }
        }

        public synchronized TimeoutExecutor getExecutor(String rule) {
            TimeoutExecutor timeoutExecutor = ruleMap.get(rule);
            if (null == timeoutExecutor) {
                timeoutExecutor = this.executors[(this.assignCount++ % this.size)];
                this.ruleMap.put(rule, timeoutExecutor);
            }
            return timeoutExecutor;
        }

        public void clean() {
            for (TimeoutExecutor timeoutExecutor : executors) {
                timeoutExecutor.clean();
            }
        }
    }

    public class TimeoutExecutor {

        private String name;

        private LinkedBlockingQueue<TimeoutTask> queue = new LinkedBlockingQueue<>();

        public TimeoutExecutor(String name) {
            this.name = name;

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        for (;;) {
                            TimeoutTask task = queue.take();
                            task.run(); 
                        }
                    } catch (Exception e) {
                        LOG.error("", e);
                    }
                }

            }, "TimeoutExecutor-" + name).start();
        }

        public void execute(Runnable runnable) {
            queue.add(new TimeoutTask(runnable));
        }

        public void clean() {
            int size = queue.size();

            if (size > triggerSize) {
                LOG.info("TimeoutExecutor[{}] task current size: {}, trigger size: {}", getName(), size, triggerSize);

                long now = System.currentTimeMillis();
                int count = 0;

                for (Iterator<TimeoutTask> iterator = queue.iterator(); iterator.hasNext();) {
                    TimeoutTask task = iterator.next();

                    if (task.timeout()) {
                        iterator.remove();
                        count++;
                    }
                }

                long useTime = System.currentTimeMillis() - now;
                LOG.info("TimeoutExecutor[{}] clean finished. use time: {} ms, clean count: {}", getName(), useTime, count);
            }
        }

        public String getName() {
            return name;
        }
    }

    public class TimeoutTask {

        private long startTime;

        private Runnable runnable;

        public TimeoutTask(Runnable runnable) {
            this.runnable = runnable;
            this.startTime = System.currentTimeMillis();
        }

        public boolean timeout() {
            return System.currentTimeMillis() - startTime > aliveTime;
        }

        public void run() {
            runnable.run();
        }
    }
}
