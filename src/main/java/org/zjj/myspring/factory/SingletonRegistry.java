package org.zjj.myspring.factory;

/**
 * Registry of singleton beans
 *
 * @author zhongjunjie on 2024/4/7
 */
public interface SingletonRegistry {
    Object getSingleton(String beanName);
}
