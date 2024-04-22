package org.zjj.myspring.aop;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * Callback before a method is invoked.
     *
     * @param method the method be invoke
     * @param args the arguments to the method
     * @param target the target of the method invocation.
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
