package org.zjj.myspring.aop;

import lombok.Getter;

public class TargetSource {
    @Getter
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }
}
