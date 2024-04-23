package org.zjj.myspring.aop.framework.autoproxy;

import java.util.Collection;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.zjj.myspring.aop.AdvisedSupport;
import org.zjj.myspring.aop.Advisor;
import org.zjj.myspring.aop.ClassFilter;
import org.zjj.myspring.aop.Pointcut;
import org.zjj.myspring.aop.TargetSource;
import org.zjj.myspring.aop.aspect.AspectJExpressionPointcutAdvisor;
import org.zjj.myspring.aop.framework.ProxyFactory;
import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValues;
import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.beans.factory.BeanFactoryAware;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.zjj.myspring.beans.factory.support.DefaultListableBeanFactory;

public class DefaultAdvisorAutoProxyCreator implements
    InstantiationAwareBeanPostProcessor,
    BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> values =
        beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class)
                   .values();

        try {
            for (AspectJExpressionPointcutAdvisor advisor : values) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);
                    advisedSupport.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());
                    advisedSupport.setTargetSource(new TargetSource(bean));
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception e) {
            throw new BeansException("Failed to create proxy of :" + beanName, e);
        }
        return null;
    }

    /*
     * Return whether the given bean class represents an infrastructure class
     * that should never be proxied.
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) ||
               Pointcut.class.isAssignableFrom(beanClass) ||
               Advisor.class.isAssignableFrom(beanClass);
    }
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pValues, Object bean2, String beanName)
    throws BeansException {
        // do nothing;
        return pValues;
    }

}
