package org.zjj.myspring.aop.framework;

import java.lang.reflect.Method;

import org.zjj.myspring.aop.AdvisedSupport;

import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@RequiredArgsConstructor
public class CglibAopProxy implements AopProxy {

    /**
     * This interceptor is not same as the interface in aop alliance.
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advisedSupport;


        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(
                advisedSupport.getTargetSource().getTarget(),
                method,
                args,
                proxy
            );
            if (advisedSupport.getMethodMatcher().matches(
                        method,
                        advisedSupport.getTargetSource().getTargetClass().getClass())
               ) {
                return advisedSupport.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }

    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        /**
         * MethodProxy is object that can invoke either original method(invokeSupper())
         * or advised method(invoke()).
         */
        MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodProxy.invoke(target, arguments);
        }
    }

    private final AdvisedSupport advisedSupport;

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        return enhancer.create();
    }

}
