package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * execute before instantiation
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
