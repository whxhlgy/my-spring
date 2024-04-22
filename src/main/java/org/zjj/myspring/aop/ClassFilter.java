package org.zjj.myspring.aop;

/**
 * Filter that restricts matching of a pointcut or introduction to a given set of target classes.
 */
public interface ClassFilter {
    /**
     * Should the pointcut apply to the given interface or target class?
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
