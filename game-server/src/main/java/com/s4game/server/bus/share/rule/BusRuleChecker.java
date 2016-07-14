package com.s4game.server.bus.share.rule;

import com.s4game.core.thread.executor.RuleChecker;

public class BusRuleChecker implements RuleChecker {

    @Override
    public boolean valid(Object rule) {
        //TODO role is online
        return true;
    }

}
