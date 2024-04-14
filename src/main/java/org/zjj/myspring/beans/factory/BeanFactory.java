package org.zjj.myspring.beans.factory;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface BeanFactory {
    Object getBean(String name, Class<?> requiredType) throws BeansException;

    Object getBean(String name) throws BeansException;
}
