package org.zjj.myspring.factory;

import org.zjj.myspring.factory.config.BeanDefinition;

/**
 * This factory is responsible for creating bean instances.
 *
 * @author zhongjunjie on 2024/4/7
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    // create bean from beanDefinition
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            addSingleton(beanName, bean);
            return bean;
        } catch (Exception e) {
            throw new BeansException("Initialization of bean failed: " + beanName, e);
        }
    }
}
