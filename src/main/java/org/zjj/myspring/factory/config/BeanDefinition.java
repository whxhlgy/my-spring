package org.zjj.myspring.factory.config;

import lombok.Data;

/**
 * BeanDefinition should contain the information of a bean, such as class, type
 * (singleton, prototype, etc.), and constructor arguments.
 * For simplicity, we only consider the class here.
 *
 * @author zhongjunjie on 2024/4/7
 */
@Data
public class BeanDefinition {
    private final Class<?> beanClass;
}
