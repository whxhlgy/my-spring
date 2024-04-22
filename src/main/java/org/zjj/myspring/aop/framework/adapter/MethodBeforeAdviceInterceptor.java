package org.zjj.myspring.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.zjj.myspring.aop.MethodBeforeAdvice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        this.advice.before(
                       invocation.getMethod(),
                       invocation.getArguments(),
                       invocation.getThis()
                   );

        return invocation.proceed();
    }

}
