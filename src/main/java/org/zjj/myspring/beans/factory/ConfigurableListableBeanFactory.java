package org.zjj.myspring.beans.factory;

import org.zjj.myspring.beans.factory.config.AutowireCapableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.ConfigurableBeanFactory;

/**
 * it provides facilities to analyze and modify bean definitions, and to pre-instantiate singletons.
 */
public interface ConfigurableListableBeanFactory
extends AutowireCapableBeanFactory, ConfigurableBeanFactory, ListableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName);

    /**
     * Ensure that all non-lazy-init singletons are instantiated.
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;
}
