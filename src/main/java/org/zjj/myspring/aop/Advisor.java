package org.zjj.myspring.aop;

import org.aopalliance.aop.Advice;

public interface Advisor {

    /**
     * Get the advice part of this aspect.
     * @return
     */
    Advice getAdvice();
}

