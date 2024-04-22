package org.zjj.myspring.common;

import java.lang.reflect.Method;

import org.zjj.myspring.aop.MethodBeforeAdvice;

public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("WorldServiceBeforeAdvice: do something");
    }

}
