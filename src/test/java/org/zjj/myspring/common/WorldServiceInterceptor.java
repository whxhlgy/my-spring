package org.zjj.myspring.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class WorldServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("WorldServiceInterceptor: before");
        Object result = invocation.proceed();
        System.out.println("WorldServiceInterceptor: after");
        return result;
    }

}
