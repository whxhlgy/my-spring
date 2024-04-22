package org.zjj.myspring.aop;

import lombok.Getter;

/**
 *
 * A {@code TargetSource} is used to obtain the current "target" of
 * an AOP invocation, which will be invoked via reflection if no around
 * advice chooses to end the interceptor chain itself.
 *
 * <p>If a {@code TargetSource} is "static", it will always return
 * the same target, allowing optimizations in the AOP framework. Dynamic
 * target sources can support pooling, hot swapping, etc.
 *
 * <p>Application developers don't usually need to work with
 * {@code TargetSources} directly: this is an AOP framework interface.
 *
 */
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
