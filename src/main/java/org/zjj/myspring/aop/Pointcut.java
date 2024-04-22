package org.zjj.myspring.aop;

/**
 * Pointcut is a set of rules that determine whether a method should be intercepted
 */
public interface Pointcut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
