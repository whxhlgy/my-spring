package org.zjj.myspring.beans.factory;

import java.util.Map;

import org.zjj.myspring.beans.BeansException;

/**
 * extension of BeanFactory to be able to enumerate all defined beans,
 * not just the ones that have been pre-instantiated.
 */
public interface ListableBeanFactory extends BeanFactory {
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();
}

