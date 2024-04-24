package org.zjj.myspring.aop.framework.autoproxy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import org.zjj.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.zjj.myspring.beans.factory.support.DefaultListableBeanFactory;

public class DefaultAdvisorAutoProxyCreator implements
    InstantiationAwareBeanPostProcessor,
    BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

	/**
	 * Keep track of early proxy references in order to avoid infinite loops.
	 * 
	 * "early" means that the target bean has not been fully initialized yet,
	 * but we can return a wrapper (proxy) for it already.
	 */
    private Set<Object> earlyProxyReference = new HashSet<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReference.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		// mark the bean indicate that this bean has been wrapped by a proxy, but is a early one
        earlyProxyReference.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    /**
     * Wrap the object as proxy if necessary
     * @param bean
     * @param beanName
     * @return
     */
    private Object wrapIfNecessary(Object bean, String beanName) {
        Class<? extends Object> beanClass = bean.getClass();
        if (isInfrastructureClass(beanClass)) {
            return bean;
        }
        Collection<AspectJExpressionPointcutAdvisor> values =
        beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class)
                   .values();

        try {
            for (AspectJExpressionPointcutAdvisor advisor : values) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    advisedSupport.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());
                    advisedSupport.setTargetSource(new TargetSource(bean));
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());


                    Object proxy = new ProxyFactory(advisedSupport).getProxy();
                    return proxy;
                }
            }
        } catch (Exception e) {
            throw new BeansException("Failed to create proxy of :" + beanName, e);
        }
        return bean;
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
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

}
