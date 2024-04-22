package org.zjj.myspring.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.zjj.myspring.aop.AdvisedSupport;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                   getClass().getClassLoader(),
                   advisedSupport.getTargetSource().getTargetClass(),
                   this
               );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advisedSupport.getTargetSource().getTarget();
        if (advisedSupport
                .getMethodMatcher()
                .matches(method, advisedSupport.getTargetSource().getTargetClass().getClass())) {
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            // use aopalliance to invoke
            return methodInterceptor
            .invoke(
                new ReflectionMethodInvocation(
                    target,
                    method,
                    args
                ));
        }
        // normal invoke
        return method.invoke(target, args);
    }

}
