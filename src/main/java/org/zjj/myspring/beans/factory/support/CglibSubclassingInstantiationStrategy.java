package org.zjj.myspring.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import org.zjj.myspring.beans.factory.config.BeanDefinition;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        return enhancer.create();
    }
}
