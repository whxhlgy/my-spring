package org.zjj.myspring.beans.factory.support;

/**
 * Registry of singleton beans
 *
 * @author zhongjunjie on 2024/4/7
 */
public interface SingletonRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName, Object singletonObject);
}
