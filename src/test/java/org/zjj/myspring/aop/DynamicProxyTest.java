package org.zjj.myspring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.zjj.myspring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.zjj.myspring.aop.aspect.AspectJExpressionPointcut;
import org.zjj.myspring.aop.aspect.AspectJExpressionPointcutAdvisor;
import org.zjj.myspring.common.WorldServiceBeforeAdvice;
import org.zjj.myspring.common.WorldServiceInterceptor;
import org.zjj.myspring.service.WorldService;
import org.zjj.myspring.service.WorldServiceImpl;
import org.zjj.myspring.aop.framework.CglibAopProxy;
import org.zjj.myspring.aop.framework.JdkDynamicAopProxy;
import org.zjj.myspring.aop.framework.ProxyFactory;

public class DynamicProxyTest {

    private AdvisedSupport advisedSupport;

    @Before
    public void setup() {
        WorldService worldService = new WorldServiceImpl();

        advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.zjj.myspring.service.WorldService.explode(..))").getMethodMatcher();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testCglibDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory() throws Exception {
        // use JDK dynamic proxy
        advisedSupport.setProxyTargetClass(false);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();

        // use CGLIB proxy
        advisedSupport.setProxyTargetClass(true);
        proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }
    @Test
    public void testBeforeAdvice() throws Exception {
        //设置BeforeAdvice
        WorldServiceBeforeAdvice beforeAdvice = new WorldServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }
    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(worldService);
            advisedSupport.setTargetSource(targetSource);
            // TODO:
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//			advisedSupport.setProxyTargetClass(true);   //JDK or CGLIB

            WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
            proxy.explode();
        }
    }
}
