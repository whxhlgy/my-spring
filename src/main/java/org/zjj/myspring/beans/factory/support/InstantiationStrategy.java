package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.config.BeanDefinition;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition);
}
