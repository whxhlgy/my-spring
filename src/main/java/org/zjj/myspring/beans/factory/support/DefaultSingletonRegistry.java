package org.zjj.myspring.beans.factory.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.DisposableBean;
import org.zjj.myspring.beans.factory.ObjectFactory;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class DefaultSingletonRegistry implements SingletonRegistry {

    // cache proxy object
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>(256);

    // cache instantiated bean.
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    // level 3 cache
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object object = singletonObjects.get(beanName);
        if (object == null) {
            object = earlySingletonObjects.get(beanName);
            if (object == null) {
                ObjectFactory<?> factory = singletonFactories.get(beanName);
                if (factory != null) {
                    object = factory.getObject();
                    // move from lv3 cache to lv2 cache
                    singletonFactories.remove(beanName);
                    earlySingletonObjects.put(beanName, object);
                }
            }
        }
        return object;
    }

    /** this method is intended to be used by developer */
    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        //
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
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

    public void addSingletonFactoy(String beanName, ObjectFactory<?> factory) {
        singletonFactories.put(beanName, factory);
    }
}
