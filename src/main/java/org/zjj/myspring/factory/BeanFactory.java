package org.zjj.myspring.factory;

/**
 * @author zhongjunjie on 2024/4/7
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
