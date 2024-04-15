package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.factory.HierarchicalBeanFactory;
import org.zjj.myspring.beans.factory.support.SingletonRegistry;

/**
 * provide facilities to configure bean instances.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
