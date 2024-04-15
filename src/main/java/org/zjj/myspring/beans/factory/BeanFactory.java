package org.zjj.myspring.beans.factory;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface BeanFactory {

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    Object getBean(String name) throws BeansException;

}
