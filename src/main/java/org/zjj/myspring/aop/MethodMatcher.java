package org.zjj.myspring.aop;

import java.lang.reflect.Method;

/**
 * Part of the pointcut, used to determine if the method matches the pointcut expression
 */
public interface MethodMatcher {

    /**
     * Perform static checking whether the given method matches.
     * @param method the candidate method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);

}
