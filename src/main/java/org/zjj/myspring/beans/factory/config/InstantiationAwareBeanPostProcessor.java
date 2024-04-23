package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * execute before instantiation
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * execute after instantiation, but before property population
     * @param bean
     * @param beanName
     * @return if the bean population should be skipped
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * post-process the given property values before the factory applies them to the given bean.
     *
     * @param bean
     * @param pValues
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pValues, Object bean2, String beanName) throws BeansException;
}
