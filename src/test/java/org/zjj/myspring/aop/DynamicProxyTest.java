package org.zjj.myspring.aop;

import org.junit.Test;
import org.zjj.myspring.aop.aspect.AspectJExpressionPointcut;
import org.zjj.myspring.common.WorldServiceInterceptor;
import org.zjj.myspring.service.WorldService;
import org.zjj.myspring.service.WorldServiceImpl;
import org.zjj.myspring.aop.framework.JdkDynamicAopProxy;

public class DynamicProxyTest {
    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher = new AspectJExpressionPointcut(
            "execution(* org.zjj.myspring.service.WorldService.explode(..))"
        ).getMethodMatcher();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);

        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }
}
