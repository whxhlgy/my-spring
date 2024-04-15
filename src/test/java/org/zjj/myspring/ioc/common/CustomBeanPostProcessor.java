package org.zjj.myspring.ioc.common;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.ioc.bean.Car;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization");
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini" );
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization");
        return bean;
    }

}
