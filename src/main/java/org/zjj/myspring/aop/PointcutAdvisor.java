package org.zjj.myspring.aop;

public interface PointcutAdvisor extends Advisor {

    /**
     * Get the Pointcut that this advisor uses.
     * @return
     */
    Pointcut getPointcut();
}

