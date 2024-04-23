package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.factory.HierarchicalBeanFactory;
import org.zjj.myspring.beans.factory.support.SingletonRegistry;
import org.zjj.myspring.util.StringValueResolver;

/**
 * provide facilities to configure bean instances.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonRegistry {
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();

    /**
     * Add a embedded value resolver such as placeholder resolver.
     *
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve a embedded value such as annotation value;
     */
    String resolveEmbeddedValue(String value);
}
