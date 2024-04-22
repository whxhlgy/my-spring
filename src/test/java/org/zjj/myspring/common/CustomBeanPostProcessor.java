package org.zjj.myspring.common;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.bean.Car;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization" + " BeanName: " + beanName);
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini" );
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization " + "beanName: " + beanName);
        return bean;
    }

}
