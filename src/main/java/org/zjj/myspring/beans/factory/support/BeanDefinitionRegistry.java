package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.config.BeanDefinition;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();
}
