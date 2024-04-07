package org.zjj.myspring.factory;

import org.zjj.myspring.factory.config.BeanDefinition;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
