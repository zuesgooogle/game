package com.s4game.server.public_.share.rule;

import com.s4game.core.thread.executor.RuleChecker;

public class PublicRuleChecker implements RuleChecker {

    @Override
    public boolean valid(Object rule) {
        return true;
    }

}
