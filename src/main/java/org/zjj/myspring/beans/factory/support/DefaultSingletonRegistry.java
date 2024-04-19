package org.zjj.myspring.beans.factory.support;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.DisposableBean;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class DefaultSingletonRegistry implements SingletonRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>(256);

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /** this method is intended to be used by developer */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName,disposableBean);
    }

    public void destroySingletons() {
        Set<String> beanNames = disposableBeans.keySet();
        for (String beanName: beanNames) {
            DisposableBean b = disposableBeans.remove(beanName);
            try {
                b.destroy();
            } catch (Exception e) {
                throw new BeansException("Destory beans names: " + beanName + " throw a Exception" + e);
            }
        }
    }
}
