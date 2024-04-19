package org.zjj.myspring.beans.factory.support;

import java.lang.reflect.Method;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.DisposableBean;
import org.zjj.myspring.beans.factory.config.BeanDefinition;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Adapter that implement DisposableBeanAdapter interface performing various destrution steps
 * on a given bean instance:
 * 1. the bean implementing the DisposableBean itself
 * 2. a custom destory method specified on the bean definition
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private final String destoryMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destoryMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotEmpty(destoryMethodName) &&
                !(bean instanceof DisposableBean
                  && "destroy".equals(destoryMethodName))) { // avoid redundant destroy call
            // invoke custom destroy method
            Method method = ClassUtil.getPublicMethod(bean.getClass(), destoryMethodName);
            if (method == null) {
                throw new BeansException("Could not find destroy method '" + destoryMethodName + "' on bean with name '"
                                         + beanName + "'");
            }
            method.invoke(bean);
        }
    }

}
