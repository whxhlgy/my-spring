package org.zjj.myspring.aop;

import org.aopalliance.intercept.MethodInterceptor;

import lombok.Data;

@Data
public class AdvisedSupport {

    // Whether use the CGLIB proxy
    private boolean proxyTargetClass = true;

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

}
