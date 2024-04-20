package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.config.BeanDefinition;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * Instantiate a bean using no-args constructor
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanDefinition.getBeanClass().getName() + "]", e);
        }
        return bean;
    }
}
